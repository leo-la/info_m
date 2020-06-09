package com.tfswx.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class InfoFileUtil {

    /**
     * 上传文件
     *
     * @param file
     * @param name
     * @param parentPath
     */
    public static String uploadFile(MultipartFile file, String name, String parentPath) {
        String filename = "";
        try {

            //存储路径
            filename = parentPath + "/" + name;

            File targetFile = new File(filename);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @param request
     * @param response
     */
    public static void downloadFile(String fileUrl, HttpServletRequest request, HttpServletResponse response) {
        String ext = "";
        String fileName = "";
        File file = null;
        try {
            file = new File(fileUrl);
            fileName = file.getName();
            ext = fileName.substring(fileName.lastIndexOf(".") + 1);
            String agent = (String) request.getHeader("USER-AGENT"); //判断浏览器类型
            if (agent != null && agent.indexOf("Fireforx") != -1) {
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");   //UTF-8编码，防止输出文件名乱码
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 初始化读入字节流和读出字节流，并设置响应response的输出属性。
        BufferedInputStream bis = null;
        OutputStream os = null;
        response.reset();
        response.setCharacterEncoding("utf-8");
        if (ext == "docx") {
            response.setContentType("application/msword"); // word格式
        } else if (ext == "pdf") {
            response.setContentType("application/pdf"); // word格式
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        //调用inputStream和outputStream的read、write函数进行IO流读写操作。关闭输出流。
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] b = new byte[bis.available() + 1000];
            int i = 0;
            os = response.getOutputStream();   //直接下载导出
            while ((i = bis.read(b)) != -1) {
                os.write(b, 0, i);
            }
            os.flush();
        } catch (IOException r) {
            r.printStackTrace();
        } finally {
           close(os);
        }
    }

    /**
     * 关闭资源
     * @param io
     */
    public static void close(Closeable...io){
        for (Closeable closeable : io) {
            if(closeable!=null){
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
