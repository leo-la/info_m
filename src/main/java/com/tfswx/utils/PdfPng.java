package com.tfswx.utils;


public class PdfPng implements AbstractPng {

  @Override
  public void createPng(String inputFile, String outputFile) throws Exception {
    ImageUtils.convertPdf2Png(inputFile, outputFile);
  }

}

