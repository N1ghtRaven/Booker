package xyz.zaddrot.booker.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import xyz.zaddrot.booker.utils.backup.Backupper;
import xyz.zaddrot.booker.utils.backup.Dumper;
import xyz.zaddrot.booker.utils.donation.alerts.DonationAlertsController;
import xyz.zaddrot.booker.utils.donation.logger.DonationController;

import java.awt.*;
import java.awt.MenuItem;
import java.io.IOException;
import java.net.URL;

/**
 * Created by NightBook on 04.10.2016.
 */
public class MainApp extends Application {
    public TextField username;
    public Spinner<Integer> amount;
    final private DonationAlertsController DAcontroller = new DonationAlertsController();
    final private DonationController controller = new DonationController();
    public Text status;
    private Thread thread;
    static TrayIcon trayIcon;

    public static void main(String... args) {
        launch(args);
    }

    private static Stage mainStage;

    @FXML
    protected void initialize() {
        //Инициализация
        initSpinner();

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.mainStage = stage;

        initLayout();

        this.mainStage.show();
        trayHidding();
    }

    private void initLayout() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/main.fxml"));
        mainStage.setScene(new Scene(root));

        mainStage.setTitle("Booker");
        mainStage.setResizable(false);
        mainStage.setOnCloseRequest(event -> System.exit(0));
        Platform.setImplicitExit(false);
    }

    private void initSettingLayout() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/settings.fxml"));
        SettingApp.setSettingStage(new Stage());
        SettingApp.getSettingStage().setScene(new Scene(root));

        SettingApp.getSettingStage().setTitle("Setting");
        SettingApp.getSettingStage().setResizable(false);
        SettingApp.getSettingStage().setOnCloseRequest(e -> {
            SettingApp.getSettingStage().close();
            mainStage.show();
        });
        mainStage.hide();
        SettingApp.getSettingStage().show();
    }

    private void trayHidding(){
        if(SystemTray.isSupported()) {
            PopupMenu menu = new PopupMenu();

            MenuItem item = new MenuItem("Закрыть");
            item.addActionListener(e -> System.exit(0));

            MenuItem item2 = new MenuItem("Развернуть");
            item2.addActionListener(e -> Platform.runLater(() -> mainStage.show()));

            MenuItem item3 = new MenuItem("Свернуть");
            item3.addActionListener(e -> Platform.runLater(() -> {
                mainStage.hide();
                trayIcon.displayMessage("Booker", "Приложение сейчас свёрнуто, для развёртывания нажмите соответствующию кнопку", TrayIcon.MessageType.INFO);
            }));

            menu.add(item3);
            menu.add(item2);
            menu.add(item);

            URL imageURL = getClass().getResource("/icons/coins.png");
            Image icon = Toolkit.getDefaultToolkit().getImage(imageURL);

            trayIcon = new TrayIcon(icon, "Booker", menu);
            trayIcon.setImageAutoSize(true);

            SystemTray tray = SystemTray.getSystemTray();
            try{ tray.add(trayIcon); }catch (AWTException ignore) { ignore.printStackTrace();}
            //trayIcon.displayMessage("test", "test", TrayIcon.MessageType.INFO);
        }
    }

    private void initSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                int value = this.getValue();
                int step = 50;

                if ((value - step) < 0) this.setValue(0);
                else this.setValue(value - step);
            }

            @Override
            public void increment(int steps) {
                int value = this.getValue();
                int step = 50;

                if ((value - step) > 999999) this.setValue(999999);
                else this.setValue(value + step);
            }
        };
        valueFactory.setValue(100);

        amount.setValueFactory(valueFactory);
    }

    private void stopTelemetry() {
        if(!thread.isInterrupted()){
            DAcontroller.stopListener();
            thread.interrupt();
        }
    }

    private void startTelemetry() {
        thread = new Thread(() -> { if(!DAcontroller.isConnected()) DAcontroller.startListener(); });
        thread.start();
    }


    public void onSave(ActionEvent actionEvent) {
        new Backupper().backup();
        //TODO: Сохранение в JSON-Базу или в базу данных результат (- сложно бекапить)
        controller.createDonation(username.getText(), amount.getValue());
        status.setText("Ручная поддержка введена");
    }

    public void onBackup(ActionEvent actionEvent) {
        new Dumper().dump();
        controller.outRebuild();
        status.setText("Откат базы завершен успешно");
        MainApp.trayIcon.displayMessage("Booker", "База откачена.", TrayIcon.MessageType.INFO);
    }

    public void onSettings(ActionEvent actionEvent) throws IOException {
        //TODO: Запуск экрана настроек
        mainStage.close();
        initSettingLayout();
    }

    public void onTelemetry(ActionEvent actionEvent) {
        ToggleButton toggleButton = (ToggleButton) actionEvent.getSource();
        if (toggleButton.isSelected()) {
            MainApp.trayIcon.displayMessage("Booker", "Автоматический сбор данных включён.", TrayIcon.MessageType.INFO);
            status.setText("Автоматический сбор данных вкл.");
            toggleButton.setText("Закончить сбор данных");
            toggleButton.setTextFill(Paint.valueOf("#cc0000"));

            startTelemetry();
        } else {
            MainApp.trayIcon.displayMessage("Booker", "Автоматический сбор данных выключён.", TrayIcon.MessageType.INFO);
            status.setText("Автоматический сбор данных выкл.");
            toggleButton.setText("Начать сбор данных");
            toggleButton.setTextFill(Paint.valueOf("#00a610"));

            stopTelemetry();
        }
    }
}
