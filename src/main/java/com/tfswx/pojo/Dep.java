package com.tfswx.pojo;

import java.io.Serializable;

public class Dep implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String depname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname;
    }

}
