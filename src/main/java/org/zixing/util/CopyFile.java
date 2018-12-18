package org.zixing.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFile {
 public static void main(String[] args) throws IOException {
  String path = "C://Users//Administrator//Desktop//report";
  String copyPath = "F://TEST";
  CopyFile copyFile = new CopyFile();
  copyFile.copy(path,copyPath);
 
 }
  
 
 public void copy(String path, String copyPath) throws IOException{
		File filePath = new File(path);
		DataInputStream read;
		DataOutputStream write;
		if (filePath.isDirectory()) {
			File[] list = filePath.listFiles();
			for (int i = 0; i < list.length; i++) {
				String newPath = path + File.separator + list[i].getName();
				String newCopyPath = copyPath + File.separator
						+ list[i].getName();
				File newFile = new File(copyPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				copy(newPath, newCopyPath);
			}
		} else if (filePath.isFile()) {
			read = new DataInputStream(new BufferedInputStream(
					new FileInputStream(path)));
			write = new DataOutputStream(new BufferedOutputStream(
					new FileOutputStream(copyPath)));
			byte[] buf = new byte[1024 * 512];
			while (read.read(buf) != -1) {
				write.write(buf);
			}
			read.close();
			write.close();
		} else {
			System.out.println("请输入正确的文件名或路径名");
		}
	}
  
}
