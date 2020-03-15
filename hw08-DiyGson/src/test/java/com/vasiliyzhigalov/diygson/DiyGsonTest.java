package com.vasiliyzhigalov.diygson;

import com.google.gson.Gson;
import com.vasiliyzhigalov.POJOs.DemoPOJO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("DiyGson должен ")
public class DiyGsonTest {
    DemoPOJO testPOJO;
    DiyGson diyGson;
    Gson gson;

    @BeforeEach
    void setUp() {
        List<String> strings = new ArrayList<>();
        strings.add("121345");
        strings.add("54321");
        String[] stringsArr = {"a", "b"};
        testPOJO = new DemoPOJO("str", 12, 'q', strings, stringsArr);
        diyGson = new DiyGson();
        gson = new Gson();
    }

    @DisplayName("должен вернуть true если при сериализации через diyGson и десериализации через gson")
    @Test
    void shouldGetTrueIfSerializedThroughDIYGsonAndDeserializedThroughGson() {
        String diyGsonStr = diyGson.toJson(testPOJO);
        DemoPOJO pojo = gson.fromJson(diyGsonStr, DemoPOJO.class);
        assertThat(pojo).isEqualTo(testPOJO);
    }

    @Test
    @DisplayName("должен пройти тесты для сериализации примитивов")

    public void primitivesTest() throws IllegalAccessException {
        var gson = new Gson();
        assertEquals(gson.toJson(null), diyGson.toJson(null));
        assertEquals(gson.toJson((byte) 1), diyGson.toJson((byte) 1));
        assertEquals(gson.toJson((short) 1f), diyGson.toJson((short) 1f));
        assertEquals(gson.toJson(1), diyGson.toJson(1));
        assertEquals(gson.toJson(1L), diyGson.toJson(1L));
        assertEquals(gson.toJson(1f), diyGson.toJson(1f));
        assertEquals(gson.toJson(1d), diyGson.toJson(1d));
        assertEquals(gson.toJson("aaa"), diyGson.toJson("aaa"));
        assertEquals(gson.toJson('a'), diyGson.toJson('a'));
        assertEquals(gson.toJson(new int[]{1, 2, 3}), diyGson.toJson(new int[]{1, 2, 3}));
        assertEquals(gson.toJson(List.of(1, 2, 3)), diyGson.toJson(List.of(1, 2, 3)));
        assertEquals(gson.toJson(Collections.singletonList(1)), diyGson.toJson(Collections.singletonList(1)));
    }
}
