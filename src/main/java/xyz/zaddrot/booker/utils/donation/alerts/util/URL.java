package xyz.zaddrot.booker.utils.donation.alerts.util;

/**
 * Created by night on 20.08.2016.
 */
public enum URL {
    CONNECT_SERVER("http://socket.donationalerts.ru:3001");

    final private String url;
    URL(String url){
        this.url = url;
    }

    @Override
    public String toString(){
        return url;
    }
}
