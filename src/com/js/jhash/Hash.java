package com.js.jhash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Hash {

	private File f;
	private byte[] fileContent;
	
	public Hash(File file) throws IOException {
		f = file;
		setFileContent(getFileContent());
	}
	
	// Will return the generated MD5 as String (in base 16) 
	public String getMD5() throws NoSuchAlgorithmException, IOException {
		return getHexString(generateMD5());
	}
	
	// Will generate the MD5 by calling getFileContent() and then using MessageDigest
	private byte[] generateMD5() throws IOException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(fileContent);
		return md.digest();
	}
	
	// Will return the generated CRC32 checksum as a String
	public String getCRC32() throws IOException {
		return Long.toString(generateCRC32());
	}
	
	// Will generate CRC32 using CRC32 class 
	private long generateCRC32() throws IOException {
		Checksum checksum = new CRC32(); // Checksum hsa to initiated to CRC32
		checksum.update(fileContent, 0, fileContent.length);
		
		return checksum.getValue();
	}
	
	// Will return the generated SHA-1 as as String (converted to base 16)
	public String getSHA1() throws IOException, NoSuchAlgorithmException{
		return getHexString(generateSHA1());
	}
	
	// Will generate SHA-1 using the same method as MD5
	private byte[] generateSHA1() throws IOException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA1");
		md.update(fileContent);
		
		return md.digest();
	}

	// Will return SHA-256 as String
	public String getSHA256() throws IOException, NoSuchAlgorithmException{
		return getHexString(generateSHA256());
	}
	
	private byte[] generateSHA256() throws IOException, NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(fileContent);
		
		return md.digest();
	}

	// This will take the message digest and convert it to hexadecimal to compare
	// most "get(X)" methods will use this method
	private String getHexString(byte[] digest) {
		String result = "";
		  for (int i=0; i < digest.length; i++) {
		    result += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
		         
		  }
		  return result;
	}
	
	// will return the content of the specified file as byte[]
	private byte[] getFileContent() throws IOException{
		FileInputStream fin = new FileInputStream(f);
		byte[] fileContent = new byte[(int)f.length()];
		
		fin.read(fileContent);
		fin.close();
		
		return fileContent;
	}
	
	// @return f
	public File getFile() {
		return f;
	}

	// @param set f
	public void setFile(File f) {
		this.f = f;
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
