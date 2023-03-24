package com.example.mobile.driverlicense.mDoc.mapper;

import java.util.Map;

import co.nstant.in.cbor.model.DataItem;

public interface NamespaceDataMapperInf<T> {
    public Map<String, DataItem> map(T obj);
}
