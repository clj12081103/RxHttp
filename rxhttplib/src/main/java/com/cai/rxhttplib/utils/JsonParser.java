package com.cai.rxhttplib.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static String toJsonStr(Object obj) {
        return new Gson().toJson(obj);
//        return JSON.toJSONString(obj);
    }


    public static <T> T parseObject(String str, Class<T> clazz) {
        return new Gson().fromJson(str, clazz);
//         return JSON.parseObject(str,clazz);
    }

    public static <E> List<E> parseArray(String str, Class<E> clazz) {
        // return new Gson().fromJson(str,new TypeToken<List<E>>() {}.getType());
//         return JSON.parseArray(str,clazz);
        Type type = TypeToken.getParameterized(ArrayList.class, clazz).getType();
        return new Gson().fromJson(str, type);
    }
}
