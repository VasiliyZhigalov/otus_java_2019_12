package com.vasiliyzhigalov.diygson.JsonElements;

public class JsonPrimitive extends JsonElement {
    private Object value;

    JsonPrimitive(Object object) {
        this.value = object;
    }


    @Override
    public String toJson() {
        if (value instanceof Number) {
            return value.toString();
        } else {
            return "\"" + value + "\"";
        }
    }
}
