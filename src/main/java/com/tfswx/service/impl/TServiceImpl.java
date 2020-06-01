package com.tfswx.service.impl;

import com.tfswx.common.PageTemplateType;
import com.tfswx.dao.Dao;
import com.tfswx.factory.PageTemplateFactory;
import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.VersionFile;
import com.tfswx.service.TService;
import com.tfswx.template.PageSearchTemplate;
import com.tfswx.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * 公司信息管理服务
 */
@Service
@Transactional
public class TServiceImpl implements TService {

    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    @Autowired
    Dao firmDao;

    @Override
    public List<Directory> searchDirectory(Integer depid) {
        List<Directory> directories = firmDao.listDirectory(depid);
        Collections.sort(directories);
        return directories;
    }

    @Override
    public List<VersionFile> searchVersionFiles(Integer dirid) {
        return firmDao.listVersionFiles(dirid);
    }

    @Override
    public PageBean searchVersionFilesPage(PageBean pageBean) {
        PageSearchTemplate template = PageTemplateFactory.createTemplate(PageTemplateType.TWO_DIRECTORY_PAGE);
        return template.run(pageBean,firmDao);
    }

    @Override
    public FileInfo searchFileById(Integer id) {
        FileInfo fileInfo = firmDao.getFileById(id);
        return fileInfo;
    }

    @Override
    public Directory searchDirById(Integer id) {

        return firmDao.getDirById(id);
    }

    @Override
    public FileInfo searchFileByName(String name) {

        return firmDao.getFileByName(name);
    }

    @Override
    public PageBean searchHistoryVersionPage(PageBean pageBean) {
        PageSearchTemplate template = PageTemplateFactory.createTemplate(PageTemplateType.THREE_DIRECTORY_PAGE);
        return template.run(pageBean,firmDao);
    }

    @Override
    public String addNewVersionFile(Integer dirid, String filename,String description,String filePath,Integer download) {
        Date date = new Date();
        Integer integer = firmDao.insertNewVersionFile(dirid,filename,date);
        Integer versionid = firmDao.getVersionFIleMaxId();
        Integer integer1 = firmDao.insertNewFile(versionid, filename, date, description,filePath,download);
        if(integer==0||integer1==0){
            return "插入失败!";
        }else{
            return "插入成功!";
        }
    }

    @Override
    public String addNewFile(Integer versionid, String filename,String description, String filePath,Integer download) {
        Date date = new Date();
        Integer integer = firmDao.insertNewFile(versionid, filename, date, description,filePath,download);

        if(integer==1){
            VersionFile fileDir = firmDao.getFileDir(versionid);
            fileDir.setVersionnum(fileDir.getVersionnum()+1);
            firmDao.updateVersionNum(versionid,fileDir.getVersionnum());
            return "插入成功!";
        }else{
            return "插入失败!";
        }
    }

    @Override
    public Boolean addFileDir(String name, Integer dirid) {
        Date date = new Date();
        Integer integer = firmDao.insertFileDir(name,date,dirid,0);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean addTDir(Directory directory) {
        firmDao.insertTDir(directory);
        directory.setSortnum(directory.getId());
        Integer integer = firmDao.updateDirSortNum(directory);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteFile(Integer id) {
        FileInfo file = firmDao.getFileById(id);
        Integer versionid = file.getVersionid();
        VersionFile fileDir = firmDao.getFileDir(versionid);
        firmDao.updateVersionNum(versionid,fileDir.getVersionnum()-1);
        Integer integer = firmDao.deleteFile(id);

        if(file.getName().contains(".")){
            int i = file.getName().lastIndexOf(".");
            String dirname = staticWordFilePath + "\\" + file.getName().substring(0,i);
            CommonUtils.deleteAllFilesOfDir(new File(dirname));
            CommonUtils.deleteFile(dirname+".pdf");
            CommonUtils.deleteFile(dirname+".html");
            CommonUtils.deleteFile(staticWordFilePath + "\\" + file.getName());
            CommonUtils.deleteFile(dirname+".pdf");
        }else {
            String dirname = staticWordFilePath + "\\" + file.getName();
            CommonUtils.deleteAllFilesOfDir(new File(dirname));
            CommonUtils.deleteFile(dirname);
        }

        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteFileDir(Integer id) {
        Integer integer = firmDao.deleteFileDir(id);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteTDir(Integer id) {
        Integer integer = firmDao.deleteTDir(id);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean updateDirName(Integer id, String name) {
        Integer integer = firmDao.updateDirName(id,name);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean updateTDirName(Integer id, String name, String enname) {
        Integer integer = firmDao.updateTDirName(id,name,enname);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String searchFileUrl(Integer id) {

        return firmDao.getFileUrl(id);
    }

    @Override
    public Boolean sortFront(Integer lastid, Integer id) {
        try{
            Directory last = firmDao.getDirById(lastid);
            Directory now = firmDao.getDirById(id);
            Integer temp = last.getSortnum();
            last.setSortnum(now.getSortnum());
            now.setSortnum(temp);
            firmDao.updateDirSortNum(last);
            firmDao.updateDirSortNum(now);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean sortDown(Integer nextid, Integer id) {
        try{
            Directory next = firmDao.getDirById(nextid);
            Directory now = firmDao.getDirById(id);
            Integer temp = next.getSortnum();
            next.setSortnum(now.getSortnum());
            now.setSortnum(temp);
            firmDao.updateDirSortNum(next);
            firmDao.updateDirSortNum(now);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

