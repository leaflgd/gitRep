package org.zixing.util;

import java.io.*;
import java.net.MalformedURLException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtils {
    //是否调用ftp,true调用,false不调用
    public static boolean isServer = false;

    //ftp服务器地址
    public static String hostname = "192.168.1.156";//156
	
    //ftp服务器端口号默认为21
    public static Integer port = 21 ;//156

    //ftp登录账号
    public static String username = "zixing";
//    public static String username = "uftp";
    //ftp登录密码
    public static String password = "3";
//    public static String password = "zixingAI1234";
    //FTP连接对象
    private FTPClient ftpClient = null;
    
    private static final Logger log =Logger.getLogger(FtpUtils.class);
    
    /**
     * 初始化ftp服务器
     */
    public void initFtpClient() {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8"); 
        try {
        	log.info("connecting...ftp服务器:"+this.hostname+":"+this.port); 
            ftpClient.connect(hostname, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
            	log.info("connect failed...ftp服务器:"+this.hostname+":"+this.port); 
            }
            log.info("connect successfu...ftp服务器:"+this.hostname+":"+this.port); 
        }catch (MalformedURLException e) { 
           log.error("initFtpClient MalformedURLException error :" + e);
           e.printStackTrace(); 
        }catch (IOException e) { 
        	log.error("initFtpClient IOException error :" + e);
           e.printStackTrace(); 
        } 
    }

    /**
    * 上传文件
    * @param pathname ftp服务保存地址
    * @param fileName 上传到ftp的文件名
    *  @param originfilename 待上传文件的名称（绝对地址） * 
    * @return
    */
    public boolean uploadFile( String pathname, String fileName,String originfilename){
        if(isServer) {
        	log.info("文件上传FTP");
            boolean flag = false;
            InputStream inputStream = null;
            try{
                log.info("开始上传文件");
                inputStream = new FileInputStream(new File(originfilename));
                initFtpClient();
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                CreateDirecroty(pathname);
                ftpClient.makeDirectory(pathname);
                ftpClient.changeWorkingDirectory(pathname);
                ftpClient.storeFile(fileName, inputStream);
                inputStream.close();
                ftpClient.logout();
                flag = true;
                log.info("上传文件成功");
            }catch (Exception e) {
                log.info("上传文件失败");
                log.error("uploadFile Exception error :" + e);
            }finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(null != inputStream){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
        	log.info("文件保存本地");
            //**	64生产环境**//
            try {
                uploadFile(pathname, fileName, new FileInputStream(new File(originfilename)));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }
    /**
     * 上传文件
     * @param pathname ftp服务保存地址
     * @param fileName 上传到ftp的文件名
     * @param inputStream 输入文件流 
     * @return
     */
    public boolean uploadFile( String pathname, String fileName,InputStream input){
        if(isServer) {
        	
        	log.info("文件上传FTP");
            boolean flag = false;
            try{
                log.info("如果已存在删除文件");
                initFtpClient();
                try {
                    ftpClient.changeWorkingDirectory(pathname);
                    ftpClient.dele(fileName);
                } catch (Exception e) {
                    log.info("删除文件失败");
                }
                log.info("开始上传文件");
                ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
                log.info("文件夹开始创建");
                flag= CreateDirecroty(pathname);
                log.info("文件夹创建");
                ftpClient.makeDirectory(pathname);
                ftpClient.changeWorkingDirectory(pathname);
                ftpClient.storeFile(fileName, input);
                ftpClient.logout();
                flag = true;
                log.info("上传文件成功");
            }catch (Exception e) {
                log.info("上传文件失败");
                log.error("uploadFile Exception error :" + e);
            }finally{
                if(ftpClient.isConnected()){
                    try{
                        ftpClient.disconnect();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
                if(null != input){
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
        	log.info("文件保存本地");
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile;
            try {

                if (!new File(pathname).exists())
                    new File(pathname).mkdir();

                downloadFile = new FileOutputStream(pathname + "/" + fileName);
                while ((index = input.read(bytes)) != -1) {
                    downloadFile.write(bytes, 0, index);
                    downloadFile.flush();
                }
                downloadFile.close();
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                log.info("uploadFile IOException " + e.getMessage());
            }
        }
        return true;
    }
    //改变目录路径
     public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
            	log.info("进入文件夹" + directory + " 成功！");

            } else {
            	log.info("进入文件夹" + directory + " 失败！开始创建文件夹");
            }
        } catch (IOException ioe) {
        	log.error("changeWorkingDirectory IOException error :" + ioe);
            ioe.printStackTrace();
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                    	log.info("创建目录[" + subDirectory + "]失败");
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    //判断ftp服务器文件是否存在    
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }
    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
            	log.info("创建文件夹" + dir + " 成功！");

            } else {
            	log.info("创建文件夹" + dir + " 失败！");
            }
        } catch (Exception e) {
        	log.error("makeDirectory Exception error :" + e);
            e.printStackTrace();
        }
        return flag;
    }
    
    /** * 下载文件 * 
    * @param pathname FTP服务器文件目录 * 
    * @param filename 文件名称 * 
    * @param localpath 下载后的文件路径 * 
    * @return */
    public  boolean downloadFile(String pathname, String filename, String localpath){ 
        boolean flag = false; 
        OutputStream os=null;
        try { 
        	log.info("开始下载文件");
        	initFtpClient();
            //切换FTP目录 
            ftpClient.changeWorkingDirectory(pathname); 
            FTPFile[] ftpFiles = ftpClient.listFiles(); 
            for(FTPFile file : ftpFiles){ 
                if(filename.equalsIgnoreCase(file.getName())){ 
                    File localFile = new File(localpath + "/" + file.getName()); 
                    os = new FileOutputStream(localFile); 
                    ftpClient.retrieveFile(file.getName(), os); 
                } 
            } 
            ftpClient.logout(); 
            flag = true; 
            log.info("下载文件成功");
        } catch (Exception e) { 
        	log.info("下载文件失败");
        	log.error("downloadFile Exception error :" + e);
            e.printStackTrace(); 
        } finally{ 
            if(ftpClient.isConnected()){ 
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            } 
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            } 
        } 
        return flag; 
    }
    
    /** * 删除文件 * 
    * @param pathname FTP服务器保存目录 * 
    * @param filename 要删除的文件名称 * 
    * @return */ 
    public boolean deleteFile(String pathname, String filename){ 
        boolean flag = false; 
        try { 
        	log.info("开始删除文件");
        	initFtpClient();
            //切换FTP目录 
            ftpClient.changeWorkingDirectory(pathname); 
            ftpClient.dele(filename); 
            ftpClient.logout();
            flag = true; 
            log.info("删除文件成功");
        } catch (Exception e) { 
        	log.info("删除文件失败");
            e.printStackTrace(); 
        } finally {
            if(ftpClient.isConnected()){ 
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            } 
        }
        return flag; 
    }
    
    //读取文件
    public InputStream readFile(String filename){
    	InputStream input =null;
    	log.info("读取文件加载");
    	if(isServer){
    		try {
        		initFtpClient();
        		log.info("开始读取文件");
        		input = ftpClient.retrieveFileStream(filename);
        		log.info("读取文件结束");
        		//ftpClient.logout();
    		} catch (IOException e) {
    			log.info("读取文件失败"+e);
    			e.printStackTrace();
    		}finally {
                 if(ftpClient.isConnected()){ 
                     try{
                         ftpClient.disconnect();
                     }catch(IOException e){
                         e.printStackTrace();
                     } 
                 } 
             }
    	}else{
    		try {
				input = new FileInputStream(filename);
			} catch (FileNotFoundException e) {
				log.info("读取文件失败"+e);
				e.printStackTrace();
			}
    	}
    	
    	return input;
    }
    
    public static void main(String[] args) {
        FtpUtils ftp =new FtpUtils(); 
        //ftp.uploadFile("ftpFile/data", "123.docx", "E://123.docx");
        //ftp.downloadFile("ftpFile/data", "123.docx", "F://");
        ftp.deleteFile("ftpFile/data", "123.docx");
        System.out.println("ok");
    }
}