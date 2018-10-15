package com.returnp.rplib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Set;


/**
 * 공통함수를 위한  클래스.
 *
 * @version 1.0
 */
public class Util {
	
	public static void printMap(HashMap<String, String> from) {
		Set<String> set = from.keySet();
		for (String s:set) {
			System.out.println( s +  " : "  + from.get(s));
		}
	}
	
	public static HashMap<String, String> queryToMap(String queryParam){
		HashMap<String, String> map = new HashMap<String, String>();
		String[] params = queryParam.split("&");
		for (int i = 0; i < params.length; i++) {
			String key = params[i].split("=")[0].trim();
			String value = params[i].split("=")[1].trim();
			map.put(key, value);
		}
		return map;
	}

	public static String reverseString(String s) {
	    return ( new StringBuffer(s) ).reverse().toString();
	  }
	
	
	public static HashMap<String, String> parseQRtoMap(HashMap<String, String> map, String key) {
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String encData = map.get(key);
		String[] encArr = encData.split("!");
		
		String field1 = encArr[0].trim();
		String field2 = encArr[1].trim();
		
		String qrPText = 
			AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(field1)) +
			AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(field2));
		
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf1= new SimpleDateFormat("yyssMMmmDDhh");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		System.out.println("승인 시간");
		try {
			System.out.println(qrPText.substring(0, 12));
			System.out.println(sdf1.parse(qrPText.substring(0, 12)));
			System.out.println(sdf2.format(sdf1.parse(qrPText.substring(0, 12))));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			qrMap.put("pat", sdf2.format(sdf1.parse(qrPText.substring(0, 12))));  //승인시간
			qrMap.put("pan", qrPText.substring(12, 16) + qrPText.substring(23, 27));   // 승인 번호
			qrMap.put("af_id", qrPText.substring(16, 23));  // 가맹점 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(27, 35))));    //승인 금액
			qrMap.put("pas", qrPText.substring(35));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(35).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		return qrMap;
	}
	
	public static String convertBase62toBase10(String base62) {
		if (base62 == null || base62.length() < 1) {
			return null;
		}

		int len = base62.length();
		double decimalValue = 0;
		int value = 0;

		for (int i = len - 1; i >= 0; i--) {
			char oneChar = base62.charAt(i);
			if (Character.isDigit(oneChar)) {
				value = Integer.parseInt(String.valueOf(base62.charAt(i)));
				decimalValue = decimalValue + (value * Math.pow(62, (len - 1) - i));
			} else {
				byte a = (byte) (oneChar);
				if (a <= 90) {
					value = (a - 65) + 10;
				} else if (a <= 122) {
					value = (a - 97) + 36;
				}
				decimalValue = decimalValue + (value * Math.pow(62, (len - 1) - i));
			}
		}

		return String.valueOf((long) decimalValue);
	}
	
	
	
	
}