package com.vasiliyzhigalov.diygson.JsonElements;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonElement {
    List<JsonElement> jsonElements = new ArrayList<>();

    public JsonArray(Object object) {
        List<Object> objectList;
        if (object.getClass().isArray()) {
            Object[] objects = (Object[]) object;
            for (Object curObj : objects) {
                JsonElement jse = create(curObj);
                add(jse);
            }
        } else {
            objectList = (ArrayList) object;
            for (Object curObj : objectList) {
                JsonElement jse = create(curObj);
                add(jse);
            }
        }
    }

    public void add(Object object) {
        if (object == null) {
            throw new NullPointerException("Null object");
        }
        this.jsonElements.add((JsonElement) object);
    }

    @Override
    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int index = 0;
        for (JsonElement obj : jsonElements) {
            stringBuilder.append(obj.toJson());
            if (index < jsonElements.size() - 1)
                stringBuilder.append(",");
            index++;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
