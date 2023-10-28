package com.ttknpdev.springbootonetomanyh2.services.one;

import java.util.Map;

public interface EmployeeService <T>{
    Iterable<T> reads();
    T read(String eid);
    T create(T obj);
    T update(String eid , T obj);
    T updateForTesting(String eid , T obj);
    Map<String,T> delete(String eid);
    Map<String,T> deleteForTesting(String eid);
}
