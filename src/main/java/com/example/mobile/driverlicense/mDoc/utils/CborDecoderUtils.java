package com.example.mobile.driverlicense.mDoc.utils;

import java.io.ByteArrayInputStream;
import java.util.List;

import co.nstant.in.cbor.CborDecoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import lombok.NonNull;

public class CborDecoderUtils {
    
    public static DataItem cborDecode(@NonNull byte[] encodedBytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(encodedBytes);
        List<DataItem> dataItems = null;
        try {
            dataItems = new CborDecoder(bais).decode();
        } catch (CborException e) {
            throw new IllegalArgumentException("Error decoding CBOR", e);
        }
        if (dataItems.size() != 1) {
            throw new IllegalArgumentException("Unexpected number of items, expected 1 got "
                    + dataItems.size());
        }
        return dataItems.get(0);
    }

    public static List<DataItem> cborDecodeAll(@NonNull byte[] encodedBytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(encodedBytes);
        List<DataItem> dataItems = null;
        try {
            dataItems = new CborDecoder(bais).decode();
        } catch (CborException e) {
            throw new IllegalArgumentException("Error decoding CBOR", e);
        }
        return dataItems;
    }
}
