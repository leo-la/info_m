package com.tfswx.utils;

import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class InfoFileUtil {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonUtils.class);

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
     * 预览文件
     *
     * @param inputFile
     * @param response
     */
    public static void previewFile(String inputFile, HttpServletResponse response,String staticpath) throws Exception {
        String outputFile = "";
        String suffix = "";
        //字符处理
        if(inputFile.contains(".")){
            int i = inputFile.lastIndexOf(".");
            outputFile = inputFile.substring(0,i);
            suffix = inputFile.substring(i,inputFile.length());
        }
        if(suffix.equals(".doc")||suffix.equals(".docx")){

            //word -> html
            OfficeUtils.createHtml(inputFile, outputFile);
            OfficeUtils.changeImageType(outputFile + ".html");//编辑html文件图片格式
            OfficeUtils.checkHtmlEndTag(outputFile + ".html");

            OfficeUtils.createPdf(outputFile + ".html",outputFile);

            response.setContentType("application/pdf");
            //预览
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(outputFile+".pdf")));//资源路径（相对/绝对）
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());//输出路径
            //中转站   一个字节(水杯)
            //中转站使用 一个 字节数组  (水桶)
            byte [] buf = new byte [1024];
            int n = bis.read(buf);
            while(n!=-1){
                bos.write(buf,0,n);//写
                n = bis.read(buf);//再读
            }
            //手动刷新输出流的缓冲区
            //bos.flush();
            //关闭
            close(bos,bis);

        }else if(suffix.equals(".pdf")){
            response.setContentType("application/pdf");
            //预览
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(outputFile+".pdf"));//资源路径（相对/绝对）
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());//输出路径
            //中转站   一个字节(水杯)
            //中转站使用 一个 字节数组  (水桶)
            byte [] buf = new byte [1024];
            int n = bis.read(buf);
            while(n!=-1){
                bos.write(buf,0,n);//写
                n = bis.read(buf);//再读
            }
            //手动刷新输出流的缓冲区
            //bos.flush();
            //关闭
            close(bos,bis);
        }else {
            return;
        }
    }

    /**
     * 将传入的字符串写入到指定的路径的文件下
     *
     * @param content 将要写入文件的内容
     * @param path    写入内容的文件路径
     */
    public static void writeFile(String content, String path) {
        OutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException ioException) {
                System.err.println(ioException.getMessage());
            }
        }
    }

    /**
     * 删除文件夹
     * @param path
     */
    public static void deleteAllFilesOfDir(File path) {

        if (null != path) {
            if (!path.exists())
                return;
            if (path.isFile()) {
                boolean result = path.delete();
                int tryCount = 0;
                while (!result && tryCount++ < 10) {
                    System.gc(); // 回收资源
                    result = path.delete();
                }
            }
            File[] files = path.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    deleteAllFilesOfDir(files[i]);
                }
            }
            path.delete();
            log.info("["+path+"]已经被成功删除");
        }
    }
    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static StringBuffer readFile(String path) {
        StringBuffer buffer = new StringBuffer();
        InputStream is = null;
        BufferedReader br = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                is = new FileInputStream(file);
                br = new BufferedReader(new InputStreamReader(is));
                String content = br.readLine();
                while (null != content) {
                    buffer.append(content);
                    content = br.readLine();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(is, br);
        }
        return buffer;
    }


    /**
     * 删除文件
     * @param pathname
     * @return
     */
    public static boolean deleteFile(String pathname){
        boolean result = false;
        File file = new File(pathname);
        if (file.exists()) {
            file.delete();
            result = true;
            log.info("["+pathname+"]已经被成功删除");
        }
        return result;
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
