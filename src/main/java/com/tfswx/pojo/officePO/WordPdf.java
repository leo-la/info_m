package com.tfswx.pojo.officePO;


import com.tfswx.utils.ItextUtils;
import com.tfswx.utils.TikaUtils;

public class WordPdf{

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordPdf.class);

  public void createPdf(String inputFile, String outputFile) throws Exception {
    String message = TikaUtils.parse(inputFile);
    ItextUtils.createImagePdf(message, outputFile);
  }

}

