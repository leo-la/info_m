package com.tfswx.pojo.officePO;

import com.tfswx.utils.FileTools;
import com.tfswx.utils.TikaUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.model.SEPX;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.core.IXWPFConverter;
import org.apache.poi.xwpf.converter.core.XWPFConverterException;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * word-html转换对象
 */
public class WordHtml {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordHtml.class);

    public void createHtml(String inputFile, String outputFile) throws Exception {
        long startTime = System.currentTimeMillis();
        InputStream is = null;
        try {
            File file = new File(outputFile);
            if (!file.exists()) {
                file.mkdirs();
                log.info("创建文件夹:" + outputFile);
            }

            File input = new File(inputFile);
            if (!file.exists()) {
                log.error("文件不存在:" + inputFile);
            }

            is = new FileInputStream(input);
            if (FileTools.isWord2003(input)) { //判断word文件版本
                convertDoc2Html(is, outputFile);
            } else if (FileTools.isWord2007(input)) {
                convertDocx2Html(is, outputFile);
            } else {
                TikaUtils.parseToHTML(inputFile, outputFile);
            }
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (Exception e) {

            }
        }
        log.info("创建[ " + outputFile + ".html]文件，耗时[" + (System.currentTimeMillis() - startTime)
                + " ms].");
    }

    /**
     * 将doc文档转换为html文件
     *
     * @param outPutFile 输出html文件的全路径
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     * @see
     * @since 1.7
     */
    private static void convertDoc2Html(InputStream is, String outPutFile)
            throws TransformerException, IOException, ParserConfigurationException {
        StreamResult streamResult = null;
        ByteArrayOutputStream out = null;
        try {
            String[] outPutFiles = outPutFile.split("\\\\");
            outPutFiles = outPutFiles[outPutFiles.length - 1].split("/");
            final String root = outPutFiles[outPutFiles.length - 1];
            // 将文件转换为poi数据结构
            HWPFDocument wordDocument = new HWPFDocument(is);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            // 获取word转换为html的句柄
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);

            // 设置html文件中图片引入路径
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
                                          float widthInches, float heightInches) {
                    return root + "/" + suggestedName;
                }
            });
            wordToHtmlConverter.processDocument(wordDocument);
            // #start save pictures
            List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        // 指定doc文档中转换后图片保存的路径
                        pic.writeImageContent(
                                new FileOutputStream(outPutFile + "/" + pic.suggestFullFileName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            // #end save pictures
            out = new ByteArrayOutputStream();
            streamResult = new StreamResult(out);

            TransformerFactory tf = TransformerFactory.newInstance();
            // 创建执行从 Source 到 Result 的复制的新 Transformer。
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); // 文件编码方式
            serializer.setOutputProperty(OutputKeys.INDENT, "yes"); // indent 指定了当输出结果树时，Transformer
            // 是否可以添加额外的空白；其值必须为 yes 或 no
            serializer.setOutputProperty(OutputKeys.METHOD, "html"); // 指定输出文件的后缀名
            Document htmlDocument = wordToHtmlConverter.getDocument();
            DOMSource domSource = new DOMSource(htmlDocument);
            serializer.transform(domSource, streamResult);
            String content = new String(out.toByteArray());
            FileTools.writeFile(content, outPutFile + ".html");
            // FileHelper.parseCharset(outPutFile + ".html");
            // System.out.println(new String(out.toByteArray()));
        } finally {
            if (null != out) {
                out.close();
            }
        }
    }

    /**
     * 将docx文件转换为html @param is @param fileOutName 输出文件的具体路径 @throws IOException @see @since
     * 1.7 @exception
     */
    private static void convertDocx2Html(InputStream is, String fileOutName) throws IOException {
        OutputStream out = null;
        XWPFDocument document = null;
        try {
            // final String root = fileOutName.substring(fileOutName.lastIndexOf("/") + 1);

            String[] outPutFiles = fileOutName.split("\\\\");
            outPutFiles = outPutFiles[outPutFiles.length - 1].split("/");
            final String root = outPutFiles[outPutFiles.length - 1];

            long startTime = System.currentTimeMillis();
            // 获取解析处理类
            document = new XWPFDocument(is);

            XHTMLOptions options = XHTMLOptions.create();// .indent(4);
            // Extract image
            File imageFolder = new File(fileOutName);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }
            // 设置图片保存路径
            options.setExtractor(new FileImageExtractor(imageFolder));
            // URI resolver
            options.URIResolver(new IURIResolver() {
                @Override
                public String resolve(String uri) {
                    return root + File.separatorChar + uri;
                }
            });
            out = new FileOutputStream(new File(fileOutName + ".html"));
            IXWPFConverter<XHTMLOptions> xhtmlCoverter = XHTMLConverter.getInstance();
            try {
                xhtmlCoverter.convert(document, out, options);
            } catch (Exception e) {
                log.info("Generate " + fileOutName + " 异常.");
            }
            // FileHelper.parseCharset(fileOutName + ".html");
            log.info(
                    "Generate " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");
        } finally {
            FileTools.close(out,document);
        }
    }
}

