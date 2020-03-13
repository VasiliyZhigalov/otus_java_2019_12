package com.vasiliyzhigalov.diygson.JsonElements;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsonArray extends JsonElement {
    List<JsonElement> jsonElements = new ArrayList<>();

    public JsonArray(Object object) {
        List<Object> objectList;
        if (object.getClass().isArray()) {
            int length = Array.getLength(object);
            for (int i = 0; i < length; i++) {
                JsonElement jse = create(Array.get(object, i));
                add(jse);
            }
        } else {
            objectList = new ArrayList<>((Collection<?>) object);
            for (Object curObj : objectList) {
                JsonElement jse = create(curObj);
                add(jse);
            }
        }
    }

    public void add(Object object) {
        if (object == null) {
            throw new NullPointerException("object must be not null");
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
