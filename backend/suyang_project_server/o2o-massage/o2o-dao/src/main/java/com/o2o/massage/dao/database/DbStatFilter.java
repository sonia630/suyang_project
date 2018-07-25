package com.o2o.massage.dao.database;

import com.alibaba.druid.filter.stat.MergeStatFilter;
import com.alibaba.druid.filter.stat.StatFilterContext;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementExecuteType;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.visitor.ParameterizedOutputVisitorUtils;
import com.alibaba.druid.stat.JdbcDataSourceStat;
import com.alibaba.druid.stat.JdbcSqlStat;
import com.alibaba.druid.support.json.JSONWriter;
import com.alibaba.druid.support.profile.Profiler;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.Map;

/**
 * druid sql执行情况拦截器
 */
public class DbStatFilter extends MergeStatFilter {
    private Logger acSlowSqlLogger = LoggerFactory.getLogger("tujia_slow_sql_logger");
    private Logger exceptionLog = LoggerFactory.getLogger("tujia_exception_sql_logger");
    private boolean mergeSql = false;
    private boolean isLogAllSql = false;//!StringUtils.equals(PropertyUtil.getProperty("runMod"), "prod"); // 非线上环境记录所有sql

    public boolean isLogAllSql() {
        return isLogAllSql;
    }

    public void setIsLogAllSql(boolean isLogAllSql) {
        this.isLogAllSql = isLogAllSql;
    }

    public DbStatFilter() {
        super.setMergeSql(true);
        this.mergeSql = true;
    }

    // same with super class . just for logging.
    @Override
    public String mergeSql(String sql, String dbType) {
        if (!mergeSql) {
            return sql;
        }
        try {
            return ParameterizedOutputVisitorUtils.parameterize(sql, dbType);
        } catch (Exception e) {
            acSlowSqlLogger.error("merge sql error, dbType {}, sql : \n{}", dbType, sql, e);
        }
        return sql;
    }


    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        internalAfterStatementExecute(statement, firstResult);
    }

    @Override
    protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
        internalAfterStatementExecute(statement, false, result);

    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        internalAfterStatementExecute(statement, true);
    }

    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        internalAfterStatementExecute(statement, false, updateCount);
    }

    private final void internalAfterStatementExecute(StatementProxy statement, boolean firstResult,
                                                     int... updateCountArray) {
        final long nowNano = System.nanoTime();
        final long nanos = nowNano - statement.getLastExecuteStartNano();

        JdbcDataSourceStat dataSourceStat = statement.getConnectionProxy().getDirectDataSource().getDataSourceStat();
        dataSourceStat.getStatementStat().afterExecute(nanos);

        final JdbcSqlStat sqlStat = statement.getSqlStat();

        if (sqlStat != null) {
            sqlStat.incrementExecuteSuccessCount();

            sqlStat.decrementRunningCount();
            sqlStat.addExecuteTime(statement.getLastExecuteType(), firstResult, nanos);
            statement.setLastExecuteTimeNano(nanos);
            if ((!statement.isFirstResultSet()) && statement.getLastExecuteType() == StatementExecuteType.Execute) {
                try {
                    int updateCount = statement.getUpdateCount();
                    sqlStat.addUpdateCount(updateCount);
                } catch (SQLException e) {
                    acSlowSqlLogger.error("getUpdateCount error", e);
                }
            } else {
                for (int updateCount : updateCountArray) {
                    sqlStat.addUpdateCount(updateCount);
                    sqlStat.addFetchRowCount(0);
                    StatFilterContext.getInstance().addUpdateCount(updateCount);
                }
            }

            boolean isLoged = false;
            long millis = nanos / (1000 * 1000);
            if (millis >= slowSqlMillis) {
                String slowParameters = buildSlowParameters(statement);
                sqlStat.setLastSlowParameters(slowParameters);

                if (logSlowSql) {
                    isLoged = true;
                    String slowSql = statement.getLastExecuteSql();
                    acSlowSqlLogger.error("slow sql {} millis. \n{}\n {}"
                            , millis, slowSql, slowParameters);
                }
                //TMonitor.recordOne(TmonitorConstants.SLOW_SQL_COST_TIME, millis);
            }

            if (!isLoged && isLogAllSql) {
                String slowParameters = buildSlowParameters(statement);
                String lastExecuteSql = statement.getLastExecuteSql().replaceAll("[\n\t ]+", " ");
                sqlStat.setLastSlowParameters(slowParameters);
                acSlowSqlLogger.info("all sql {} millis. {} =====params:{}"
                        , millis
                        , lastExecuteSql
                        , slowParameters);
            }
            //TMonitor.recordOne(TmonitorConstants.SQL_COST_TIME, millis);
        }

        String sql = statement.getLastExecuteSql();
        StatFilterContext.getInstance().executeAfter(sql, nanos, null);

        Profiler.release(nanos);
    }

    private String buildSlowParameters(StatementProxy statement) {
        JSONWriter out = new JSONWriter();

        out.writeArrayStart();
        for (int i = 0, parametersSize = statement.getParametersSize(); i < parametersSize; ++i) {
            JdbcParameter parameter = statement.getParameter(i);
            if (i != 0) {
                out.writeComma();
            }
            if (parameter == null) {
                continue;
            }

            Object value = parameter.getValue();
            if (value == null) {
                out.writeNull();
            } else if (value instanceof String) {
                String text = (String) value;
                if (text.length() > 100) {
                    out.writeString(text.substring(0, 97) + "...");
                } else {
                    out.writeString(text);
                }
            } else if (value instanceof Number
                    || value instanceof java.util.Date
                    || value instanceof Boolean) {
                out.writeObject(value);
            } else if (value instanceof InputStream) {
                out.writeString("<InputStream>");
            } else if (value instanceof NClob) {
                out.writeString("<NClob>");
            } else if (value instanceof Clob) {
                out.writeString("<Clob>");
            } else if (value instanceof Blob) {
                out.writeString("<Blob>");
            } else {
                out.writeString('<' + value.getClass().getName() + '>');
            }
        }
        out.writeArrayEnd();

        return out.toString();
    }

    @Override
    protected void statement_executeErrorAfter(StatementProxy statement, String sql, Throwable error) {
        super.statement_executeErrorAfter(statement, sql, error);
        try {
           // TMonitor.recordOne(TmonitorConstants.DB_EXCEPTION);
            Map<Integer, JdbcParameter> parameters = statement.getParameters();
            StringBuilder paramsBuf = new StringBuilder("[ ");
            if (MapUtils.isNotEmpty(parameters)) {
                for (Map.Entry<Integer, JdbcParameter> entry : parameters.entrySet()) {
                    paramsBuf = paramsBuf.append(ObjectUtils.toString(entry.getValue().getValue())).append(",");
                }
            }
            paramsBuf = paramsBuf.append(" ]");
            exceptionLog.error("execute sql Error:{} ; params:{}", sql, paramsBuf, error);
        } catch (Exception e) {
            exceptionLog.error(e.getMessage(), e);
        }

    }
}
