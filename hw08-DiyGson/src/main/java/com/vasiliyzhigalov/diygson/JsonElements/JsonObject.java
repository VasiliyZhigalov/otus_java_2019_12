package com.vasiliyzhigalov.diygson.JsonElements;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonObject extends JsonElement {
    private Map<String, JsonElement> elementMap;

    public JsonObject(Object object) {
        elementMap = new HashMap<>();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field currentField : fields) {
            currentField.setAccessible(true);
            try {
                if (currentField.get(object) != null)
                    elementMap.put(currentField.getName(), create(currentField.get(object)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        int index = 0;
        for (String element : elementMap.keySet()) {
            stringBuilder.append("\"").append(element).append("\":");
            stringBuilder.append(elementMap.get(element).toJson());
            if (index < elementMap.size() - 1)
                stringBuilder.append(",");
            index++;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
