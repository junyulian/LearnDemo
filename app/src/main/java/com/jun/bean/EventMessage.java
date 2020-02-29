package com.jun.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EventMessage implements Serializable {

    private int type;
    private Map map;

    public EventMessage(int type,Map map){
        this.type = type;
        this.map = map;
    }

    public int getType() {
        return type;
    }

    public Map getMap() {
        return map;
    }

    public String getString(String key){
        if(map.containsKey(key)){
            return (String) map.get(key);
        }
        return "";
    }

    public int getInt(String key){
        if(map.containsKey(key)){
            return (int)map.get(key);
        }
        return 0;
    }

    //<T> 声明此方法为泛型方法
    public <T> T getObject(String key,Class<T> clazz){
        if(map.containsKey(key)){
            return (T)map.get(key);
        }
        return null;
    }

    public <T> List<T> getList(String key, Class<T> clazz){
        if(map.containsKey(key)){
            return (List<T>)map.get(key);
        }
        return null;
    }
}
