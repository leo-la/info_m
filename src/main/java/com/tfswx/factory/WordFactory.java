package com.tfswx.factory;

import com.tfswx.pojo.officePO.WordHtml;
import com.tfswx.pojo.officePO.WordPdf;
import com.tfswx.pojo.officePO.WordText;
import com.tfswx.utils.CommonUtils;
import com.tfswx.utils.FileTools;

import static com.tfswx.utils.ItextUtils.createPdf;

public class WordFactory {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordFactory.class);

  /**
   * 将word转换为html
   * 
   * @param inputFile 需要转换的word文档
   * @param outputFile 转换为html文件名称（全路径）
   * @throws Exception
   */
  public static void convert2Html(String inputFile, String outputFile,String staticpath) throws Exception {
    log.info("将word转换为html文件开始,输出文件 [" + outputFile + ".html]......");
    long startTime = System.currentTimeMillis();
    //word -> html
    WordHtml wordHtml = new WordHtml();
    wordHtml.createHtml(inputFile, outputFile);
    FileTools.changeImageType(outputFile + ".html");//编辑html文件图片格式
    FileTools.checkHtmlEndTag(outputFile + ".html");
    log.info("Generate " + outputFile + ".html with " + (System.currentTimeMillis() - startTime)
        + " ms.");
  }

  public static void convert2Text(String fileName, String outPutFile) throws Exception {
    log.info("start convert Word to txt,out file [" + outPutFile + ".txt]......");
    long startTime = System.currentTimeMillis();
    WordText text = new WordText();
    text.createTxt(fileName, outPutFile);
    log.info("将Word转换为Word文件......ok");
    log.info("convert success! Generate " + outPutFile + ".Word with "
        + (System.currentTimeMillis() - startTime) + " ms.");
  }

  public static boolean convert2Pdf(String fileName, String outPutFile) throws Exception {
    log.info("start convert Word to pdf,out file [" + outPutFile + ".pdf]......");
    long startTime = System.currentTimeMillis();
    WordPdf html = new WordPdf();
    html.createPdf(fileName, outPutFile);
    log.info("将Word转换为pdf文件......ok");
    log.info("convert success! Generate " + outPutFile + ".pdf with "
        + (System.currentTimeMillis() - startTime) + " ms.");
    return false;
  }

  public static boolean convert2Html2Pdf(String inputFile, String outputFile,String staticpath) throws Exception {
    //word -> html
    WordHtml wordHtml = new WordHtml();
    wordHtml.createHtml(inputFile, outputFile);
    FileTools.changeImageType(outputFile + ".html");//编辑html文件图片格式
    FileTools.checkHtmlEndTag(outputFile + ".html");
    //html -> pdf
    createPdf(outputFile + ".html", outputFile);
    return false;
  }

  public static void main(String[] args) throws Exception {
//    convert2Html("C:\\Users\\S1\\Desktop\\王祖贤.docx","C:\\Users\\S1\\Desktop\\gongju2");
  }

}

