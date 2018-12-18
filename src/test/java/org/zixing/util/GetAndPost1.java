package org.zixing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.zixing.web.ChromatidController;

public class GetAndPost1 {

	private final static String HTTP_URL = "http://192.168.100.100:8080/alg3/";
	static ChromatidController chrom = new ChromatidController();
	private static String mess = "";
	
	private static HashMap<String, String> mData = new HashMap<String, String>();

	public static void main(String[] args) {
		mData.put("name", "HongBin");
		mData.put("sax", "male");
		mess = chrom.caryexcision(null);
		System.out.println("GetResult:" + startGet(HTTP_URL));
		System.out.println("PostResult:" + startPost(HTTP_URL));
	}

	private static String startGet(String path) {
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			// GET请求直接在链接后面拼上请求参数
			String mPath = path + "?";
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
			conn.setReadTimeout(10000);
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
			e.printStackTrace();
		}
		// 关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	private static String startPost(String path) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 发送POST请求必须设置为true
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 设置连接超时时间和读取超时时间
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			out = new OutputStreamWriter(conn.getOutputStream());
			// POST请求参数写在正文中
//			for (String key : mData.keySet()) {
//				out.write(key + "=" + mData.get(key) + "&");
//			}
			out.write(mess);
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
			e.printStackTrace();
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
				ex.printStackTrace();
			}
		}
		
		return result.toString();
	}
}
