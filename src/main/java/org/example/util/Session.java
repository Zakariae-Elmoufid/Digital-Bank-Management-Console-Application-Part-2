package org.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

public class  Session {
    private  static  Session instance;

    HashMap<String,Object> session = new HashMap<>();

    public  static Session getInstance(){
        if(instance==null){
            instance=new Session();
        }
        return instance;
    }

    public <T>  void setSession(String key, T value) {
          session.put(key, value);
    }

    public <T>  T getSession(String key ){
        return (T)session.get(key);
    }

    public void removeSession(String key) {
        session.remove(key);
    }

    public void clearSession() {
        session.clear();
    }


}
