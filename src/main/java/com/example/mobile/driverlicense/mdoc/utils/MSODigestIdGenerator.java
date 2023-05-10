package com.example.mobile.driverlicense.mdoc.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MSODigestIdGenerator {
    // 9.1.2.4 Digest ID max value shall be smaller than 2^31
    private static final int MAX_DIGEST_ID = Integer.MAX_VALUE;

    public static Long generateDigestId(String algorithm, byte[] data) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        byte[] hash = digest.digest(data);
        int truncatedDigest = ((hash[0] & 0xFF) << 24) |
                ((hash[1] & 0xFF) << 16) |
                ((hash[2] & 0xFF) << 8) |
                ((hash[3] & 0xFF) << 0);
        return Long.valueOf(Math.abs(truncatedDigest % MAX_DIGEST_ID));
    }
}