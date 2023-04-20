package com.example.mobile.driverlicense.mDoc.utils;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

import org.bouncycastle.util.BigIntegers;

import co.nstant.in.cbor.CborBuilder;
import co.nstant.in.cbor.CborEncoder;
import co.nstant.in.cbor.CborException;
import co.nstant.in.cbor.model.DataItem;
import co.nstant.in.cbor.model.UnicodeString;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CborEncoderUtils {

    private static final long COSE_KEY_KTY = 1;
    private static final long COSE_KEY_TYPE_EC2 = 2;
    private static final long COSE_KEY_EC2_CRV = -1;
    private static final long COSE_KEY_EC2_X = -2;
    private static final long COSE_KEY_EC2_Y = -3;
    private static final long COSE_KEY_EC2_CRV_P256 = 1;

    /* Encodes an integer according to Section 2.3.5 Field-Element-to-Octet-String Conversion
     * of SEC 1: Elliptic Curve Cryptography (https://www.secg.org/sec1-v2.pdf).
     */
    public static byte[] sec1EncodeFieldElementAsOctetString(int octetStringSize, BigInteger fieldValue) {
        return BigIntegers.asUnsignedByteArray(octetStringSize, fieldValue);
    }

    public static DataItem cborBuildCoseKey(@NonNull PublicKey key) {
        ECPublicKey ecKey = (ECPublicKey) key;
        ECPoint w = ecKey.getW();
        byte[] x = sec1EncodeFieldElementAsOctetString(32, w.getAffineX());
        byte[] y = sec1EncodeFieldElementAsOctetString(32, w.getAffineY());
        DataItem item = new CborBuilder()
                .addMap()
                .put(COSE_KEY_KTY, COSE_KEY_TYPE_EC2)
                .put(COSE_KEY_EC2_CRV, COSE_KEY_EC2_CRV_P256)
                .put(COSE_KEY_EC2_X, x)
                .put(COSE_KEY_EC2_Y, y)
                .end()
                .build().get(0);
        return item;
    }

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
        String dateString = localDateTime.atZone(TimeZone.getTimeZone("UTC").toZoneId()).format(DateTimeFormatter.ISO_DATE_TIME);
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
