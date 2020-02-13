package ui;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;


public class PreferencesDialog {

    private Stage stage;
    private SingleSelectionModel<Tab> selectionModel;
    private Dialog<Boolean> dialog = new Dialog<>();

    public PreferencesDialog() {

        dialog.setTitle("Preferences");

        stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        APICredentialsTab apiCredentialsTab = new APICredentialsTab();
        GeneralTab generalTab = new GeneralTab();

        TabPane tabPane = new TabPane(generalTab, apiCredentialsTab);

        selectionModel = tabPane.getSelectionModel();

        Scene scene = new Scene(tabPane);

        stage.setAlwaysOnTop(true);

        stage.setScene(scene);

        stage.setOnCloseRequest(e -> stage.close());

    }

    public void show(int index) {

        if (!stage.isShowing()) {

            selectionModel.select(index);

            dialog.showAndWait();

        }

        stage.toFront();

    }

}
