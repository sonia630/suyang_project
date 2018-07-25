package com.o2o.massage.core.utils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class VersionUtils {

    private static Logger logger = LoggerFactory.getLogger(VersionUtils.class);

    private VersionUtils() {
        throw new RuntimeException("No VersionUtils instance for you");
    }

    public static int getVersionNumber(String version) {
        if (StringUtils.isBlank(version)) throw new NullPointerException("version");
        int index = version.indexOf(Constants.DT);
        if (index == -1) return Integer.valueOf(version) * 10000;
        String[] splits = version.split(Constants.ESC_DT);
        int r;
        if (splits.length < 1) throw new IllegalArgumentException(version);
        int n100 = Integer.valueOf(splits[0]) * 10000;
        r = n100;
        if (splits.length > 1) {
            int n10 = Integer.valueOf(splits[1]) * 100;
            r += n10;
        }
        if (splits.length > 2) {
            int n1 = Integer.valueOf(splits[2]) * 1;
            r += n1;
        }
        if (logger.isDebugEnabled()) logger.debug("version:{}, get version number:{}", version, r);
        return r;
    }

    public static boolean greaterThan(String verA, String verB) {
        try {
            return getVersionNumber(verA) > getVersionNumber(verB);
        } catch (Exception ex) {
            return false;
        }
    }

    public static int getMajorVersion(String version) {
        if (StringUtils.isBlank(version)) throw new NullPointerException("version");
        int index = version.indexOf(Constants.DT);
        if (index != -1) {
            version = version.substring(0, index);
        }
        return Integer.valueOf(version);
    }

    public static int getMinorVersion(String version) {
        if (StringUtils.isBlank(version)) throw new NullPointerException("version");
        int index = version.indexOf(Constants.DT);
        if (index == -1) return 0;
        version = version.substring(index + 1, version.length());
        index = version.indexOf(Constants.DT);
        if (index != -1) {
            version = version.substring(0, index);
        }
        return Integer.valueOf(version);
    }

}
