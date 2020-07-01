package com.tfswx.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
}
