package com.tfswx.utils;


import com.tfswx.utils.abstractOffice.AbstractPdf;

public class WordPdf implements AbstractPdf {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordPdf.class);

  @Override
  public void createPdf(String inputFile, String outputFile) throws Exception {
    String message = TikaUtils.parse(inputFile);
    ItextUtils.createSimplePdf(message, outputFile);
  }

}

