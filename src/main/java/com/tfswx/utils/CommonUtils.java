package com.tfswx.utils;


import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;

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


    public static String filenameUnique(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename.contains(".")) {
            int i = filename.lastIndexOf(".");
            filename = filename.substring(0,i)+"_" + UUID.randomUUID().toString().replace("-", "")
                    + filename.substring(i,filename.length());
        } else {
            filename = filename + "_" + UUID.randomUUID().toString().replace("-", "");
        }
        return filename;
    }
}
