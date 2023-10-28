package com.ttknpdev.springbootonetomanyh2.services.many;

public interface AddressService <T>{
    Iterable<T> reads();
    Iterable<T> deleteAllByEmployeeId(String eid);
    Iterable<T> readsByEmployeeId(String eid);
    Iterable<T> deleteByAddressId(String aid);
    T read(String aid);
    // lines 10 and 11 it's same result
    T create(T obj , String eid); // I write query on my own (use Native Query)
    T createSecond(T obj , String eid); // using method of JpaRepository
    T createForTesting(T obj , String eid); // using method of JpaRepository
}