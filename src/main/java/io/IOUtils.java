package io;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.scene.image.Image;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;


/**
 * IO.IOUtils.java
 * Contains common IO methods.
 */
public class IOUtils {

    private final static String I2L_APP_ID = "I2L_APP_ID";
    private final static String I2L_APP_KEY = "I2L_APP_KEY";
    private final static String I2L_THIRD_RESULT_WRAPPER_OPTION = "I2L_THIRD_RESULT_WRAPPER_OPTION";
    private final static String I2L_FOURTH_RESULT_WRAPPER_OPTION = "I2L_FOURTH_RESULT_WRAPPER_OPTION";

    private final static String CONFIG_NODE_PATH = "I2L_API_CREDENTIAL_CONFIG";
    private static Preferences preferences = Preferences.userRoot().node(CONFIG_NODE_PATH);

    public final static String[] SUPPORTED_PROTOCOLS = new String[]{"TLSv1.2"};

    // IO.Recognition object initialisation
    private static Recognition recognition = new Recognition();

    /**
     * Original source: https://stackoverflow.com/a/33477375/4658633
     *
     * @return whether macOS enabled dark mode.
     */
    public static boolean isMacDarkMode() {

        try {
            // process will exit with 0 if dark mode enabled
            final Process process = Runtime.getRuntime().exec(new String[]{"defaults", "read", "-g", "AppleInterfaceStyle"});
            process.waitFor(100, TimeUnit.MILLISECONDS);
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException | IllegalThreadStateException e) {
            return false;
        }

    }

    /**
     * Execute the OCR request in Java concurrent way.
     *
     * @param image image to be recognised.
     * @return recognised result.
     */
    public static Response concurrentCall(Image image) {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        if (recognition.setSrcParameters(image)) {
            Future<Response> result = executor.submit(recognition);
            try {
                return result.get();
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }

        return null;

    }

    /**
     * Get latest release version via GitHub API.
     *
     * @return latest released version.
     */
    public static String getLatestVersion() {

        // workaround to resolve #26
        SSLContext context = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(context, SUPPORTED_PROTOCOLS, null, NoopHostnameVerifier.INSTANCE);

        // maximum connection waiting time 1 seconds
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).build();

        // build the HTTP client with above config
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setSSLSocketFactory(sslConnectionSocketFactory).build();

        // API url
        HttpGet request = new HttpGet("https://api.github.com/repos/blaisewang/img2latex-mathpix/releases/latest");

        try {

            // get the raw result from the execution
            HttpResponse result = httpClient.execute(request);
            // obtain the message entity of this response
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            // parse json string to Json object
            JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
            // close and release resources
            httpClient.close();

            String[] tag = jsonObject.get("tag_name").getAsString().split("v");
            return tag[tag.length - 1];

        } catch (IOException ignored) {

            return null;

        }

    }

    /**
     * @return whether the API credential config is valid.
     */
    public static boolean isAPICredentialConfigValid() {
        return getAPICredentialConfig().isValid();
    }

    /**
     * Set app ID.
     *
     * @param appId App ID to be written.
     */
    public static void setAppId(String appId) {
        preferences.put(I2L_APP_ID, appId);
    }

    /**
     * Set app key.
     *
     * @param appKey App key to be written.
     */
    public static void setAppKey(String appKey) {
        preferences.put(I2L_APP_KEY, appKey);
    }

    /**
     * Get App ID and App Key from Java Preferences API.
     *
     * @return IO.APICredentialConfig object.
     */
    public static APICredentialConfig getAPICredentialConfig() {
        return new APICredentialConfig(preferences.get(I2L_APP_ID, ""), preferences.get(I2L_APP_KEY, ""));
    }

    /**
     * Set third result wrapper option.
     *
     * @param option option to be written.
     */
    public static void setThirdResultWrapperOption(int option) {
        preferences.putInt(I2L_THIRD_RESULT_WRAPPER_OPTION, option);
    }

    /**
     * Get third result wrapper option.
     *
     * @return third result wrapper option.
     */
    public static int getThirdResultWrapperOption() {
        return preferences.getInt(I2L_THIRD_RESULT_WRAPPER_OPTION, 2);
    }

    /**
     * Set fourth result wrapper option.
     *
     * @param option option to be written.
     */
    public static void setFourthResultWrapperOption(int option) {
        preferences.putInt(I2L_FOURTH_RESULT_WRAPPER_OPTION, option);
    }

    /**
     * Get fourth result wrapper option.
     *
     * @return fourth result wrapper option.
     */
    public static int getFourthResultWrapperOption() {
        return preferences.getInt(I2L_FOURTH_RESULT_WRAPPER_OPTION, 0);
    }

    /**
     * Wrap the original recognised result with the selected formatting options.
     *
     * @param result recognised result.
     * @return the wrapped result.
     */
    public static String thirdResultWrapper(String result) {

        // return null if the original result is null
        if (result == null) {
            return null;
        }

        int option = getThirdResultWrapperOption();

        switch (option) {
            case 0:
                return "\\begin{equation*}\n " + result + " \n\\end{equation*}";
            case 1:
                return "\\begin{align*}\n " + result + " \n\\end{align*}";
            case 3:
                return "\\[\n " + result + " \n\\]";
            default:
                // default for option 2 and others
                return "$$\n " + result + " \n$$";
        }

    }

    /**
     * Wrap the original recognised result with the selected formatting options.
     *
     * @param result recognised result.
     * @return the wrapped result.
     */
    public static String fourthResultWrapper(String result) {

        // return null if the original result is null
        if (result == null) {
            return null;
        }

        int option = getFourthResultWrapperOption();

        if (option == 1) {
            return "\\begin{align}\n " + result + " \n\\end{align}";
        }

        return "\\begin{equation}\n " + result + " \n\\end{equation}";

    }

}
