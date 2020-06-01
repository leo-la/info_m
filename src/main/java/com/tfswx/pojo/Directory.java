package com.tfswx.pojo;

import lombok.Data;

@Data
public class Directory implements Comparable<Directory>{
    private Integer id;
    private String dirname;
    private Integer depid;
    private String enname;
    private Integer sortnum;

    @Override
    public int compareTo(Directory o) {
        return this.sortnum-o.getSortnum();
    }
}
