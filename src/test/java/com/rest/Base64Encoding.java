package com.rest;

import java.util.Base64;

public class Base64Encoding {

    public static void main(String[] args){
        String usernameColonPassword = "username:password";
        String base64Encoded = Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
        System.out.println("Encoded: " + base64Encoded);

        byte[] decodeBytes = Base64.getDecoder().decode(base64Encoded);
        System.out.println("Decoded: " + new String(decodeBytes));

    }
}
