package com.returnp.rplib;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class ReturnPDecoder {
	public static HashMap<String, String> decodePaymentInfo(String data) throws Exception {
		return ReturnPDecoder.decodePaymentInfoToMap(data);
	}
	
	public static String decode(String data) {
		return null;
	}
	
	public static HashMap<String, String> decodePaymentInfoToMap(String encData) throws Exception{
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String sep   = encData.contains(QRManager.QR_MAP_SEP_CREDIT) ? 
				QRManager.QR_MAP_SEP_CREDIT  :  (encData.contains(QRManager.QR_MAP_SEP_CASH) ? QRManager.QR_MAP_SEP_CASH : "NOT" );
		
		if (sep.equals("NOT")) {
			return null;
		}
		
		String[] encArr =  encData.split(sep);
		if (encArr.length != 5) return null;
		String field1 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[0]));
		String field2 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[1]));
		String field3 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[2]));
		String field4 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[3]));
		String field5 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[4]));
		
	/*	if (!QRManager.afterValidateQR(field1, field2, field3 ,  field4, field5)) {
			return null;
		}*/
		
		String qrPText = field1 + field2 + field3 + field4 + field5;
		qrPText = qrPText.substring(0, 27) + String.format("%013d", Long.valueOf(qrPText.substring(27)));
		System.out.println(qrPText);
		
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar(
			Integer.valueOf("20"+qrPText.substring(0, 12).substring(0, 2)),      //연
			Integer.valueOf(qrPText.substring(0, 12).substring(4, 6)) -1,  //월
			Integer.valueOf(qrPText.substring(0, 12).substring(8, 10)),    //일
			Integer.valueOf(qrPText.substring(0, 12).substring(10, 12)),  //시
			Integer.valueOf(qrPText.substring(0, 12).substring(6, 8)),      //분
			Integer.valueOf(qrPText.substring(0, 12).substring(2, 4))       //초
		);
		Date date = calendar.getTime();
		
		/* 
		 * 인코딩된 데이타를 디크립트 후 맵 반환
		 * map key value
		 * pat : 승인 시간
		 * pan : 승인 번호
		 * af_id : 가맹점 번호
		 * pam : 승인 금액
		 * pas : 승인 상태  (1 : 결제 승인, 0 : 결제 승인 취소)
		 * pas_str : 승인 상태에 따른 추가된 문자열
		 * */
		
		try {
			qrMap.put("pat", sdf2.format(date));  //승인시간
			qrMap.put("pan", qrPText.substring(12, 16) + qrPText.substring(23, 27) + qrPText.substring(36, 40));   // 승인 번호
			qrMap.put("af_id", qrPText.substring(16, 23));  // 가맹점 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(27, 35))));    //승인 금액
			qrMap.put("pas", qrPText.substring(35,36));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(35,36).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
			qrMap.put("pay_type", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "1" : "2");    //1 : 신용카드 2 : 현금 결제 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}  
		return qrMap;
	}
	public static HashMap<String, String> parseQRToMap(String queryQR) throws Exception{
		HashMap<String, String> queryMap = Util.queryToMap(queryQR);
		if (!QRManager.beforeValidateQR(queryMap)) {
			return null;
		}
		
		HashMap<String, String> qrMap = new HashMap<String, String>();
		String encData = queryMap.get(QRManager.QR_MAP_KEY_D);
		String sep   = encData.contains(QRManager.QR_MAP_SEP_CREDIT) ? 
				QRManager.QR_MAP_SEP_CREDIT  :  (encData.contains(QRManager.QR_MAP_SEP_CASH) ? QRManager.QR_MAP_SEP_CASH : "NOT" );
		
		if (sep.equals("NOT")) {
			return null;
		}
		
		String[] encArr =  encData.split(sep);
		if (encArr.length != 5) return null;
		String field1 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[0]));
		String field2 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[1]));
		String field3 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[2]));
		String field4 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[3]));
		String field5 = AntiLogarithm62.convertBase62toBase10(AntiLogarithm62.strEachReverse(encArr[4]));
		
	/*	if (!QRManager.afterValidateQR(field1, field2, field3 ,  field4, field5)) {
			return null;
		}*/
		
		String qrPText = field1 + field2 + field3 + field4 + field5;
		qrPText = qrPText.substring(0, 27) + String.format("%013d", Integer.valueOf(qrPText.substring(27)));
		System.out.println(qrPText);
		
		/*VAN 시간을 내부 포맷으로 변경*/
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		GregorianCalendar calendar = new GregorianCalendar(
			Integer.valueOf("20"+qrPText.substring(0, 12).substring(0, 2)),      //연
			Integer.valueOf(qrPText.substring(0, 12).substring(4, 6)) -1,  //월
			Integer.valueOf(qrPText.substring(0, 12).substring(8, 10)),    //일
			Integer.valueOf(qrPText.substring(0, 12).substring(10, 12)),  //시
			Integer.valueOf(qrPText.substring(0, 12).substring(6, 8)),      //분
			Integer.valueOf(qrPText.substring(0, 12).substring(2, 4))       //초
		);
		Date date = calendar.getTime();
		
		/* 
		 * 인코딩된 데이타를 디크립트 후 맵 반환
		 * map key value
		 * pat : 승인 시간
		 * pan : 승인 번호
		 * af_id : 가맹점 번호
		 * pam : 승인 금액
		 * pas : 승인 상태  (1 : 결제 승인, 0 : 결제 승인 취소)
		 * pas_str : 승인 상태에 따른 추가된 문자열
		 * */
		
		try {
			qrMap.put("pat", sdf2.format(date));  //승인시간
			qrMap.put("pan", qrPText.substring(12, 16) + qrPText.substring(23, 27) + qrPText.substring(36, 40));   // 승인 번호
			qrMap.put("af_id", qrPText.substring(16, 23));  // 가맹점 번호
			qrMap.put("pam", String.valueOf(Integer.valueOf(qrPText.substring(27, 35))));    //승인 금액
			qrMap.put("pas", qrPText.substring(35,36));    //승인 상태 
			qrMap.put("pas_str", qrPText.substring(35,36).equals("0") ? "승인 완료" : "승인 취소");    //승인 상태 
			qrMap.put("pay_type", sep.equals(QRManager.QR_MAP_SEP_CREDIT ) ? "1" : "2");    //1 : 신용카드 2 : 현금 결제 
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}  
		return qrMap;
	}
}
