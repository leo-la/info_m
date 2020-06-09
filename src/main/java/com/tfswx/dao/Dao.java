package com.tfswx.dao;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.VersionFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.io.File;
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
     * 统计四级目录数据
     * @param id
     * @return
     */
    Integer countFilesByThreeDirid(Integer id);

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
     * 查询四级目录页面信息
     * @param start
     * @param size
     * @param id
     * @return
     */
    List<FileInfo> listFilesByThreeDirid(Integer start,Integer size, Integer id);

    /**
     * 查询2级目录下文件
     * @param id
     * @param id
     * @return
     */
    List<Integer> listFileBy2Dirid(Integer id);

    /**
     * 查询1级目录下文件
     * @param id
     * @param id
     * @return
     */
    List<Integer> listFileBy1Dirid(Integer id);

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
     * @param fileInfo
     * @return
     */
    Integer insertNewFile(FileInfo fileInfo);

    /**
     * 创建文件目录
     * @param name
     * @param dirid
     * @return
     */
    Integer insertFileDir(String name,Date createtime,Integer dirid,Integer versionnum);

    /**
     * 创建一级目录
     * @param directory
     * @return
     */
    Integer insertTDir(Directory directory);

    /**
     * 创建三级级目录
     * @param fileInfo
     * @return
     */
    Integer insertFourDir(FileInfo fileInfo);

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
     * 更新3级目录名称
     * @param fileInfo
     * @return
     */
    Integer update3Dir(FileInfo fileInfo);

    /**
     * 更新文件
     * @param fileInfo
     * @return
     */
    Integer updateFile(FileInfo fileInfo);

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
     * 更新目录排序
     * @param dir
     * @return
     */
    Integer updateDirSortNum(Directory dir);

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
     * 删除文件
     * @param ids
     * @return
     */
    Integer deleteFiles(List<Integer> ids);

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
