package com.example.mobile.driverlicense.mDoc.utils;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnicodeString;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CborEncoderUtils {

    public static byte[] cborEncode(@NonNull DataItem dataItem) {
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
    public static DataItem cborBuildDateTime(@NonNull LocalDateTime localDateTime) {
        String dateString = localDateTime.atZone(null).format(DateTimeFormatter.ISO_DATE_TIME);
        DataItem dataItem = new UnicodeString(dateString);
        dataItem.setTag(1004);
        return dataItem;
    }

    // ====================== More than 1 item ===================================

    private static byte[] cborEncode(@NonNull List<DataItem> dataItem) {
        return cborEncodeWithoutCanonicalizing(dataItem);
    }

    private static byte[] cborEncodeWithoutCanonicalizing(@NonNull List<DataItem> dataItem) {
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
    public static byte[] cborEncodeBoolean(String accessControl, boolean value) {
        return cborEncode(new CborBuilder().add(accessControl).add(value).build());
    }

        /**
     * RFC8610 "tstr" or "text"  Text string (major type 3).
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeString(String accessControl, @NonNull String value) {
        return cborEncode(new CborBuilder().add(accessControl).add(value).build());
    }

    /**
     * RFC8610 "uint"  An unsigned integer (major type 0). In JAVA it is long type.
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeNumber(String accessControl, long value) {
        return cborEncode(new CborBuilder().add(accessControl).add(value).build());
    }

    /**
     * RFC8610 "bstr" or "bytes"  A byte string (major type 2). 
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeBytestring(String accessControl, @NonNull byte[] value) {
        return cborEncode(new CborBuilder().add(accessControl).add(value).build());
    }

    /**
     * RFC8943 full-date = #6.1004(tstr)
     * @param value
     * @return cborData byte[]
     */
    public static byte[] cborEncodeDateTime(String accessControl, @NonNull LocalDateTime localDateTime) {
        return cborEncode(cborBuildDateTime(accessControl, localDateTime));
    }

    /**
     * Returns full-date = #6.1004(tstr) where tag 1004 is specified in RFC 8943.
     */
    private static List<DataItem> cborBuildDateTime(String accessControl, @NonNull LocalDateTime localDateTime) {
        List<DataItem> dataItems = new CborBuilder().add(accessControl).build();
        String dateString = localDateTime.atZone(null).format(DateTimeFormatter.ISO_DATE_TIME);
        DataItem dataItem = new UnicodeString(dateString);
        dataItem.setTag(1004);
        dataItems.add(dataItem);
        return dataItems;
    }
}
