package com.returnp.rplib;

import java.util.HashMap;

public class ReturnpEncoder {
	public static String decodePaymentInfo(HashMap<String, String> payMap) throws Exception {
		return ReturnpEncoder.decodePaymentInfoToMap(payMap);
	}

	public static String encode(HashMap<String, String> sourceMap) {
		return null;
	}
	
	public static String decodePaymentInfoToMap(HashMap<String, String> payMap) throws Exception {
		if (!payMap.containsKey("pat" ) ||  !payMap.containsKey("af_id" ) || !payMap.containsKey("pas" ) || !payMap.containsKey("pan" ) || !payMap.containsKey("pam" ) || !payMap.containsKey("pay_type" )) {
			throw new Exception("wrong payment Info");
		}
		char[] dataSeq = new char[40];
		for (int i = 0; i <dataSeq.length; i++) {
			dataSeq[i] = '0';
		}
		//결제일 복사 
		char[] pat = payMap.get("pat").toCharArray();
		char[] pan = payMap.get("pan").toCharArray();
		char[] afid = payMap.get("af_id").toCharArray();
		char[] pas = payMap.get("pas").toCharArray();
		char[] pam = payMap.get("pam").toCharArray();
		
		int index = 0;
		/*결제 승인 시간  복사*/
		System.arraycopy(pat, 0, dataSeq, index, pat.length);
		index += pat.length;
		
		/*승인번호 앞자리 4자리 복사*/
		System.arraycopy(pan, 0 , dataSeq, index ,  4);
		index += 4;
		
		/*afID 7자리 복사*/
		System.arraycopy(afid, 0 , dataSeq , index , 7);
		index += 7;
		
		/*승인 번호 중간 4자리 복사*/
		System.arraycopy(pan, 4 , dataSeq , index , 4);
		index += 4;
		
		/*승인 금액 복사*/
		System.arraycopy(pam, 0 , dataSeq, index , pam.length);
		index += pam.length;
		
		/*승인 상태  복사*/
		System.arraycopy(pas, 0 , dataSeq, index , pas.length);
		index += pas.length;

		/*승인 번호 긑 4   복사*/
		System.arraycopy(pan, 8 , dataSeq, index , 4);
		index += pas.length;
		
		String t_data = String.valueOf(dataSeq);
		System.out.println("1차 변환 데이타");
		System.out.println(t_data);
		/* 신용카드, 현금 결제에 따른 구분자 설정*/
		String sep = payMap.get("pay_type").equals("1") ? "!" : "@"; 
		String[]  encData  =  new String[5];
		encData[0] = AntiLogarithm62.strEachReverse(AntiLogarithm62.change62_s(Long.valueOf(t_data.substring(0, 9))));
		encData[1] = AntiLogarithm62.strEachReverse(AntiLogarithm62.change62_s(Long.valueOf(t_data.substring(9, 18))));
		encData[2] = AntiLogarithm62.strEachReverse(AntiLogarithm62.change62_s(Long.valueOf(t_data.substring(18, 27))));
		encData[3] = AntiLogarithm62.strEachReverse(AntiLogarithm62.change62_s(Long.valueOf(t_data.substring(27, 36))));
		encData[4] = AntiLogarithm62.strEachReverse(AntiLogarithm62.change62_s(Long.valueOf(t_data.substring(36, 40))));
		return String.join(sep, encData);
	}
}
