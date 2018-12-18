package org.zixing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import net.sf.json.JSONObject;

public class GetAndPost {

	private static String HTTP_URL = null;
	private static HashMap<String, String> mData = new HashMap<String, String>();

//	public static void main(String[] args) throws Exception {
////		for (; ;) {
//			JSONObject  obj = new JSONObject();
//			obj.accumulate("Event_id", "C279020");                                                                                                                                                                                                                                               
//			obj.accumulate("DialPiece_Num", "3~A");
//			System.out.println("111111111111");
//			try {
//				System.out.println(new GetAndPost().start("post",obj,"http://192.168.1.118:8000/alg3/"));//156
////				System.out.println(new GetAndPost().start("post",obj,"http://192.168.80.75:8000/alg3/"));//SC
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
	public String start(String start ,JSONObject obj,String url) throws Exception{
		String str="";
		HTTP_URL=url;
		switch (start) {
		case "post":
			str =startPost(obj);
		break;
		case "get":
			str =startGet(obj);
			break;
		default:
			throw new RuntimeException("链接方式错误");
		}	
		return str;
	}
	public static String startGet(JSONObject  obj) throws Exception{
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			// GET请求直接在链接后面拼上请求参数
			String mPath = HTTP_URL + "?";
			for (String key : mData.keySet()) {
				mPath += key + "=" + mData.get(key) + "&";
			}
			URL url = new URL(mPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			// Get请求不需要DoOutPut
			conn.setDoOutput(false);
			conn.setDoInput(true);
			// 设置连接超时时间和读取超时时间
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(1000000000);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接服务器
			conn.connect();
			// 取得输入流，并使用Reader读取
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Get请求失败");
		}
		// 关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				throw new RuntimeException("IO异常");
			}
		}
		return result.toString();
	}

	public static String startPost(JSONObject  obj) throws Exception{
		OutputStream out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL url = new URL(HTTP_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置为true
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置连接超时时间和读取超时时间
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(1000000000);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			out = conn.getOutputStream();
			// POST的请求参数写在正文中
//			for (String key : mData.keySet()) {
//				out.write(key + "=" + mData.get(key) + "&");
//			}
			out.write((obj.toString()).getBytes());
			out.flush();
			out.close();
			// 取得输入流，并使用Reader读取
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Post请求失败");
		}
		// 关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
				throw new RuntimeException("IO异常");
			}
		}
		return result.toString();
	}
}
