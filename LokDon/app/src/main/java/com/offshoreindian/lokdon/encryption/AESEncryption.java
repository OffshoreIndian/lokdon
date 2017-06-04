package com.offshoreindian.lokdon.encryption;

import android.util.Base64;

import com.offshoreindian.lokdon.utils.DebugUtil;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by praveshkumar on 18/10/16.
 */

public class AESEncryption {
    private SecretKeySpec secretKey;
    private byte[] key;

    private String decryptedString;
    private String encryptedString;
    public static AESEncryption instance;
    public static AESEncryption getInstance()
    {
        if(instance == null)
            instance = new AESEncryption();
        return instance;
    }

    private AESEncryption()
    {

    }
    public void setKey(String myKey) {


        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            System.out.println(key.length);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); // use only first 128 bit
            //System.out.println(key.length);
            //System.out.println(new String(key, "UTF-8"));
            secretKey = new SecretKeySpec(key, "AES");
          //  System.out.println(" secretKey "+secretKey);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public String getDecryptedString() {
        return decryptedString;
    }

    public void setDecryptedString(String decryptedString) {
        decryptedString = decryptedString;
    }

    public String getEncryptedString() {
        return encryptedString;
    }

    public void setEncryptedString(String encryptedString) {
        encryptedString = encryptedString;
    }

    public String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
             byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes());
             encryptedString =  Base64.encodeToString(encrypted,encrypted.length);
            setDecryptedString(encryptedString);
          //  System.out.println("encryptedString: " + encryptedString);

        } catch (Exception e) {

            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public String decrypt(String strToDecrypt) {
        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            setDecryptedString(new String(cipher.doFinal(Base64.decode(strToDecrypt, strToDecrypt.length()))));
//

//            Key aesKey = new SecretKeySpec(secretKey, "AES");
            //DebugUtil.printLog("strToDecrypt :"+strToDecrypt+":");
            Cipher cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] encryptedTextBytes =  Base64.decode(strToDecrypt, strToDecrypt.length());
            byte[] encryptedTextBytes =  Base64.decode(strToDecrypt, Base64.DEFAULT);

            //decryptedString = new String(cipher.doFinal(strToDecrypt.getBytes()));
            decryptedString = new String(cipher.doFinal(encryptedTextBytes));
            //System.out.println("decryptedString: " + decryptedString);

            setDecryptedString(decryptedString);
        } catch (Exception e) {

            System.out.println("Error while decrypting: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }




    public  byte[]  encryptByte(byte[] strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encrypted = cipher.doFinal(strToEncrypt);
           // encryptedString =  Base64.encodeToString(encrypted,encrypted.length);
            //setDecryptedString(encryptedString);
            //  System.out.println("encryptedString: " + encryptedString);

            return encrypted;

        } catch (Exception e) {

            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public byte[] decryptByte(byte[] strToDecrypt) {
        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            setDecryptedString(new String(cipher.doFinal(Base64.decode(strToDecrypt, strToDecrypt.length()))));
//

//            Key aesKey = new SecretKeySpec(secretKey, "AES");
            //DebugUtil.printLog("strToDecrypt :"+strToDecrypt+":");
            Cipher cipher = Cipher.getInstance("AES");
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
//            byte[] encryptedTextBytes =  Base64.decode(strToDecrypt, strToDecrypt.length());
           // byte[] encryptedTextBytes =  Base64.decode(strToDecrypt, Base64.DEFAULT);

            //decryptedString = new String(cipher.doFinal(strToDecrypt.getBytes()));
            //decryptedString = new String(cipher.doFinal(strToDecrypt));
            //System.out.println("decryptedString: " + decryptedString);

          //  setDecryptedString(decryptedString);

            return cipher.doFinal(strToDecrypt);
        } catch (Exception e) {

            System.out.println("Error while decrypting: " + e.toString());
            e.printStackTrace();
        }
        return null;
    }

}
