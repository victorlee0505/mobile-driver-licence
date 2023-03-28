package com.example.mobile.driverlicense.mDoc.utils;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import com.example.mobile.driverlicense.mDoc.constant.CryptographicProtocols;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * Please use defined constant in CryptographicProtocols for algorithm and spec to instantiate KeyMaker.
 */
public class CryptoKeyMaker {

    private String cryptographicAlg;
    private String cryptographicSpec;

    /**
     * Generate a BigInteger for the use of x, y coordinates with defined algorithm
     * @return
     */
    public BigInteger generateBigInteger() {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance(this.cryptographicAlg);
            params.init(new ECGenParameterSpec(this.cryptographicSpec));
            ECParameterSpec ecParameters = params.getParameterSpec(ECParameterSpec.class);
            SecureRandom random = new SecureRandom();
            return new BigInteger(ecParameters.getCurve().getField().getFieldSize(), random);
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    /**
     * Generate a PublicKey with randomized x, y coordinates
     * @return
     */
    public PublicKey getPublicKeyFromIntegers() {
        BigInteger x = generateBigInteger();
        BigInteger y = generateBigInteger();
        return getPublicKeyFromIntegers(x, y);
    }

    /**
     * Generate a PublicKey
     * @param x
     * @param y
     * @return
     */
    public PublicKey getPublicKeyFromIntegers(@NonNull BigInteger x, @NonNull BigInteger y) {
        try {
            AlgorithmParameters params = AlgorithmParameters.getInstance(this.cryptographicAlg);
            params.init(new ECGenParameterSpec(this.cryptographicSpec));
            ECParameterSpec ecParameters = params.getParameterSpec(ECParameterSpec.class);

            ECPoint ecPoint = new ECPoint(x, y);
            ECPublicKeySpec keySpec = new ECPublicKeySpec(ecPoint, ecParameters);
            KeyFactory kf = KeyFactory.getInstance(CryptographicProtocols.ELLIPTIC_CURVE);
            ECPublicKey ecPublicKey = (ECPublicKey) kf.generatePublic(keySpec);
            return ecPublicKey;
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException | InvalidKeySpecException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    // public static void main(String[] args) {
    //     CryptoKeyMaker maker = new CryptoKeyMaker(CryptographicProtocols.ELLIPTIC_CURVE, CryptographicProtocols.SECP256R1);
    //     PublicKey pub = maker.getPublicKeyFromIntegers();
    //     System.out.println(pub.toString());
    // }
}
