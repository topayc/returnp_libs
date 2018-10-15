package com.returnp.rplib;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class AntiLogarithm62 {

	private static final long base = 62; // 62진수로 표현위해 나누는 변수 값
	
	/**
	 * 10진수를 62진수로 변환.
	 * 
	 * @param num10
	 *            long type의 10진수.
	 * @param length
	 * 			  결과 값의 길이.
	 * @return 변환된 62진수 String.
	 */
	public static String change62_s(long num10, int length) { // 10진수를 62진수로 변환
		long modofresult = 0;
		StringBuilder result62 = new StringBuilder();
		
		do {
			modofresult = num10 % base;
			if (modofresult <= 9) {
				result62.insert(0, modofresult);
			} else if (modofresult <= 35) {
				result62.insert(0, (char) (65 + (modofresult - 10)));
			} else if (modofresult <= 61) {
				result62.insert(0, (char) (97 + (modofresult - 36)));
			}
			
			num10 /= base;
			
		} while (num10 > 0);
		
		for (int i = result62.length(); i < length; i++) {
			result62.insert(0, "0");
		}
		
		return result62.toString();
	}
	
	/**
	 * 10진수를 62진수로 변환.
	 * 
	 * @param num10
	 *            long type의 10진수.
	 * @return 변환된 62진수 String.
	 */
	public static String change62_s(long num10) { // 10진수를 62진수로 변환
		return change62_s(num10, 0);
	}

	public static int change10(String bbsDepth) {
		if (bbsDepth == null || bbsDepth.length() != 30) {
			return 0;
		}

		double decimalValue = 0;
		for (int i = 30; i > 0; i -= 5) {
			String depth = bbsDepth.substring(i - 5, i);
			int value = 0;
			if (!depth.equals("zzzzz")) {
				for (int j = 4; j >= 0; j--) {
					char oneChar = depth.charAt(j);
					if (Character.isDigit(oneChar)) {
						value = Integer.parseInt(String.valueOf(depth.charAt(j)));
						decimalValue = decimalValue + (value * Math.pow(62, 4 - j));
					} else {
						byte a = (byte) (oneChar);
						if (a <= 90) {
							value = (a - 65) + 10;
						} else if (a <= 122) {
							value = (a - 97) + 36;
						}
						decimalValue = decimalValue + (value * Math.pow(62, 4 - j));
					}
				}
				break;
			} else {
				continue;
			}
		}
		return (int) decimalValue;
	}

	public static String convertBase62toBase10(String base62) {
		if (base62 == null || base62.length() < 1) {
			return null;
		}

		int len = base62.length();
		BigDecimal decimalValue = new BigDecimal("0");
		int value = 0;

		for (int i = len - 1; i >= 0; i--) {
			char oneChar = base62.charAt(i);
			if (Character.isDigit(oneChar)) {
				value = Integer.parseInt(String.valueOf(base62.charAt(i)));
				decimalValue = decimalValue.add(new BigDecimal((value * Math.pow(62, (len - 1) - i))));
			} else {
				byte a = (byte) (oneChar);
				if (a <= 90) {
					value = (a - 65) + 10;
				} else if (a <= 122) {
					value = (a - 97) + 36;
				}
				decimalValue = decimalValue.add(new BigDecimal(value * Math.pow(62, (len - 1) - i)));
			}
		}
		return decimalValue.toString();
	}
	
	public static String strEachReverse(String str) {
		char[] arr = str.toCharArray();
		
		for(int i=0; i<arr.length; ++i){
			if(97<=arr[i] && arr[i]<=122){
                arr[i]=(char)(arr[i]-32);
                }else if ((65<=arr[i] && arr[i]< 90)){
                	arr[i]=(char)(arr[i]+32);
                }
            }
          return String.valueOf(arr);
     }
}