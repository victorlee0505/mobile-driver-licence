package com.example.mobile.driverlicense.mDoc.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;

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

    /**
     * This is for testing only
     * 
     * @return
     */
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(this.cryptographicAlg);
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(this.cryptographicSpec);
            keyPairGenerator.initialize(ecSpec);

            // Generate a new key pair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    public PrivateKey loadPrivateKey(String privateKeyPath) {
        try {
            byte[] privateKeyBytes = Files.readAllBytes(Paths.get(privateKeyPath));

            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(privateKeySpec.getAlgorithm());
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    public PublicKey getPublicKeyFromPrivateKey(byte[] privateKeyBytes) {
        try {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(privateKeySpec.getAlgorithm());
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Generate the corresponding public key
            return keyFactory.generatePublic(new ECPublicKeySpec(
                    ((ECPrivateKey) privateKey).getParams().getGenerator(), ((ECPrivateKey) privateKey).getParams()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Unexpected error", e);
        }
    }

    public static void main(String[] args) {
        CryptoKeyMaker maker = new CryptoKeyMaker(CryptographicProtocols.ELLIPTIC_CURVE, CryptographicProtocols.SECP256R1);
        PublicKey pub = maker.getPublicKeyFromIntegers();
        System.out.println(pub.toString());

        KeyPair keyPair = maker.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        PublicKey publicKey = maker.getPublicKeyFromPrivateKey(privateKey.getEncoded());

        System.out.println("publicKey: " + publicKey.toString());

        PublicKey publicKey2 = maker.getPublicKeyFromPrivateKey(privateKey.getEncoded());

        System.out.println("publicKey2: " + publicKey2.toString());
    }
}
