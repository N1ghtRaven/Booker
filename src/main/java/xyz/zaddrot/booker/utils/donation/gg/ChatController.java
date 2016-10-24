package xyz.zaddrot.booker.utils.donation.gg;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

/**
 * Created by NightBook on 18.10.2016.
 */
public class ChatController {
    private final Logger logger = LogManager.getLogger(getClass());
    private final Object waitLock = new Object();
    private static long channel;
    private void wait4TerminateSignal() {
        synchronized(waitLock){
            try {
                waitLock.wait();
            } catch (InterruptedException ignore) {}
        }
    }

    private static Session goodgame;

    public void start(){
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            goodgame = container.connectToServer(getClass(), Const.CONST.toURI());
            goodgame.addMessageHandler(new Handler());

            //TODO: АПИ ЧАТА ГГ
            channel = 0; //ID канала

            JSONObject resultJson = new JSONObject();
            JSONObject dataJSON = new JSONObject();

            dataJSON.put("site_id", 1);
            //dataJSON.put("user_id", chat.getUser_ID());
            //dataJSON.put("token", chat.getToken());
            resultJson.put("type", "auth");
            resultJson.put("data", dataJSON);
            goodgame.getBasicRemote().sendText(resultJson.toJSONString());

            resultJson = new JSONObject();
            dataJSON = new JSONObject();

            dataJSON.put("channel_id", channel);
            dataJSON.put("hidden", true);
            dataJSON.put("mobile", 0);
            resultJson.put("type", "join");
            resultJson.put("data", dataJSON);
            goodgame.getBasicRemote().sendText(resultJson.toJSONString());
        } catch (DeploymentException | IOException e) { logger.error(e); }
        wait4TerminateSignal();
        if(goodgame != null){ try { goodgame.close(); } catch (IOException ignore) { logger.error(ignore); }}
    }
    public void stop(){
        if(goodgame != null){
            try {
                goodgame.close();
            } catch (IOException ignore) { logger.error(ignore); }
        }
    }
}
