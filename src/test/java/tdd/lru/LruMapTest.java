package tdd.lru;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LruMapTest {

    LruMap<String, String> map = new LruMap<String, String>();

    @Test
    public void 上限に達さない場合は全部入ったまま() throws Exception {
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        map.put("KEY3", "VALUE3");
        assertEquals(3, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals("VALUE2", map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));
    }

}
