package com.manz.sample;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.crypto.Cipher;

public class MainClass {
	
	static Decrypt decrypt = new Decrypt();
	static Encrypt encrypt = new Encrypt();

	public static void main(String[] args) {
//		if(args.length<1){
//			System.out.println("Pass at least one argument (filename)");		
//			return;
//		}
		try{
			//check files - just for safety
			String fileName="C:\\Users\\manojka\\Desktop\\Junk\\Flipkart.pdf";
			String tempFileName=fileName+".enc";
			String resultFileName=fileName+".dec";

			File file = new File(fileName);
			if(!file.exists()){
				System.out.println("No file "+fileName);
				return;
			}
			File file2 = new File(tempFileName);
			File file3 = new File(resultFileName);
			if(file2.exists() || file3.exists()){
				System.out.println("File for encrypted temp file or for the result decrypted file already exists. Please remove it or use a different file name");
				return;
			}

			copy(Cipher.ENCRYPT_MODE, fileName, tempFileName, "password12345678");
			copy(Cipher.DECRYPT_MODE, tempFileName, resultFileName, "password12345678");

			System.out.println("Success. Find encrypted and decripted files in current directory");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void copy(int mode, String inputFile, String outputFile, String password) throws Exception {
		
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(inputFile));
		BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
		if(mode==Cipher.ENCRYPT_MODE){
			encrypt.encrypt(is, os, password);
		}
		else if(mode==Cipher.DECRYPT_MODE){
			decrypt.decrypt(is, os, password);
		}
		else throw new Exception("unknown mode");
		is.close();
		os.close();
	}
	
	

	/* A helper - to reuse the stream code below - if a small String is to be encrypted */
	public static byte[] encrypt(String plainText, String password) throws Exception {
		ByteArrayInputStream bis = new ByteArrayInputStream(plainText.getBytes("UTF8"));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		encrypt.encrypt(bis, bos, password);
		return bos.toByteArray();
	}


	public static byte[] decrypt(String cipherText, String password) throws Exception {
		byte[] cipherTextBytes = cipherText.getBytes();
		ByteArrayInputStream bis = new ByteArrayInputStream(cipherTextBytes);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		decrypt.decrypt(bis, bos, password);		
		return bos.toByteArray();
	}

}

