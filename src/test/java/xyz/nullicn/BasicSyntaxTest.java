package xyz.nullicn;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

@DisplayName("Java基础语法测试")
public class BasicSyntaxTest {

    @Test
    @DisplayName("HashMap测试")
    public void hashMapTest() {
        HashMap<String,String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Set<String> keys = map.keySet();
        System.out.println(keys);
        String r1 = map.get("key1");
        System.out.println(r1);
    }
}
