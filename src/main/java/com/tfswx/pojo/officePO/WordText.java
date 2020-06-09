package com.tfswx.pojo.officePO;

import com.tfswx.utils.FileTools;
import com.tfswx.utils.TikaUtils;

public class WordText{

  private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WordText.class);

  public void createTxt(String inputFile, String outputFile) throws Exception {
    String content = TikaUtils.parse(inputFile);
    FileTools.writeFile(content, outputFile + ".txt");
  }

}

