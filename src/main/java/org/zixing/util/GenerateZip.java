package org.zixing.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

public class GenerateZip {
	
	/**
     * 打包成zip包
     * sourcePath:原始文件的路径
     * zipName:压缩包的名字
     * zipPath:打成压缩包后保存的路径
     */
	public boolean createCardImgZip(String sourcePath, String zipName,String zipPath) {  
        // TODO Auto-generated method stub  
        boolean result = false;  
        File sourceFile = new File(sourcePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
  
        if (sourceFile.exists() == false) {  
            System.out.println("File catalog:" + sourcePath + "not exist!");  
        } else {  
            try {  
                if(!new File(zipPath).exists()){  
                    new File(zipPath).mkdirs();  
                }  
                File zipFile = new File(zipPath + "/" + zipName + ".zip");  
                if (zipFile.exists()) {
                	zipFile.delete();
                	zipFile.createNewFile();
                	
                	 File[] sourceFiles = sourceFile.listFiles();  
                     if (null == sourceFiles || sourceFiles.length < 1) {  
                         System.out.println("File Catalog:" + sourcePath + "nothing in there,don't hava to compress!");  
                     } else {  
                         fos = new FileOutputStream(zipFile);  
                         zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                         byte[] bufs = new byte[1024 * 10];  
                         for (int i = 0; i < sourceFiles.length; i++) {  
                             // create .zip and put pictures in  
                             ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                             zos.putNextEntry(zipEntry);  
                             // read documents and put them in the zip  
                             fis = new FileInputStream(sourceFiles[i]);  
                             bis = new BufferedInputStream(fis, 1024 * 10);  
                             int read = 0;  
                             while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {  
                                 zos.write(bufs, 0, read);  
                             }  
                         }  
                         result = true;  
                     }
                } else {  
                    File[] sourceFiles = sourceFile.listFiles();  
                    if (null == sourceFiles || sourceFiles.length < 1) {  
                        System.out.println("File Catalog:" + sourcePath + "nothing in there,don't hava to compress!");  
                    } else {  
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024 * 10];  
                        for (int i = 0; i < sourceFiles.length; i++) {  
                            // create .zip and put pictures in  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                            zos.putNextEntry(zipEntry);  
                            // read documents and put them in the zip  
                            fis = new FileInputStream(sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024 * 10);  
                            int read = 0;  
                            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {  
                                zos.write(bufs, 0, read);  
                            }  
                        }  
                        result = true;  
                    }  
                }  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally {  
                try {  
                    if (null != bis)  
                        bis.close();  
                    if (null != zos)  
                        zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return result;  
    }  
	
	/**
	 * 下载压缩包
	 */
//	public String downLoadZip(String fileName,String path) {  
////        String fileName = "folder.zip";  
////        String path = "D:\\test\\folder.zip";  
//        try {  
//            HttpServletResponse response = ServletActionContext.getResponse();  
//            File file = new File(path);  
//            response.setCharacterEncoding("UTF-8");  
//            response.setHeader("Content-Disposition",  
//                    "attachment; filename=" + new String(fileName.getBytes("ISO8859-1"), "UTF-8"));  
//            response.setContentLength((int) file.length());  
//            response.setContentType("application/zip");// 定义输出类型  
//            FileInputStream fis = new FileInputStream(file);  
//            BufferedInputStream buff = new BufferedInputStream(fis);  
//            byte[] b = new byte[1024];// 相当于我们的缓存  
//            long k = 0;// 该值用于计算当前实际下载了多少字节  
//            OutputStream myout = response.getOutputStream();// 从response对象中得到输出流,准备下载  
//            // 开始循环下载  
//            while (k < file.length()) {  
//                int j = buff.read(b, 0, 1024);  
//                k += j;  
//                myout.write(b, 0, j);  
//            }  
//            myout.flush();  
//            buff.close();  
//            file.delete();  
//        } catch (Exception e) {  
//            System.out.println(e);  
//        }  
//        return null;  
//    }
	

}
