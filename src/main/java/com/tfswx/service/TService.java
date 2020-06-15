package com.tfswx.service;

import com.tfswx.pojo.*;

import java.util.List;

public interface TService {
    /**
     * 查找目录信息
     * @return
     */
    List<Directory> searchNo_1DirPage(Integer depid);

    /**
     * 查找目录预览文件
     * @param dirid
     * @return
     */
    List<VersionFile> searchVersionFiles(Integer dirid);

    /**
     * 查找版本文件信息集
     * @param pageBean
     * @return
     */
    PageBean searchNo_2DirPage(PageBean pageBean);

    /**
     * 查询文件
     * @param id
     * @return
     */
    FileInfo searchFileById(Integer id);

    /**
     * 查询目录
     * @param id
     * @return
     */
    Directory searchDirById(Integer id);

    /**
     * 查询文件
     * @param name
     * @return
     */
    FileInfo searchFileByName(String name);

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
    RequestResult addNo_3File(FileInfo fileInfo);

    /**
     * 文件重新上传
     * @param fileInfo
     * @return
     */
    RequestResult reuploadFile(FileInfo fileInfo);

    /**
     * 增加文件目录
     * @param name
     * @param dirid
     * @return
     */
    RequestResult addNo_2Dir(String name, Integer dirid);

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    RequestResult addNO_1Dir(Directory directory);

    /**
     * 添加三级级目录
     * @param fileInfo
     * @return
     */
    RequestResult addNo_3Dir(FileInfo fileInfo);

    /**
     * 删除文件
     * @param id
     * @return
     */
    RequestResult deleteNo_3File(Integer id);

    /**
     * 删除目录
     * @param id
     * @return
     */
    Boolean deleteFileDir(Integer id);

    /**
     * 删除一级目录
     * @param id
     * @return
     */
    RequestResult deleteNo_1Dir(Integer id);

    /**
     * 更新目录名
     * @param id
     * @param name
     * @return
     */
    RequestResult updateNo_2Dir(Integer id, String name);

    /**
     * 更新一级目录名
     * @param directory
     * @return
     */
    RequestResult updateNO_1Dir(Directory directory);

    /**
     * 更新3级目录名
     * @param fileInfo
     * @return
     */
    RequestResult updateNo_3Dir(FileInfo fileInfo);

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
    RequestResult sortDir(Integer opid,Integer id);


}
