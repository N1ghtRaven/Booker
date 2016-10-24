package xyz.zaddrot.booker.utils.donation.gg;

import java.net.URI;

/**
 * Created by NightBook on 18.10.2016.
 */
enum Const {
    CONST("ws://chat.goodgame.ru:8081/chat/websocket");

    final private String req;
    Const(String req){
        this.req = req;
    }

    @Override
    public String toString(){
        return req;
    }


    public URI toURI(){
        return URI.create(req);
    }
}
