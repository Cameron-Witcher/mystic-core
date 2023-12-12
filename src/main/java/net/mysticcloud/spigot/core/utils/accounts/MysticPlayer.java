package net.mysticcloud.spigot.core.utils.accounts;

import org.json2.JSONObject;

public class MysticPlayer {

    JSONObject data;
    public MysticPlayer(JSONObject data) {
        this.data = data;
    }

    public MysticPlayer(){
        this.data = new JSONObject("{}");
        //Set defaults
    }

    public JSONObject getData(){
        return data;
    }
}
