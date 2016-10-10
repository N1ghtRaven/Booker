package xyz.zaddrot.booker.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import xyz.zaddrot.booker.utils.conf.ConfigController;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by NightBook on 04.10.2016.
 */
public class SettingApp{
    public TextField tokenField;
    public CheckBox mouthBox;
    public CheckBox dayBox;

    private static Stage settingStage;
    public Hyperlink tokenLink;
    public Spinner maxTop;

    @FXML
    protected void initialize() {
        initSpinner();

        ConfigController cfg = new ConfigController();
        tokenField.setText(cfg.getAuth().getToken().getToken());

        mouthBox.setSelected(cfg.getAuth().getProperties().isTopMounth());
        dayBox.setSelected(cfg.getAuth().getProperties().isTopDay());
    }

    private void initSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                int value = this.getValue();
                int step = 1;

                if ((value - step) < 1) this.setValue(1);
                else this.setValue(value - step);
            }

            @Override
            public void increment(int steps) {
                int value = this.getValue();
                int step = 1;

                if ((value - step) > 20) this.setValue(20);
                else this.setValue(value + step);
            }
        };
        valueFactory.setValue(5);

        maxTop.setValueFactory(valueFactory);
    }

    static Stage getSettingStage() {
        return settingStage;
    }
    static void setSettingStage(Stage settingStage) {
        SettingApp.settingStage = settingStage;
    }


    public void onSave(ActionEvent actionEvent) {
        ConfigController cfg = new ConfigController();
        cfg.getAuth().getToken().setToken(tokenField.getText());

        cfg.getAuth().getProperties().setTopDay(dayBox.isSelected());
        cfg.getAuth().getProperties().setTopMounth(mouthBox.isSelected());

        cfg.getAuth().dump();
        MainApp.trayIcon.displayMessage("Booker", "Настройки сохранены.", TrayIcon.MessageType.INFO);
    }

    public void onClear(ActionEvent actionEvent) {
        if(Path.BASE.toFile().exists()) Path.BASE.toFile().delete();
        if(Path.DAY_BASE.toFile().exists()) Path.DAY_BASE.toFile().delete();

        MainApp.trayIcon.displayMessage("Booker", "База очищена", TrayIcon.MessageType.INFO);
    }

    public void onGetToken(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("http://donationalerts.ru/dashboard/alert-widget"));
        }catch (IOException | URISyntaxException ignore){}
        MainApp.trayIcon.displayMessage("Booker", "Возьмите из вашего виджета оповежений (donationalerts.ru/widget/alerts?token=X) токен (в примере заменён на \"X\")", TrayIcon.MessageType.INFO);
    }


    public void onGetTokenSelected(MouseEvent mouseEvent) {
        tokenLink.setOpacity(0.6);
    }

    public void onGetTokenUnselected(MouseEvent mouseEvent) {
        tokenLink.setOpacity(0.3);
    }
}
