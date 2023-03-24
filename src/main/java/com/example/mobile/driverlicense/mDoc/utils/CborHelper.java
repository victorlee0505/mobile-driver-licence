package com.example.mobile.driverlicense.mDoc.utils;

import java.math.BigInteger;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.model.DataItem;

public class CborHelper {
    public static DataItem createDataItem(long value) {
        return new CborBuilder().add(value).build().get(0);
    }

    public static DataItem createDataItem(BigInteger value) {
        return new CborBuilder().add(value).build().get(0);
    }

    public static DataItem createDataItem(boolean value) {
        return new CborBuilder().add(value).build().get(0);
    }

    public static DataItem createDataItem(float value) {
        return new CborBuilder().add(value).build().get(0);
    }

    public static DataItem createDataItem(double value) {
        return new CborBuilder().add(value).build().get(0);
    }

    public static DataItem createDataItem(byte[] bytes) {
        return new CborBuilder().add(bytes).build().get(0);
    }

    public static DataItem createDataItem(String value) {
        return new CborBuilder().add(value).build().get(0);
    }
}
