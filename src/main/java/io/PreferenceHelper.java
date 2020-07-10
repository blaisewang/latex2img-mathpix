package io;

import java.util.prefs.Preferences;

/**
 * IO.PreferenceHelper.java
 * Contains preference-related methods.
 */
public class PreferenceHelper {

    private static final String I2L_APP_ID = "I2L_APP_ID";
    private static final String I2L_APP_KEY = "I2L_APP_KEY";
    private static final String I2L_PROXY_PORT = "I2L_PROXY_PORT";
    private static final String I2L_PROXY_HOSTNAME = "I2L_PROXY_HOSTNAME";
    private static final String I2L_PROXY_ENABLE_OPTION = "I2L_PROXY_ENABLE_OPTION";
    private static final String I2L_SECOND_FORMATTING_OPTION = "I2L_SECOND_FORMATTING_OPTION";
    private static final String I2L_THIRD_FORMATTING_OPTION = "I2L_THIRD_FORMATTING_OPTION";
    private static final String I2L_SUBMIT_BUTTON_ENABLE_OPTION = "I2L_SUBMIT_BUTTON_ENABLE_OPTION";

    private static final String CONFIG_NODE_PATH = "I2L_API_CREDENTIAL_CONFIG";
    private static final Preferences PREFERENCES = Preferences.userRoot().node(CONFIG_NODE_PATH);

    /**
     * Set app ID.
     *
     * @param appId App ID to be written.
     */
    public static void setAppId(String appId) {
        PREFERENCES.put(I2L_APP_ID, appId);
    }

    /**
     * Set app key.
     *
     * @param appKey App key to be written.
     */
    public static void setAppKey(String appKey) {
        PREFERENCES.put(I2L_APP_KEY, appKey);
    }

    /**
     * Get App ID and App Key from Java Preferences API.
     *
     * @return IO.APICredentialConfig object.
     */
    public static APICredentialConfig getAPICredentialConfig() {
        return new APICredentialConfig(PREFERENCES.get(I2L_APP_ID, ""), PREFERENCES.get(I2L_APP_KEY, ""));
    }

    /**
     * Set second formatting option.
     *
     * @param option option to be written.
     */
    public static void setSecondResultFormattingOption(int option) {
        PREFERENCES.putInt(I2L_SECOND_FORMATTING_OPTION, option);
    }

    /**
     * Get second formatting option.
     *
     * @return second formatting option.
     */
    public static int getSecondResultFormattingOption() {
        return PREFERENCES.getInt(I2L_SECOND_FORMATTING_OPTION, 2);
    }

    /**
     * Set third formatting option.
     *
     * @param option option to be written.
     */
    public static void setThirdFormattingOption(int option) {
        PREFERENCES.putInt(I2L_THIRD_FORMATTING_OPTION, option);
    }

    /**
     * Get third formatting option.
     *
     * @return third formatting option.
     */
    public static int getThirdResultFormattingOption() {
        return PREFERENCES.getInt(I2L_THIRD_FORMATTING_OPTION, 0);
    }


    /**
     * Set proxy enable option.
     *
     * @param option option to be written.
     */
    public static void setProxyEnableOption(boolean option) {
        PREFERENCES.putBoolean(I2L_PROXY_ENABLE_OPTION, option);
    }

    /**
     * Get proxy option enabled or not.
     *
     * @return proxy enable option.
     */
    public static boolean getProxyEnableOption() {
        return PREFERENCES.getBoolean(I2L_PROXY_ENABLE_OPTION, false);
    }

    /**
     * Set proxy host.
     *
     * @param host host to be written.
     */
    public static void setProxyHostname(String host) {
        PREFERENCES.put(I2L_PROXY_HOSTNAME, host);
    }

    /**
     * Set proxy port.
     *
     * @param port port to be written.
     */
    public static void setProxyPort(String port) {
        PREFERENCES.put(I2L_PROXY_PORT, port);
    }

    /**
     * Get proxy config.
     *
     * @return proxy config.
     */
    public static ProxyConfig getProxyConfig() {

        int port;
        try {
            port = Integer.parseInt(PREFERENCES.get(I2L_PROXY_PORT, ""));
        } catch (NumberFormatException e) {
            port = -1;
        }

        return new ProxyConfig(PREFERENCES.get(I2L_PROXY_HOSTNAME, ""), port);

    }

    /**
     * Set submit button enable option.
     *
     * @param option option to be written.
     */
    public static void setSubmitButtonEnableOption(boolean option) {
        PREFERENCES.putBoolean(I2L_SUBMIT_BUTTON_ENABLE_OPTION, option);
    }

    /**
     * Get submit button option enabled or not.
     *
     * @return submit button enable option.
     */
    public static boolean getSubmitButtonEnableOption() {
        return PREFERENCES.getBoolean(I2L_SUBMIT_BUTTON_ENABLE_OPTION, false);
    }

}
