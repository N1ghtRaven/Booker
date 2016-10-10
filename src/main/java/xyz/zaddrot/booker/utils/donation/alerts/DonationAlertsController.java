package xyz.zaddrot.booker.utils.donation.alerts;

import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import xyz.zaddrot.booker.utils.conf.ConfigController;
import xyz.zaddrot.booker.utils.donation.alerts.util.URL;

import java.net.URISyntaxException;

/**
 * Created by night on 20.08.2016.
 */
public class DonationAlertsController {
    private final Logger logger = LogManager.getLogger(getClass());
    final private String token = new ConfigController().getAuth().getToken().getToken();
    final private Listener donationListener = new Listener();
    final private JSONObject jsonAuth;
    private static Socket socket = null;

    public DonationAlertsController(){
        try {
            socket = IO.socket(URL.CONNECT_SERVER.toString());
        } catch (URISyntaxException e) { logger.error(e); }

        socket.io().reconnection(true);
        socket.io().reconnectionAttempts(0);
        socket.io().reconnectionDelay(5000);

        jsonAuth = new JSONObject();
        jsonAuth.put("token", token);
        jsonAuth.put("type", "alert_widget");

        socket.emit("add-user", jsonAuth);
        socket.on("reconnect", args -> socket.emit("add-user", jsonAuth));

        socket.on("donation", donationListener::onDonation);
        socket.on("connect_timeout", donationListener::onTimeout);
    }

    public boolean isConnected(){
        return socket.connected();
    }

    public void startListener(){
        socket.connect();
    }
    public void stopListener(){
        if(isConnected()) {
            socket.disconnect();
            socket.close();
        }
    }
}
