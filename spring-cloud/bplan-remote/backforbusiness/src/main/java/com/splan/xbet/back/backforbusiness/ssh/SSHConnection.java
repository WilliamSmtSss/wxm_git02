package com.splan.xbet.back.backforbusiness.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.util.Properties;

public class SSHConnection {
    private final static String S_PATH_FILE_PRIVATE_KEY = "/Users/hdwang/.ssh/id_rsa";
    private final static String S_PATH_FILE_KNOWN_HOSTS = "/Users/hdwang/.ssh/known_hosts";
    private final static String S_PASS_PHRASE = "";
    private final static int LOCAl_PORT = 3310;
    private final static int REMOTE_PORT = 3306;
    private final static int SSH_REMOTE_PORT = 22;
    //商户
//    private final static String SSH_USER = "vuerelease";
//    private final static String SSH_PASSWORD = "HdLK3X1cihjqB6Q0";
//    private final static String SSH_REMOTE_SERVER = "47.75.118.226";
//    private final static String MYSQL_REMOTE_SERVER = "rm-3ns0j7yt1qw20z8qk.mysql.rds.aliyuncs.com";

    //暴风test
//    private final static String SSH_USER = "root";
//    private final static String SSH_PASSWORD = "RiseWinterBplan4!";
//    private final static String SSH_REMOTE_SERVER = "47.91.159.222";
//    private final static String MYSQL_REMOTE_SERVER = "rm-3nst8g3h627285fw0.mysql.rds.aliyuncs.com";

    //ams
    private final static String SSH_USER = "XBET-DEVOS";
    private final static String SSH_PASSWORD = "&KLID0WUs*k&jvWcMGtTVF*^1l!8UQz%";
    private final static String SSH_REMOTE_SERVER = "18.162.112.210";
    private final static String MYSQL_REMOTE_SERVER = "xbet-dev.c0ihwn6tazso.ap-east-1.rds.amazonaws.com";

    private Session sesion; //represents each ssh session

    public void closeSSH ()
    {
        sesion.disconnect();
    }

    public SSHConnection() throws Throwable
    {

        JSch jsch = null;

        jsch = new JSch();
//        jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        //jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY);

        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);

        sesion.setPassword(SSH_PASSWORD);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        sesion.setConfig(config);

        sesion.connect(); //ssh connection established!

        //by security policy, you must connect through a fowarded port
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);

    }
}
