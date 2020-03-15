package com.vasiliyzhigalov.diygson.JsonElements;

public class JsonNull  extends JsonElement{

    public JsonNull() {
    }

    @Override
    public String toJson() {
        return "null";
    }
}
