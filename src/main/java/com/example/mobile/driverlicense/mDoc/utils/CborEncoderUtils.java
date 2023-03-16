package com.example.mobile.driverlicense.mDoc.utils;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnicodeString;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CborEncoderUtils {

    private static byte[] cborEncode(@NonNull DataItem dataItem) {
        return cborEncodeWithoutCanonicalizing(dataItem);
    }

    private static byte[] cborEncodeWithoutCanonicalizing(@NonNull DataItem dataItem) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            new CborEncoder(baos).nonCanonical().encode(dataItem);
        } catch (CborException e) {
            // This should never happen and we don't want cborEncode() to throw since that
            // would complicate all callers. Log it instead.
            log.error("Unexpected failure encoding data", e);
            throw new IllegalStateException("Unexpected failure encoding data", e);
        }
        return baos.toByteArray();
    }
    
    /**
     * RFC8610 "bool"  Boolean value (major type 7, additional information 20 or 21).
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeBoolean(boolean value) {
        return cborEncode(new CborBuilder().add(value).build().get(0));
    }

    /**
     * RFC8610 "tstr" or "text"  Text string (major type 3).
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeString(@NonNull String value) {
        return cborEncode(new CborBuilder().add(value).build().get(0));
    }

    /**
     * RFC8610 "uint"  An unsigned integer (major type 0). In JAVA it is long type.
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeNumber(long value) {
        return cborEncode(new CborBuilder().add(value).build().get(0));
    }

    /**
     * RFC8610 "bstr" or "bytes"  A byte string (major type 2). 
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeBytestring(@NonNull byte[] value) {
        return cborEncode(new CborBuilder().add(value).build().get(0));
    }

    /**
     * RFC8943 full-date = #6.1004(tstr)
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeDateTime(@NonNull LocalDateTime localDateTime) {
        return cborEncode(cborBuildDateTime(localDateTime));
    }

    /**
     * Returns full-date = #6.1004(tstr) where tag 1004 is specified in RFC 8943.
     */
    private static DataItem cborBuildDateTime(@NonNull LocalDateTime localDateTime) {
        String dateString = localDateTime.atZone(null).format(DateTimeFormatter.ISO_DATE_TIME);
        DataItem dataItem = new UnicodeString(dateString);
        dataItem.setTag(1004);
        return dataItem;
    }
}