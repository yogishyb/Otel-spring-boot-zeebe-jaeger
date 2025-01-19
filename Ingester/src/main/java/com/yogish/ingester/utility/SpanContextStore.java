package com.yogish.ingester.utility;


import io.opentelemetry.context.Context;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SpanContextStore {
    private final HashMap<String, Context> contextHashMap=new HashMap<>();

    public void addContext(String key,Context context){
        contextHashMap.put(key,context);
    }

    public Context getContext(String key){
        return  contextHashMap.get(key);
    }
}
