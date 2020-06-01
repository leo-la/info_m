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
     * 上传新版本文件
     * @return
     */
    String addNewVersionFile(Integer dirid, String filename, String description, String filePath, Integer download);

    /**
     * 多文件上传
     * @param versionid
     * @param filename
     * @param filePath
     * @return
     */
    String addNewFile(Integer versionid, String filename, String description, String filePath, Integer download);

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
     * 查询文件路径
     * @param id
     * @return
     */
    String searchFileUrl(Integer id);

    /**
     * 向前移动
     * @param lastid
     * @param id
     * @return
     */
    Boolean sortFront(Integer lastid,Integer id);

    /**
     * 目录后移
     * @param nextid
     * @param id
     * @return
     */
    Boolean sortDown(Integer nextid,Integer id);
}
