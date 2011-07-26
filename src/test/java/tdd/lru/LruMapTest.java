package tdd.lru;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LruMapTest {

    LruMap<String, String> map = new LruMap<String, String>(3);

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

    @Test
    public void 登録してない要素を取得するとnullが返る() throws Exception {
        map.put("KEY1", "VALUE1");
        assertEquals(1, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals(null, map.get("KEY2"));
    }

    @Test
    public void 上限を超えると追加する度に古い要素が削除されていく() throws Exception {
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        map.put("KEY3", "VALUE3");
        assertEquals(3, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals("VALUE2", map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));

        map.put("KEY4", "VALUE4");
        assertEquals(3, map.size());
        assertEquals(null, map.get("KEY1"));
        assertEquals("VALUE2", map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));
        assertEquals("VALUE4", map.get("KEY4"));

        map.put("KEY5", "VALUE5");
        assertEquals(3, map.size());
        assertEquals(null, map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));
        assertEquals("VALUE4", map.get("KEY4"));
        assertEquals("VALUE5", map.get("KEY5"));
    }

    @Test
    public void 古いイコール最も参照されなかった要素のこと_参照することで延命される() throws Exception {
        map.put("KEY1", "VALUE1");
        map.put("KEY2", "VALUE2");
        map.put("KEY3", "VALUE3");
        assertEquals(3, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals("VALUE2", map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));

        assertEquals("VALUE1", map.get("KEY1"));

        map.put("KEY4", "VALUE4");
        assertEquals(3, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals(null, map.get("KEY2"));
        assertEquals("VALUE3", map.get("KEY3"));
        assertEquals("VALUE4", map.get("KEY4"));

        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals("VALUE3", map.get("KEY3"));

        map.put("KEY5", "VALUE5");
        assertEquals(3, map.size());
        assertEquals("VALUE1", map.get("KEY1"));
        assertEquals("VALUE3", map.get("KEY3"));
        assertEquals(null, map.get("KEY4"));
        assertEquals("VALUE5", map.get("KEY5"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void 上限が0のマップを作成しようとすると例外をスローする() throws Exception {
        new LruMap<String, String>(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void 上限が負のマップを作成しようとすると例外をスローする() throws Exception {
        new LruMap<String, String>(-1);
    }
}
