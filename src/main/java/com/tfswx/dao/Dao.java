package com.tfswx.dao;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.DirectoryTwo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Date;
import java.util.List;

@Mapper
@Repository
@CacheConfig(cacheNames="T")
public interface Dao extends BaseDao{

    /**
     * 查找公司信息管理目录集
     * @return
     */
    List<Directory> listNo_1DirPage(Integer depid);

    /**
     * 统计公司信息管理版本文件页面信息
     * @param dirid
     * @return
     */
    Integer countNo_2DirFiles(Integer dirid);

    /**
     * 查询公司信息管理版本文件页面信息
     * @param start
     * @param size
     * @param dirid
     * @return
     */
    List<DirectoryTwo> listNo_2DirPage(Integer dirid,Integer start,Integer size);

    /**
     * 查询公司信息管理文件历史版本信息
     * @param start
     * @param size
     * @param id
     * @return
     */

    List<FileInfo> listNo_3DirPage(Integer start,Integer size, Integer id);

    /**
     * 统计公司信息管理文件历史版本数
     * @param parentid
     * @return
     */
    Integer countNo_3DirFiles(Integer parentid);

    /**
     * 统计四级目录数据
     * @param id
     * @return
     */
    Integer countNo_4DirFiles(Integer id);

    /**
     * 查找文件
     * @param id
     * @return
     */
    FileInfo getFileById(Integer id);

    /**
     * 查询四级目录页面信息
     * @param start
     * @param size
     * @param id
     * @return
     */
    List<FileInfo> listNo_4DirPage(Integer start,Integer size, Integer id);

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
     * 插入文件信息
     * @param fileInfo
     * @return
     */
    Integer insertNo_3File(FileInfo fileInfo);

    /**
     * 创建文件目录
     * @param name
     * @param dirid
     * @return
     */
    Integer insertNo_2Dir(String name,Date createtime,Integer dirid,Integer versionnum);

    /**
     * 创建一级目录
     * @param directory
     * @return
     */
    Integer insert1LevelDir(Directory directory);

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
    Integer updateNo_3Dir(FileInfo fileInfo);

    /**
     * 更新文件
     * @param fileInfo
     * @return
     */
    Integer updateFile(FileInfo fileInfo);

    /**
     * 查询文件目录
     * @param parentid
     * @return
     */
    DirectoryTwo getFileDir(Integer parentid);

    /**
     * 查询目录
     * @param id
     * @return
     */
    Directory getDirById(Integer id);

    /**
     * 更新一级目录名
     * @param directory
     * @return
     */
    Integer updateNO_1Dir(Directory directory);

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
    @CacheEvict(keyGenerator = "keyGenerator")
    Integer deleteFile(Integer id);

    /**
     * 删除文件
     * @param ids
     * @return
     */
    @CacheEvict(keyGenerator = "keyGenerator")
    Integer deleteFiles(List<Integer> ids);

    /**
     * 删除目录
     * @param id
     * @return
     */
    @CacheEvict(keyGenerator = "keyGenerator")
    Integer deleteFileDir(Integer id);

    /**
     * 删除一级目录
     * @return
     */
    @CacheEvict(keyGenerator = "keyGenerator")
    Integer deleteTDir(Integer id);


}
