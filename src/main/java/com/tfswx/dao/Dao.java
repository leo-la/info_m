package com.tfswx.dao;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.VersionFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface Dao extends BaseDao{

    /**
     * 查找公司信息管理目录集
     * @return
     */
    List<Directory> listDirectory(Integer depid);

    /**
     * 查找公司信息管理文件预览（3）
     * @param dirid
     * @return
     */
    List<VersionFile> listVersionFiles(Integer dirid);

    /**
     * 查找主页最新文件预览信息
     * @param dirid
     * @return
     */
    List<FileInfo> listNewFilePreview(Integer dirid);

    /**
     * 统计公司信息管理版本文件页面信息
     * @param dirid
     * @return
     */
    Integer countVersionFiles(Integer dirid);

    /**
     * 查询公司信息管理版本文件页面信息
     * @param start
     * @param size
     * @param dirid
     * @return
     */
    List<VersionFile> listVersionFilesPage(Integer start,Integer size, Integer dirid);

    /**
     * 统计公司信息管理文件历史版本数
     * @param versionid
     * @return
     */
    Integer countHistoryVersionsByVersionid(Integer versionid);

    /**
     * 查找文件
     * @param id
     * @return
     */
    FileInfo getFileById(Integer id);


    /**
     * 查询公司信息管理文件历史版本信息
     * @param start
     * @param size
     * @param id
     * @return
     */
    List<FileInfo> listHistoryVersionPageByVersionid(Integer start,Integer size, Integer id);

    /**
     * 插入公司信息管理新版本文件信息
     * @param dirid
     * @param name
     * @return
     */
    Integer insertNewVersionFile(Integer dirid, String name, Date date);


    /**
     * 查询公司信息管理版本文件表最大数据id值
     * @return
     */
    Integer getVersionFIleMaxId();

    /**
     * 插入文件信息
     * @param versionid
     * @param name
     * @param createtime
     * @param description
     * @param filePath
     * @return
     */
    Integer insertNewFile(Integer versionid,String name,Date createtime,String description,String filePath,Integer download);

    /**
     * 创建文件目录
     * @param name
     * @param dirid
     * @return
     */
    Integer insertFileDir(String name,Date createtime,Integer dirid,Integer versionnum);

    /**
     * 创建一级目录
     * @param name
     * @param enname
     * @param depid
     * @return
     */
    Integer insertTDir(String name,String enname,Integer depid);

    /**
     * 更新文件版本数量
     * @param versionnum
     * @return
     */
    Integer updateVersionNum(Integer id,Integer versionnum);

    /**
     * 更新目录名称
     * @param dirid
     * @param name
     * @return
     */
    Integer updateDirName(Integer dirid,String name);

    /**
     * 查询文件目录
     * @param versionid
     * @return
     */
    VersionFile getFileDir(Integer versionid);

    /**
     * 查询目录
     * @param id
     * @return
     */
    Directory getDirById(Integer id);

    /**
     * 更新一级目录名
     * @param id
     * @param name
     * @param enname
     * @return
     */
    Integer updateTDirName(Integer id,String name, String enname);

    /**
     * 查询文件路径
     * @param id
     * @return
     */
    String getFileUrl(Integer id);

    /**
     * 查询文件
     * @param name
     * @return
     */
    FileInfo getFileByName(String name);

    /**
     * 删除文件
     * @param id
     * @return
     */
    Integer deleteFile(Integer id);

    /**
     * 删除目录
     * @param id
     * @return
     */
    Integer deleteFileDir(Integer id);

    /**
     * 删除一级目录
     * @return
     */
    Integer deleteTDir(Integer id);

}
