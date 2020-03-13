package com.vasiliyzhigalov.diygson.JsonElements;

import java.util.Collection;

public class JsonElement {
    public JsonElement() {
    }

    public JsonElement create(Object obj) {
        if (obj == null) {
            return new JsonNull();
        } else if (isJsonPrimitive(obj)) {
            return new JsonPrimitive(obj);
        } else if (isJsonArray(obj)) {
            return new JsonArray(obj);
        } else {
            return new JsonObject(obj);
        }
    }


    private boolean isJsonArray(Object object) {
        Class clazz = object.getClass();
        return clazz.isArray() || Collection.class.isAssignableFrom(clazz);
    }


    private boolean isJsonPrimitive(Object object) {
        Class clazz = object.getClass();
        return clazz.isPrimitive() || Number.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz) || String.class.isAssignableFrom(clazz);
    }

    public String toJson() {
        throw new UnsupportedOperationException();
    }

    ;
}
