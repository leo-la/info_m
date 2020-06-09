package com.tfswx.service;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.VersionFile;

import java.util.List;

public interface TService {
    /**
     * 查找目录信息
     * @return
     */
    List<Directory> searchDirectory(Integer depid);

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
    PageBean searchVersionFilesPage(PageBean pageBean);

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
    PageBean searchHistoryVersionPage(PageBean pageBean);

    /**
     * 查询四级分类目录页面信息
     * @param pageBean
     * @return
     */
    PageBean searchFourLevelDirPage(PageBean pageBean);

    /**
     * 文件上传
     * @param fileInfo
     * @return
     */
    String addNewFile(FileInfo fileInfo);

    /**
     * 文件重新上传
     * @param fileInfo
     * @return
     */
    String reuploadFile(FileInfo fileInfo);

    /**
     * 增加文件目录
     * @param name
     * @param dirid
     * @return
     */
    Boolean addFileDir(String name, Integer dirid);

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    Boolean addTDir(Directory directory);

    /**
     * 添加三级级目录
     * @param fileInfo
     * @return
     */
    Boolean addFourDir(FileInfo fileInfo);

    /**
     * 删除文件
     * @param id
     * @return
     */
    Boolean deleteFile(Integer id);

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
    Boolean deleteTDir(Integer id);

    /**
     * 更新目录名
     * @param id
     * @param name
     * @return
     */
    Boolean updateDirName(Integer id, String name);

    /**
     * 更新一级目录名
     * @param id
     * @param name
     * @param enname
     * @return
     */
    Boolean updateTDirName(Integer id, String name, String enname);

    /**
     * 更新3级目录名
     * @param fileInfo
     * @return
     */
    Boolean update3Dir(FileInfo fileInfo);

    /**
     * 查询文件路径
     * @param id
     * @return
     */
    String searchFileUrl(Integer id);

    /**
     * 排序目录
     * @param lastid
     * @param id
     * @return
     */
    Boolean sortDir(Integer lastid,Integer id);


}
