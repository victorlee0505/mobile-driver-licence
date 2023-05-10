package com.example.mobile.driverlicense.mdoc.utils;

import java.util.Locale;

public class BinaryUtils {
    
    public static byte[] fromHex(String stringWithHex) {
        int stringLength = stringWithHex.length();
        if ((stringLength % 2) != 0) {
            throw new IllegalArgumentException("Invalid length of hex string: " + stringLength);
        }
        int numBytes = stringLength / 2;
        byte[] data = new byte[numBytes];
        for (int n = 0; n < numBytes; n++) {
            String byteStr = stringWithHex.substring(2 * n, 2 * n + 2);
            data[n] = (byte) Integer.parseInt(byteStr, 16);
        }
        return data;
    }

    public static String toHex(byte[] bytes) {
        return toHex(bytes, 0, bytes.length);
    }

    public static String toHex(byte[] bytes, int from, int to) {
        if (from < 0 || to > bytes.length || from > to) {
            String msg = String.format(Locale.US, "Expected 0 <= from <= to <= %d, got %d, %d.",
                    bytes.length, from, to);
            throw new IllegalArgumentException(msg);
        }
        StringBuilder sb = new StringBuilder();
        for (int n = from; n < to; n++) {
            byte b = bytes[n];
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}