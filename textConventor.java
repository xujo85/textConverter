/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.math.BigInteger;
import java.util.HashMap;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


import javax.crypto.Cipher;
/**
 *
 * @author kuro
 */
public class textConventor {

 private static BigInteger big = new BigInteger("123124512412321");

 static HashMap<String,String> encryptionTable = new HashMap<>();
 static HashMap<String,String> decryptionTable = new HashMap<>();
   
 public static void main(String args[]) throws NoSuchAlgorithmException, Exception
 {
    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
    keyPairGenerator.initialize(4096);
        
    KeyPair keyPair = keyPairGenerator.generateKeyPair();  
   
     encryptionTable.put("1","11");
     encryptionTable.put("2","12");
     encryptionTable.put("3","13");
     encryptionTable.put("4","21");
     encryptionTable.put("5","22");
     encryptionTable.put("6","23");
     encryptionTable.put("7","31");
     encryptionTable.put("8","32");
     encryptionTable.put("9","33");
     encryptionTable.put("0","42");
     encryptionTable.put("_","99");
     decryptionTable.put("11","1");
     decryptionTable.put("12","2");
     decryptionTable.put("13","3");
     decryptionTable.put("21","4");
     decryptionTable.put("22","5");
     decryptionTable.put("23","6");
     decryptionTable.put("31","7");
     decryptionTable.put("32","8");
     decryptionTable.put("33","9");
     decryptionTable.put("42","0");
     decryptionTable.put("99","_");
     String e = stringToNumbers("Ahojky ja som pato. Lorem ipsum.");
     System.out.println("Povodne: Ahojky ja som pato. Lorem ipsum.");
     
     System.out.println("Encrypted to numbers:"+ e);
     System.out.println("Decrypted from numbers:" + numbersToString(e));
     byte[] cipherTextArray = encrypt(e, keyPair.getPublic());
     String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
     System.out.println("Public key : "+keyPair.getPublic());
     System.out.println("Private key : "+keyPair.getPrivate());
     System.out.println("Encrypted Text to RSA: "+encryptedText);
        
     // Decryption
     String decryptedText = decrypt(cipherTextArray, keyPair.getPrivate());
     System.out.println("Decrypted Text from RSA : "+numbersToString(decryptedText));

 }
 
 static String stringToNumbers(String data)
 {
     String b = data;
     String tmp = "";
     int j = 0;
     StringBuilder st = new StringBuilder();
     for(int i = 0;i<b.length();i++)
     {
        
         int a = (int)data.charAt(0);
         //System.out.println(a);
         System.out.println((char)a);
         String x = String.valueOf(a);
         for(int v=0;v<x.length();v++)
         {
             tmp  = x.substring(v,v+1);
             //System.out.println("table: " + encryptionTable.get(tmp));
             st.append(encryptionTable.get(tmp));
         }
         st.append("99"); 
         data = data.substring(1, data.length());
     
     }
     return st.toString();
 }
 
 static String numbersToString(String data)
 {
     //#STAGE1
     
     String b = data;
     String tmp = "_";
     StringBuilder st = new StringBuilder();
     StringBuilder Final = new StringBuilder();
     
     //#STAGE2
     while(!data.equals(""))
     {
        
         String a = data.substring(0,2);
         String x = String.valueOf(a);
         //System.out.println("tmp" + x);
         //System.out.println("real value: " + decryptionTable.get(x));
         tmp = String.valueOf(decryptionTable.get(x));
         st.append(tmp);
         data = data.substring(2, data.length());
     }
     //#STAGE3
     data = st.toString();
     //System.out.println("d:" + st.toString());
     while(data!="")
     {
         int index = data.indexOf("_");
         //index--;
         //System.out.println("index " + index);
         //System.out.println("character:" +((char)(int)Integer.parseInt(data.substring(0, index))));
         Final.append(((char)(int)Integer.parseInt(data.substring(0, index))));
         data = data.substring(index+1, data.length());
     }  
     
     //System.out.println("decrypted: " + Final.toString() );
     return Final.toString();
 }
   public static byte[] encrypt (String plainText,PublicKey publicKey ) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes()) ;

        return cipherText;
    }
    
    public static String decrypt (byte[] cipherTextArray, PrivateKey privateKey) throws Exception
    {
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(cipherTextArray);
        
        return new String(decryptedTextArray);
    } 
}
