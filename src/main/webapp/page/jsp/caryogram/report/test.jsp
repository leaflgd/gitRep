<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>    
    <head>    
        <meta name="layout" content="main">    
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />      
        <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/jquery.min.js"></script>    
        <script type="text/javascript" src="${pageContext.request.contextPath}/page/js/plug/html2canvas.js"></script>    
             
    </head>    
    <body>    
             
          <h2>文件上传</h2>
    <form action="/chromosome/chCount/upload" enctype="multipart/form-data" method="post">
        <table>
            <tr>
                <td>文件描述:</td>
                <td><input type="text" name="description"></td>
            </tr>
            <tr>
                <td>请选择文件:</td>
                <td><input type="file" name="file" webkitdirectory></td>
            </tr>
            <tr>
                <td><input type="submit" value="上传"></td>
            </tr>
        </table>
    </form>    
    </body>    
</html>  