package com.cai.rxhttplib.convert;

/**
 * 用于把返回的数据解析成具体的类
 * 加这个才能使用getGenericSuperclass()获取到泛型类型
 * 否则需要传入一个Class用于解析
 * @param <T>
 */
public abstract class Converter<T> {

    public abstract T convertResponse(String s);
}
