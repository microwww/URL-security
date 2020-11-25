
package com.github.microwww.security.cli.help;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author changshu.li
 */
public class Rconfig {

    private static Log logger = LogFactory.getLog(Rconfig.class);
    private static Properties properties = new Properties();
    public static String CONFIG_FILE = "/rconfig.properties";

    static {
        loadConfig();
    }

    private Rconfig() {
    }

    private static void loadConfig() {
        try {
            InputStream input = Rconfig.class.getResourceAsStream(CONFIG_FILE);
            properties.load(input);
        } catch (IOException ex) {
            logger.error("load " + CONFIG_FILE + "error", ex);
        }
    }

    public static Properties getConfig() {
        return new Properties(properties);
    }

    public static String getConfig(String key, String def) {
        return (String) getConfig().getProperty(key, def);
    }

    public static String getSessionKey() {
        return getConfig().getProperty("login_session_key", "LOGIN_EMPLOYEE");
    }

    public static String getTokenStore() {
        return getConfig().getProperty("token_store", "header");
    }

    public static String getTokenName() {
        return getConfig().getProperty("token_name", "token");
    }

    public static String getLoginPage() {
        return getConfig("login_page", null);
    }

    public static String getSkipUrl() {
        return getConfig("skip_url", "");
    }

    public static boolean isQueryOn() {
        return Boolean.parseBoolean(getConfig("query_on", "false"));
    }

    public static String getAppId() {
        return getConfig("appId", "");
    }

    public static String getAppSecurity() {
        return getConfig("appSecurity", "");
    }

    public static String getCachePrefix() {
        return getConfig("cache_prefix", "rurl-cli-cache_prefix." + getAppId());
    }

    public static String getCacheTime() {
        return getConfig("cache_time", "3600");
    }

    public static String getRurlServer() {
        return getConfig("rurl_server", "");
    }

    public static boolean isCollectUrl() {
        return Boolean.parseBoolean(getConfig("collection_url", "false"));
    }

    public static boolean isOpenRight() {
        return Boolean.parseBoolean(getConfig("open_right", "true"));
    }
}
