package com.tfswx.utils;

import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.officePO.WordHtml;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.UUID;

import static com.tfswx.utils.ItextUtils.createPdf;

/**
 * 通用工具类
 */
public class CommonUtils {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CommonUtils.class);
    /**
     * restFul字符串参数转换
     *
     * @param str
     * @return
     */
    public static Integer restFulConverter(String str) {
        String s1 = str.substring(1, str.length() - 1);
        Integer i = Integer.parseInt(s1);
        return i;
    }

    /**
     * 时间转换器
     *
     * @return
     */
    public static SimpleDateFormat timeFormater() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format;
    }

    /**
     * Date转LocalDate
     */
    public static LocalDate DateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    public static String periodTime(LocalDate oldDate) {

        LocalDate today = LocalDate.now();

        Period p = Period.between(oldDate, today);
        if (p.getYears() > 0) {
            return p.getYears() + "年前";
        } else if (p.getYears() == 0 && p.getMonths() > 0) {
            return p.getMonths() + "个月前";
        } else if (p.getMonths() == 0 && p.getDays() > 0) {
            return p.getDays() + "天前";
        } else {
            return "今天";
        }
    }

    /**
     * 上传文件
     *
     * @param file
     * @param staticWordFilePath
     * @param isReupload 是否是重新上传
     */
    public static FileInfo uploadFile(MultipartFile file, FileInfo fileInfo, String staticWordFilePath, boolean isReupload) {
        String filename = "";
        try {
            //文件名唯一化处理
            filename = file.getOriginalFilename();
            if (filename.contains(".")) {
                int i = filename.lastIndexOf(".");
                filename = filename.substring(0,i)+"_" + UUID.randomUUID().toString().replace("-", "")
                        + filename.substring(i,filename.length());
            } else {
                filename = filename + "_" + UUID.randomUUID().toString().replace("-", "");
            }

            //存储路径
            String filePath = staticWordFilePath + "/" + filename;
            fileInfo.setUrl(filePath);
            fileInfo.setName(filename);

            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileInfo;
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
            os.close();
        } catch (IOException r) {
            r.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public static void te(String path,HttpServletResponse response){

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
            WordHtml wordHtml = new WordHtml();
            wordHtml.createHtml(inputFile, outputFile);
            FileTools.changeImageType(outputFile + ".html");//编辑html文件图片格式
            FileTools.checkHtmlEndTag(outputFile + ".html");

            createPdf(outputFile + ".html",outputFile);

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
            FileTools.close(bos,bis);

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
            FileTools.close(bos,bis);
        }else {
            return;
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

}
