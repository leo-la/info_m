package com.tfswx.utils;

public class WordText implements AbstractText {

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordText.class);

  @Override
  public void createTxt(String inputFile, String outputFile) throws Exception {
    String content = TikaUtils.parse(inputFile);
    FileHelper.writeFile(content, outputFile + ".txt");
  }

}

