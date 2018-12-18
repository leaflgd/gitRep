package org.zixing.util;


import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author:XieZhiLiang
 * @Date:Created in 11:57 2017/11/20
 * @Type:工具类
 */
public class NginxFileUploading {

    /**
     * 判断是否有该文件夹，如果没有则创建
     */
    public static void isMkdir(File file){
        if(!file.exists()){
            file.mkdir();
        }
    }

    private static final String UPLOAD_URL="http://192.168.100.156:8888/execute";

    //先将文件保存到本地，返回图片绝对地址
    public static String imageStr(MultipartFile file){
        if(!file.isEmpty()){
            String path ="C:\\image.cache";
            isMkdir( new File(path) );
            path+="\\"+file.getOriginalFilename();
            try {
                file.transferTo(new File(path));
                return path;
            } catch (IOException e) {
                System.out.println("save localhost image error:"+e.getMessage());
            }
        }
        //System.out.println("file is empty ------------------");
        return null;
    }

    /**
     * 上传图片
     * @param textMap
     * @param fileMap
     * @return
     */
    public static String formUpload(Map<String, String> textMap, Map<String, String> fileMap) {
        System.out.println("开始上传文件");
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; //boundary就是request头和上传文件内容的分隔符
        try {
            URL url = new URL(UPLOAD_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());
            System.out.println("1");
            // text
            if (textMap != null) {
                StringBuffer strBuf = new StringBuffer();
                Iterator<Map.Entry<String, String>> iter = textMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
                    strBuf.append(inputValue);
                }
                out.write(strBuf.toString().getBytes());
            }
            System.out.println("2");
            // file
            if (fileMap != null) {
                Iterator<Map.Entry<String, String>> iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, String> entry = iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    System.out.println("3");
                    File file = new File(inputValue);
                    String filename = file.getName();
                    System.out.println("33：filename"+filename+",inputValue:"+inputValue);
                    MagicMatch match = Magic.getMagicMatch(file, false, true);
                    System.out.println("4");
                    String contentType = match.getMimeType();
                    System.out.println("5");
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());
                    System.out.println("5");
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 读取返回数据
            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + UPLOAD_URL);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    //上传完就删除
    public static void delImage(String path){
        File file=new File(path);
        file.delete();
    }
}
