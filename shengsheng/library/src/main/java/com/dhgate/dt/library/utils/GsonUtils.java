package com.dhgate.dt.library.utils;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.dhgate.dt.library.BuildConfig;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GsonUtils {

    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setLenient()// json宽松
                    .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                    .serializeNulls() //智能null
                    .setPrettyPrinting()// 调教格式
                    .disableHtmlEscaping() //默认是GSON把HTML 转义的
                    .registerTypeAdapter(int.class, new JsonDeserializer<Integer>() {//根治服务端int 返回""空字符串
                        @Override
                        public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                            //try catch不影响效率
                            try {
                                return json.getAsInt();
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }
                    })
                    .create();
        }
    }

    public static Gson getGson() {
        return gson;
    }

    /**
     * 将json字符串解析成实体对象
     *
     * @param json
     * @param cls
     * @return T
     */
    public static <T> T jsonToEntity(String json, Class<T> cls) {
        if (BuildConfig.DEBUG) {
            LogUtils.e(json);
        }
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, cls);
        } catch (Exception e) {

            LogUtils.e(e.getMessage());
        }
        return t;
    }

    /**
     * 将json字符串解析成集合
     *
     * @param json
     * @param cls
     * @return ArrayList<T>
     */
    public static <T> ArrayList<T> jsonToList(String json, Class<T> cls) {
        if (BuildConfig.DEBUG) {
            LogUtils.e(json);
        }
        Gson gson = new Gson();
        ArrayList<T> mList = new ArrayList<T>();
        try {
            JsonArray array = new JsonParser().parse(json).getAsJsonArray();
            for (final JsonElement elem : array) {
                mList.add(gson.fromJson(elem, cls));
            }
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return mList;
    }

    /**
     * 将json字符串解析成map
     *
     * @param json
     * @return List<Map < String , Object>>
     */
    public static List<Map<String, Object>> jsonToMap(String json) {
        if (BuildConfig.DEBUG) {
            LogUtils.e(json);
        }
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return list;
    }

    /**
     * 将对象实体类转换为Json字符串
     *
     * @param o
     * @return
     */
    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
