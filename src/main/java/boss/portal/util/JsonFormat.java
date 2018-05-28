package boss.portal.util;

import org.json.JSONObject;

public class JsonFormat {
	
	public static JSONObject formatResult(int code,String msg,Object data) {
		JSONObject json = new JSONObject() ;
		try {
			json.accumulate("code", code);
			json.accumulate("msg", msg);
			json.accumulate("data", data);
		}catch (Exception e) {
		}
		
		return json;
	}

	public static void main(String[] args) {
		String res = String.format("%s",formatResult(1,"a",new Object().toString()));
		System.out.println(res);
	}

}
