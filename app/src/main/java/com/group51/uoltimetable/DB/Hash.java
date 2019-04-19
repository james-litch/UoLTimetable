package com.group51.uoltimetable.DB;

import java.security.MessageDigest;

public class Hash {
    private String input;
    private static String digits = "0123456789abcdef";

    // When using create hash object with String that needs to be hashed as the input
    public Hash(String input) {
        this.input = input;
    }

    // call this method on the hash object to return the hash
    public String getHash() {
        try {
            MessageDigest hash = MessageDigest.getInstance("SHA-256");
            hash.update(this.input.getBytes());
            return bytesToHex(hash.digest());
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}