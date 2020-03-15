package com.vasiliyzhigalov.diygson;

import com.vasiliyzhigalov.diygson.JsonElements.JsonElement;
import com.vasiliyzhigalov.diygson.JsonElements.JsonObject;

public class DiyGson {
    public DiyGson() {
    }
    public String toJson(Object object){
        JsonElement jsonElement= new JsonElement();
        jsonElement = jsonElement.create(object);
        return jsonElement.toJson();
    }
}
