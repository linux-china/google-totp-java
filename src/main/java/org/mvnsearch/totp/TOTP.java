package org.mvnsearch.totp;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * TOTP utils
 *
 * @author linux_china
 */
public class TOTP {
    /**
     * get totp auth url
     *
     * @param user         user, mainly email
     * @param issuer       issuer
     * @param plaintSecret plain secret
     * @return auth url
     */
    public static String getAuthUrl(String user, String issuer, String plaintSecret) {
        Base32 base32 = new Base32();
        String secret = base32.encodeToString(plaintSecret.getBytes());
        if (secret.contains("=")) {
            secret = secret.substring(0, secret.indexOf("="));
        }
        return "otpauth://totp/" + user + "?secret=" + secret + "&issuer=" + issuer;
    }

    /**
     * check code
     *
     * @param plainSecret plain secret
     * @param code        code
     * @return legal indication
     * @throws NoSuchAlgorithmException no such algorithm exception
     * @throws InvalidKeyException      invalid key exception
     */
    public static boolean checkCode(String plainSecret, long code) throws NoSuchAlgorithmException, InvalidKeyException {
        long t = System.currentTimeMillis() / 1000 / 30;
        byte[] decodedKey = plainSecret.getBytes();
        // Window is used to check codes generated in the near past.
        // You can use this value to tune how far you're willing to go.
        int window = 3;
        for (int i = -window; i <= window; ++i) {
            long hash = verifyCode(decodedKey, t + i);
            if (hash == code) {
                return true;
            }
        }
        // The validation code is invalid.
        return false;
    }

    private static int verifyCode(byte[] key, long t) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] data = new byte[8];
        long value = t;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[20 - 1] & 0xF;
        // We're using a long because Java hasn't got unsigned int.
        long truncatedHash = 0;
        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;
        return (int) truncatedHash;
    }
}
