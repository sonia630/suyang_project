package com.o2o.massage.core.utils;

/**
 * @author zhuifengbuaa
 * @email tongyao1@xiaomi.com
 * @since Oct 11, 2016 11:00:15 AM
 */
public class KerberosUtils {

    public static void initKerberos(){
        System.setProperty("java.security.krb5.conf","/etc/krb5-hadoop.conf");
        System.setProperty("hadoop.property.hadoop.security.authentication","kerberos");
        System.setProperty("hadoop.property.hadoop.client.kerberos.principal","h_miuisec@XIAOMI.HADOOP");
        System.setProperty("hadoop.property.hadoop.client.keytab.file","/etc/h_miuisec.keytab");
    }
}
