package com.tfswx.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.tfswx.factory.UnicodeFontFactory;
import com.tfswx.service.TService;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.UUID;

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
     * @param versionid
     * @param description
     * @param download
     * @param staticWordFilePath
     * @param tService
     */
    public static void uploadFile(MultipartFile file, Integer versionid, String description, Boolean download, String staticWordFilePath, TService tService) {


        Integer downl = 0;
        if (null != download && download == true) {
            downl = 1;
        }
        try {
            String filename = file.getOriginalFilename();
            if (filename.contains(".")) {
                int i = filename.lastIndexOf(".");
                filename = filename.substring(0,i)+"_" + UUID.randomUUID().toString().replace("-", "")
                        + filename.substring(i,filename.length());
            } else {
                filename = filename + "_" + UUID.randomUUID().toString().replace("-", "");
            }

            String realPath = staticWordFilePath;
            String filePath = realPath + "\\" + filename;

            tService.addNewFile(versionid, filename, description, filePath, downl);

            File targetFile = new File(filePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

//            之后，初始化读入字节流和读出字节流，并设置响应response的输出属性。

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
//            接着调用inputStream和outputStream的read、write函数进行IO流读写操作。最后在写出操作完成后，一定要关闭输出流。

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

    /**
     * 预览文件
     *
     * @param inputFile
     * @param response
     */
    public static void previewFile(String inputFile, HttpServletResponse response) throws Exception {
        String outputFile = "";
        String suffix = "";
        //字符处理
        if(inputFile.contains(".")){
            int i = inputFile.lastIndexOf(".");
            outputFile = inputFile.substring(0,i);
            suffix = inputFile.substring(i,inputFile.length());
        }
        if(suffix.equals(".doc")||suffix.equals(".docx")){

            WordHtml wordHtml = new WordHtml();
            wordHtml.createHtml(inputFile, outputFile);

            FileHelper.changeImageType(outputFile + ".html");
            FileHelper.checkHtmlEndTag(outputFile + ".html");

            createPdf(outputFile + ".html", outputFile);

            response.setContentType("application/pdf");
            FileInputStream in = new FileInputStream(new File(outputFile+".pdf"));
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[512];
            while ((in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        }else if(suffix.equals(".pdf")){
            response.setContentType("application/pdf");
            FileInputStream in = new FileInputStream(new File(outputFile+".pdf"));
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[512];
            while ((in.read(b)) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
        }else {
            return;
        }
    }

    public static void createPdf(String HTML, String file) {
        // step 1
        Document document = null;
        InputStream is = null;
        PdfWriter writer = null;
        try {
            Object[] objects = getDocument(file);
            document = (Document) objects[0];
            writer = (PdfWriter) objects[1];
            // step 4
            // CSS
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
            StringBuffer cssBuffer = FileHelper.getHtmlCss(HTML);
            CssFile cssFile =
                    XMLWorkerHelper.getCSS(new ByteArrayInputStream(cssBuffer.toString().getBytes()));
            cssResolver.addCss(cssFile);

            // HTML
            CssAppliers cssAppliers = new CssAppliersImpl(new UnicodeFontFactory());
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new ItextUtils.Base64ImageProvider());

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            StringBuffer buffer = FileHelper.readFile(HTML);
            p.parse(new ByteArrayInputStream(buffer.toString().getBytes()));
            p.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // step 5
                if (null != document) document.close();
                if (null != is) is.close();
                if (null != writer) writer.close();
            } catch (Exception e) {

            }
        }
    }

    public static final Object[] getDocument(String file) {
        Document document = null;
        PdfWriter writer = null;
        Object[] objects = new Object[2];
        try {
            // Step 1—Create a Document.
            document = new Document(PageSize.A4.rotate());
            // Step 2—Get a PdfWriter instance.
            writer = PdfWriter.getInstance(document, new FileOutputStream(file + ".pdf"));
            // Step 3—Open the Document.
            document.open();
            objects[0] = document;
            objects[1] = writer;
        } catch (Exception e) {
            try {
                if (null != document) document.close();
                if (null != writer) writer.close();
            } finally {

            }
        }

        return objects;
    }

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
