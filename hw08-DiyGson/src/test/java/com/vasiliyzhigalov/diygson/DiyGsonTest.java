package com.vasiliyzhigalov.diygson;

import com.google.gson.Gson;
import com.vasiliyzhigalov.POJOs.DemoPOJO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    void shouldGetTrueIfSerializedThroughDIYGsonAndDeserializedThroughGson(){
        String diyGsonStr = diyGson.toJson(testPOJO);
        DemoPOJO pojo = gson.fromJson(diyGsonStr, DemoPOJO.class);
        assertThat(pojo).isEqualTo(testPOJO);
    }
}
