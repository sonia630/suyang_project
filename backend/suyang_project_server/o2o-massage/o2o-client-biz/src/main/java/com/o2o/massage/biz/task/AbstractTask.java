package com.o2o.massage.biz.task;

import com.o2o.massage.core.utils.LocalHost;
import com.o2o.massage.core.utils.PropertyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * task抽象类
 */
public abstract class AbstractTask {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTask.class);

    public static final String taskRunOnHostName = PropertyUtil.getProperty("task_run_on_host_name", "");

    public static final String HOSTNAME;

    static {
        HOSTNAME = LocalHost.getHostName();
        logger.info("本机名称:{}", HOSTNAME);
    }

    /**
     * 任务名称
     *
     * @return
     */
    public abstract String taskName();

    /**
     * 任务执行逻辑
     */
    protected abstract void run()throws Exception;

    /**
     * 任务入口
     */
    public void doSchedule() {
        if (!StringUtils.equals(taskRunOnHostName, HOSTNAME)) {
            logger.info("task not scheduled to run on current machine,taskRunOnHostName:{},current:{},taskName:{}",
                    taskRunOnHostName, HOSTNAME, taskName());
            /*logger.info("系统相关task运行所在机器名与本机名不匹配,不在本机运行,关闭quartz");
            SchedulerFactoryBean sf = SpringContextHolder.getBean(SchedulerFactoryBean.class);
            try {
                sf.getScheduler().shutdown();
            } catch (SchedulerException e) {
                logger.error("关闭quartz时发生错误", e);
                sf.stop();
            }*/
            return;
        }

        long start = System.currentTimeMillis();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("{}[{}]开始执行...", this.taskName(), getClass().getCanonicalName());
            }
            this.run();
            if (logger.isInfoEnabled()) {
                logger.info("{}[{}]执行完毕,耗时[{}]ms", this.taskName()
                        , getClass().getCanonicalName(), (System.currentTimeMillis() - start));
            }
        } catch (Exception e) {
            logger.error("定时任务[{}:{}]运行出错", this.taskName(), getClass().getCanonicalName(), e);
        }
    }
}

