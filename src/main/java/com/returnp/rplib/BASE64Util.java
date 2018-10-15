package com.returnp.rplib;

import java.io.UnsupportedEncodingException;

import org.apache.axis.encoding.Base64;

public class BASE64Util {

	   // Encode
	   public static String encodeString(String text)
	           throws UnsupportedEncodingException {
	       byte[] bytes = text.getBytes("UTF-8");
	       String encodeString = Base64.encode(bytes);
	       return encodeString;
	   }
	 
	   // Decode
	   public static String decodeString(String encodeText)
	           throws UnsupportedEncodingException {
	       byte[] decodeBytes = Base64.decode(encodeText);
	       String str = new String(decodeBytes, "UTF-8");
	       return str;
	   }
	    
	    
	   public static void main(String[] args) throws UnsupportedEncodingException  {
	       String text = "Example Vietnamese text - Tiếng Việt";
	        
	       System.out.println("Text before encode: "+ text);
	        
	       String encodeText = encodeString(text);
	       System.out.println("Encode text: "+ encodeText);
	        
	       String decodeText =  decodeString(encodeText);
	       System.out.println("Decode text: "+ decodeText);        
	   }
	
}
