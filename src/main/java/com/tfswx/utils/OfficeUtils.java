package com.tfswx.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
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
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.IURIResolver;
import org.apache.poi.xwpf.converter.core.IXWPFConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Iterator;
import java.util.List;

public class OfficeUtils {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OfficeUtils.class);

    /**
     * 判断word文件版本（是否为word2003）
     *
     * @param file
     * @return
     */
    public static boolean isWord2003(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            new HWPFDocument(is);
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 判断word版本（是否为word2007）
     *
     * @param file
     * @return
     */
    public static boolean isWord2007(File file) {
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            new XWPFDocument(is).close();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 创建html文档
     *
     * @param inputFile
     * @param outputFile
     * @throws Exception
     */
    public static void createHtml(String inputFile, String outputFile) throws Exception {
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
            if (isWord2003(input)) { //判断word文件版本
                convertDoc2Html(is, outputFile);
            } else if (isWord2007(input)) {
                convertDocx2Html(is, outputFile);
            } else {
                parseToHTML(inputFile, outputFile);
            }
        } finally {
            InfoFileUtil.close(is);
        }
        log.info("创建[ " + outputFile + ".html]文件，耗时[" + (System.currentTimeMillis() - startTime)
                + " ms].");
    }


    /**
     * 解析转换html
     *
     * @param fileName
     * @param outPutFile
     * @return
     * @throws IOException
     * @throws SAXException
     * @throws TikaException
     */
    public static String parseToHTML(String fileName, String outPutFile){
        ContentHandler handler = new ToHTMLContentHandler();
        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        InputStream stream = null;
        try {
            stream = new FileInputStream(new File(fileName));
            parser.parse(stream, handler, metadata);

            InfoFileUtil.writeFile(handler.toString(), outPutFile + ".html");

            parse(outPutFile + ".html");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    /**
     * @param htmFilePath
     * @throws IOException
     */
    public static void parse(String htmFilePath) throws IOException {
        File htmFile = new File(htmFilePath);
        // 以GB2312读取文件
        org.jsoup.nodes.Document doc = Jsoup.parse(htmFile, "utf-8");
        String xmlns = doc.getElementsByTag("html").attr("xmlns");
        if (null == xmlns || "".equals(xmlns)) {
            return;
        }
        doc.getElementsByTag("html").removeAttr("xmlns");
        Element head = doc.head();
        /*
         * Elements headChildren = head.children(); for(Element children : headChildren) { Elements
         * metas = children.getElementsByTag("meta"); for(Element meta : metas) { meta.remove(); } }
         */
        head.appendElement("meta").attr("name", "viewport").attr("content",
                "width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no");

        // 获取html节点
        Element element = doc.body();
        Elements content = head.getElementsByAttributeValueStarting("name", "meta:page-count");
        for (Element meta : content) {
            String value = meta.attr("content");
            try {
                Integer count = Integer.valueOf(value);
                Elements ps = element.getElementsByTag("p");
                Iterator<Element> iterator = ps.iterator();
                while (iterator.hasNext()) {
                    Element p = iterator.next();
                    String text = p.text();
                    if (text.equals("- " + count + " -")) {
                        for (int offset = count; offset > 0; offset--) {
                            p.remove();
                            p = iterator.next();
                            text = p.text();
                        }
                    }
                    if (text.equals("")) {
                        p.remove();
                        p = iterator.next();
                    }
                    p.attr("align", "center");
                    p.attr("style", "font-size:1.5rem;");
                    break;
                }
            } catch (Exception e) {

            }
            // 获取content节点，修改charset属性
            // meta.attr("content", "text/html; charset=utf-8");
            break;
        }
        // 转换成utf-8编码的文件写入
        FileUtils.writeStringToFile(htmFile, "<!DOCTYPE html>" + doc.html(), "utf-8");
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
            InfoFileUtil.writeFile(content, outPutFile + ".html");
            // FileHelper.parseCharset(outPutFile + ".html");
            // System.out.println(new String(out.toByteArray()));
        } finally {
            InfoFileUtil.close(out);
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
            InfoFileUtil.close(out,document);
        }
    }

    /**
     * 更改html文件图片格式
     *
     * @param htmFilePath
     * @throws IOException
     */
    public static void changeImageType(String htmFilePath) throws IOException {
        File htmFile = new File(htmFilePath);
        // 以GB2312读取文件
        org.jsoup.nodes.Document doc = Jsoup.parse(htmFile, "utf-8");
        Elements div = doc.getElementsByTag("div");
        div.attr("style", "width:100%;margin-bottom:72.0pt;margin-top:72.0pt;margin-left:0%;margin-right:86.0pt;");

        Elements table = doc.getElementsByTag("table");
        table.attr("style", "width:100.0%;border-collapse:collapse;");

        Elements elements = doc.getElementsByTag("img");
        String imgPath = "";
        for (Element element : elements) {
            String src = element.attr("src");
            String[] sp = src.split("\\.");
            String newSrc = htmFile.getParent() + File.separator + sp[0] + ".png";
            imgPath = src;
            element.attr("src", newSrc);
        }
        FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");
    }

    /**
     *
     * @param htmFilePath
     * @throws IOException
     */
    public static void checkHtmlEndTag(String htmFilePath) throws IOException {
        File htmFile = new File(htmFilePath);
        // 以GB2312读取文件
        org.jsoup.nodes.Document doc = Jsoup.parse(htmFile, "utf-8");
        Elements all = doc.getElementsByTag("html");
        for (Element element : all) {
            parseElements(all, element);
        }
        FileUtils.writeStringToFile(htmFile, doc.html(), "utf-8");
    }

    /**
     * 解析元素
     * @param elements
     * @param element
     */
    public static void parseElements(Elements elements, Element element) {
        int childNodeSize = elements.size();
        if (0 < childNodeSize) {
            for (int offset = 0; offset < childNodeSize; offset++) {
                parseElements(elements.get(offset).children(), elements.get(offset));
            }
        } else {
            String tagName = element.tagName();
            String content = element.toString();
            if (tagName.length() + 3 > content.length()) {
                element.text("");
            } else {
                try {
                    String endTag =
                            content.substring(content.length() - tagName.length() - 3, content.length());
                    if (!("</" + tagName + ">").equals(endTag)) {
                        element.text("");
                    }
                } catch (Exception w) {
                }
            }
        }
    }

    /**
     * 获取文件css样式
     *
     * @param src 文件
     * @return 文件css样式
     * @throws IOException 打开文件异常
     */
    public static final StringBuffer getHtmlCss(String src) throws IOException {
        File htmFile = new File(src);
        // 以GB2312读取文件
        org.jsoup.nodes.Document doc = Jsoup.parse(htmFile, "utf-8");
        Elements styles = doc.getElementsByTag("style");
        StringBuffer csStringBuffer = new StringBuffer();
        for (Element style : styles) {
            csStringBuffer.append(style.toString().replace("<style>", "").replace("</style>", ""));
        }
        Elements links = doc.getElementsByTag("link");
        for (Element style : links) {
            String href = style.attr("href");
            String realPath = src + File.separator + href;
            StringBuffer link = InfoFileUtil.readFile(realPath);
            csStringBuffer.append(link);
        }

        return csStringBuffer;

    }

    /**
     * 创建文档对象
     *
     * @param file
     * @return
     */
    public static final Object[] getDocument(String file) {
        com.itextpdf.text.Document document = null;
        PdfWriter writer = null;
        Object[] objects = new Object[2];
        try {
            // Step 1—Create a Document.
            Rectangle pageSize = new Rectangle(PageSize.A4); // 页面大小设置为A4
            document = new com.itextpdf.text.Document(pageSize); // 创建doc对象并设置边距
            // Step 2—Get a PdfWriter instance.
            writer = PdfWriter.getInstance(document, new FileOutputStream(file + ".pdf"));
            // Step 3—Open the Document.
            document.open();
            document.newPage();
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

    /**
     *html -> pdf
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public static void createPdf(String HTML, String file) throws IOException, DocumentException {
        // step 1
        com.itextpdf.text.Document document = null;
        InputStream is = null;
        PdfWriter writer = null;
        try {
            Object[] objects = getDocument(file);
            document = (com.itextpdf.text.Document) objects[0];
            writer = (PdfWriter) objects[1];
            // step 4
            // CSS
            CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
            StringBuffer cssBuffer = getHtmlCss(HTML);
            CssFile cssFile =
                    XMLWorkerHelper.getCSS(new ByteArrayInputStream(cssBuffer.toString().getBytes()));
            cssResolver.addCss(cssFile);

            // HTML
            CssAppliers cssAppliers = new CssAppliersImpl(new FontProvider() {
                @Override
                public boolean isRegistered(String s) {
                    return false;
                }

                @Override
                public Font getFont(String s, String s1, boolean b, float v, int i, BaseColor baseColor) {
                    return setChineseFont();
                }
            });
            HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
            htmlContext.setImageProvider(new Base64ImageProvider());

            // Pipelines
            PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
            HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
            CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

            // XML Worker
            XMLWorker worker = new XMLWorker(css, true);
            XMLParser p = new XMLParser(worker);
            StringBuffer buffer = InfoFileUtil.readFile(HTML);
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

    static class Base64ImageProvider extends AbstractImageProvider {
        @Override
        public Image retrieve(String src) {
            int pos = src.indexOf("base64,");
            try {
                if (src.startsWith("data") && pos > 0) {
                    byte[] img = Base64.decode(src.substring(pos + 7));
                    return Image.getInstance(img);
                } else {
                    return Image.getInstance(src);
                }
            } catch (BadElementException ex) {
                return null;
            } catch (IOException ex) {
                return null;
            }
        }
        @Override
        public String getImageRootPath() {
            return null;
        }
    }



    /**
     * 设置中文字体
     * @return
     */
    public static Font setChineseFont() {
        BaseFont bf = null;
        Font fontChinese = null;
        try {
            bf = BaseFont.createFont("SIMKAI.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            fontChinese = new Font(bf, 12, Font.NORMAL);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fontChinese;
    }
}
