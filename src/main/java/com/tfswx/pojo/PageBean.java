package com.tfswx.pojo;

import java.io.Serializable;
import java.util.List;

public class PageBean implements Serializable {
    private Integer currentPage;
    private Integer totalPage;
    private Integer pageSize;
    private Integer totalCount;
    private List pageData;
    private List pageData2;
    private String str;
    private Integer id;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public List getPageData2() {
        return pageData2;
    }

    public void setPageData2(List pageData2) {
        this.pageData2 = pageData2;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}