/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.oea.online_exam_app.Utils;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author tirth
 */
public class EncryptData {
      @Value("{${crypto.AES_secret}}")
    private static final String AES_SECRET = "1234567890123456"; 

    public String encrypt(String data) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(AES_SECRET.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            String encryptedString =Base64.getEncoder().encodeToString(encryptedData);
            return encryptedString;
        } catch (Exception e) {
            throw new RuntimeException("Error during encryption", e);
        }
    }
}
