package com.skarp.prio.user;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA3 {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;
    private static final String OUTPUT_FORMAT = "%-20s:%s";

    public static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        //byte[] result = md.digest(input);
        return md.digest(input);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String hashPassword(String password){
        String algorithm = "SHA3-256";

        byte[] passwordInBytes = SHA3.digest(password.getBytes(UTF_8),algorithm);
        //String passwordHex = bytesToHex(passwordInBytes);
        return bytesToHex(passwordInBytes);
    }
}