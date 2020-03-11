package com.vasiliyzhigalov.diygson.JsonElements;

import java.util.Collection;

public abstract class JsonElement {

    public JsonElement create(Object obj) {
        if (obj == null) {
            throw new NullPointerException(obj.getClass().getName());
        }
        if (isJsonPrimitive(obj)) {
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

    public abstract String toJson();
}
