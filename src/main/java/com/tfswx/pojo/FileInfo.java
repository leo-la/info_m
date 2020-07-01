package com.tfswx.pojo;

import lombok.Data;

@Data
public class FileInfo {
    private Integer id;
    private String name;
    private Integer depid;
    private String createtime;
    private String description;
    private Integer versionid;
    private String updatetime;
    private Integer download;
    private String url;
    private Integer type;
    private Integer level;

}
