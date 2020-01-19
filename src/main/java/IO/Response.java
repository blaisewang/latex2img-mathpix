package IO;

import java.util.ArrayList;

/**
 * IO.Response.java
 * used by Gson to deserialize the JSON string to the object.
 */
public class Response {

    private String error;
    private String text;
    private String textDisplay;
    private String latexStyled;
    private double latexConfidence;
    private ArrayList<String> detectionList;

    public Response(String error, String text, String textDisplay, String latexStyled, double latexConfidence, ArrayList<String> detectionList) {

        this.error = error;
        this.text = text;
        this.textDisplay = textDisplay;
        this.latexStyled = latexStyled;
        this.latexConfidence = latexConfidence;
        this.detectionList = detectionList;

    }

    /**
     * @return error message if any.
     */
    public String getError() {
        return error;
    }

    /**
     * @return text format result.
     */
    public String getText() {
        return text;
    }

    /**
     * @return text_display format result.
     */
    public String getTextDisplay() {
        return textDisplay;
    }

    /**
     * @return LaTeX format result.
     */
    public String getLatexStyled() {
        return latexStyled;
    }

    /**
     * @return confidence of the correctness.
     */
    public Double getLatexConfidence() {
        return latexConfidence;
    }

    /**
     * @return if no equation in the image.
     */
    public Boolean isNotMath() {
        return detectionList.contains("is_not_math");
    }

}