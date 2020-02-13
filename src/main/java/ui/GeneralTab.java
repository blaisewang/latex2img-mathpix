package ui;

import io.IOUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class GeneralTab extends Tab {

    private static final int MINIMUM_MARGIN = 5;
    private static final int PANEL_MARGIN = 20;

    private final static String ORIGINAL_RESULT = "e^{\\pi}";

    public GeneralTab() {

        setText(" General ");
        setClosable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(5);
        gridPane.setVgap(4);
        gridPane.setPadding(new Insets(PANEL_MARGIN, PANEL_MARGIN + MINIMUM_MARGIN, PANEL_MARGIN, PANEL_MARGIN));

        Label headerLabel = new Label("Formatting");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        GridPane.setMargin(headerLabel, new Insets(0, MINIMUM_MARGIN, MINIMUM_MARGIN, 0));
        gridPane.add(headerLabel, 0, 0, 4, 1);

        String initialThirdResult = IOUtils.thirdResultWrapper(ORIGINAL_RESULT).replace("\n", "");

        Label thirdResult = new Label(initialThirdResult);
        thirdResult.setFont(Font.font(14));
        GridPane.setMargin(thirdResult, new Insets(MINIMUM_MARGIN, MINIMUM_MARGIN, MINIMUM_MARGIN, 0));
        gridPane.add(thirdResult, 0, 1, 4, 1);
        final ToggleGroup thirdWrapperOptions = new ToggleGroup();

        List<RadioButton> thirdWrapperOptionList = Arrays.asList(
                new RadioButton("\\begin{equation*}.."),
                new RadioButton("\\begin{align*}.."),
                new RadioButton("$$ .. $$"),
                new RadioButton("\\[ .. \\]")
        );

        AtomicInteger thirdOption = new AtomicInteger(IOUtils.getThirdResultWrapperOption());
        thirdWrapperOptionList.get(thirdOption.get()).setSelected(true);

        thirdWrapperOptions.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            thirdOption.set(thirdWrapperOptions.getToggles().indexOf(thirdWrapperOptions.getSelectedToggle()));
            IOUtils.setThirdResultWrapperOption(thirdOption.get());
            thirdResult.setText(IOUtils.thirdResultWrapper(ORIGINAL_RESULT).replace("\n", ""));

        });

        for (int i = 0; i < thirdWrapperOptionList.size(); i++) {
            RadioButton radioButton = thirdWrapperOptionList.get(i);
            radioButton.setToggleGroup(thirdWrapperOptions);
            GridPane.setMargin(radioButton, new Insets(0, MINIMUM_MARGIN, 3 * MINIMUM_MARGIN, MINIMUM_MARGIN));
            gridPane.add(radioButton, i, 2);
        }

        String initialFourthResult = IOUtils.fourthResultWrapper(ORIGINAL_RESULT).replace("\n", "");

        Label fourthResult = new Label(initialFourthResult);
        fourthResult.setFont(Font.font(14));
        GridPane.setMargin(fourthResult, new Insets(MINIMUM_MARGIN, MINIMUM_MARGIN, MINIMUM_MARGIN, 0));
        gridPane.add(fourthResult, 0, 3, 4, 1);

        final ToggleGroup fourthWrapperOptions = new ToggleGroup();

        List<RadioButton> fourthWrapperOptionList = Arrays.asList(
                new RadioButton("\\begin{equation}.."),
                new RadioButton("\\begin{align}..")
        );

        AtomicInteger fourthOption = new AtomicInteger(IOUtils.getFourthResultWrapperOption());
        fourthWrapperOptionList.get(fourthOption.get()).setSelected(true);

        fourthWrapperOptions.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {

            fourthOption.set(fourthWrapperOptions.getToggles().indexOf(fourthWrapperOptions.getSelectedToggle()));
            IOUtils.setFourthResultWrapperOption(fourthOption.get());
            fourthResult.setText(IOUtils.fourthResultWrapper(ORIGINAL_RESULT).replace("\n", ""));

        });

        for (int i = 0; i < fourthWrapperOptionList.size(); i++) {
            RadioButton radioButton = fourthWrapperOptionList.get(i);
            radioButton.setToggleGroup(fourthWrapperOptions);
            GridPane.setMargin(radioButton, new Insets(0, MINIMUM_MARGIN, MINIMUM_MARGIN, MINIMUM_MARGIN));
            gridPane.add(radioButton, i, 4);
        }

        setContent(gridPane);

    }

}
