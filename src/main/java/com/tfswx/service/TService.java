package com.tfswx.service;

import com.tfswx.exception.ResultBody;
import com.tfswx.pojo.*;

import java.util.List;

public interface TService {
    /**
     * 查找目录信息
     * @return
     */
    List<Directory> searchNo_1DirPage(Integer depid);

    /**
     * 查找版本文件信息集
     * @param pageBean
     * @return
     */
    PageBean searchNo_2DirPage(PageBean pageBean);

    /**
     * 查询目录
     * @param id
     * @return
     */
    Directory searchDirById(Integer id);

    /**
     * 查找历史版本文件目录
     * @param pageBean
     * @return
     */
    PageBean searchNo_3DirPage(PageBean pageBean);

    /**
     * 查询四级分类目录页面信息
     * @param pageBean
     * @return
     */
    PageBean searchNo_4DirPage(PageBean pageBean);

    /**
     * 文件上传
     * @param fileInfo
     * @return
     */
    void addNo_3File(FileInfo fileInfo);

    /**
     * 文件重新上传
     * @param fileInfo
     * @return
     */
    void reuploadFile(FileInfo fileInfo);

    /**
     * 增加文件目录
     * @param name
     * @param dirid
     * @return
     */
    void addNo_2Dir(String name, Integer dirid);

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    void addNO_1Dir(Directory directory);

    /**
     * 添加三级级目录
     * @param fileInfo
     * @return
     */
    void addNo_3Dir(FileInfo fileInfo);

    /**
     * 删除文件
     * @param id
     * @return
     */
    void deleteNo_3File(Integer id);

    /**
     * 删除目录
     * @param id
     * @return
     */
    void deleteFileDir(Integer id);

    /**
     * 删除一级目录
     * @param id
     * @return
     */
    void deleteNo_1Dir(Integer id);

    /**
     * 更新目录名
     * @param id
     * @param name
     * @return
     */
    void updateNo_2Dir(Integer id, String name);

    /**
     * 更新一级目录名
     * @param directory
     * @return
     */
    void updateNO_1Dir(Directory directory);

    /**
     * 更新3级目录名
     * @param fileInfo
     * @return
     */
    void updateNo_3Dir(FileInfo fileInfo);

    /**
     * 查询文件路径
     * @param id
     * @return
     */
    String searchFileUrl(Integer id);

    /**
     * 排序目录
     * @param opid
     * @param id
     * @return
     */
    void sortDir(Integer opid,Integer id);


}
