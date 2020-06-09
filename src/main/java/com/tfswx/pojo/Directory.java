package com.tfswx.pojo;

public class Directory implements Comparable<Directory>{
    private Integer id;
    private String dirname;
    private Integer depid;
    private String enname;
    private Integer sortnum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public Integer getDepid() {
        return depid;
    }

    public void setDepid(Integer depid) {
        this.depid = depid;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    @Override
    public int compareTo(Directory o) {
        return this.sortnum-o.getSortnum();
    }
}
