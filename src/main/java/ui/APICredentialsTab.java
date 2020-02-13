package ui;

import io.APICredentialConfig;
import io.IOUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class APICredentialsTab extends Tab {

    private static final int MINIMUM_MARGIN = 12;

    public APICredentialsTab() {

        setText("API Credentials");
        setClosable(false);

        APICredentialConfig apiCredentialConfig = IOUtils.getAPICredentialConfig();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(3);
        gridPane.setVgap(2);
        gridPane.setPadding(new Insets(MINIMUM_MARGIN * 2));

        Label headerLabel = new Label("Enter your MathpixOCR API credentials below");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setMargin(headerLabel, new Insets(0, MINIMUM_MARGIN, MINIMUM_MARGIN, 0));
        gridPane.add(headerLabel, 0, 0, 2, 1);

        gridPane.add(new Label("APP ID:"), 0, 1);

        TextField idTextField = new TextField();
        idTextField.setPromptText("APP ID");
        idTextField.setText(apiCredentialConfig.getAppId());
        idTextField.setPrefWidth(300);
        GridPane.setMargin(idTextField, new Insets(MINIMUM_MARGIN));

        idTextField.textProperty().addListener((observable, oldValue, newValue) -> IOUtils.setAppId(newValue));

        // moves the caret to after the last char of the text
        idTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(idTextField::end);
            }
        });

        gridPane.add(idTextField, 1, 1);

        gridPane.add(new Label("APP Key:"), 0, 2);

        TextField keyTextField = new TextField();
        keyTextField.setPromptText("APP Key");
        keyTextField.setText(apiCredentialConfig.getAppKey());
        keyTextField.setPrefWidth(300);
        GridPane.setMargin(keyTextField, new Insets(MINIMUM_MARGIN));

        keyTextField.textProperty().addListener((observable, oldValue, newValue) -> IOUtils.setAppKey(newValue));

        keyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(keyTextField::end);
            }
        });

        gridPane.add(keyTextField, 1, 2);

        setContent(gridPane);

    }

}
