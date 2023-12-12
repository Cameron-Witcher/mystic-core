package net.mysticcloud.spigot.core.utils.accounts;

import org.bukkit.Bukkit;
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

    public void putData(String key, Object value){
        data.put(key, value);
    }

    public Object getData(String key){
        return data.has(key) ? data.get(key) : null;
    }
    public int getInt(String key){
        return data.has(key) ? data.getInt(key) : 0;
    }
    public String getString(String key){
        return data.has(key) ? data.getString(key) : "";
    }

}
