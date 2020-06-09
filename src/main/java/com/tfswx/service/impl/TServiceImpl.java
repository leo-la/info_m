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
import java.text.SimpleDateFormat;
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
    public PageBean searchFourLevelDirPage(PageBean pageBean) {
        PageSearchTemplate template = PageTemplateFactory.createTemplate(PageTemplateType.Four_DIRECTORY_PAGE);
        return template.run(pageBean,firmDao);
    }

    @Override
    public String addNewFile(FileInfo fileInfo) {
        Date date = new Date();
        SimpleDateFormat format = CommonUtils.timeFormater();
        fileInfo.setCreatetime(format.format(date));
        fileInfo.setType(0);
        Integer integer = firmDao.insertNewFile(fileInfo);

        if(integer==1){
            VersionFile fileDir = firmDao.getFileDir(fileInfo.getVersionid());
            if(fileDir!=null){
                fileDir.setVersionnum(fileDir.getVersionnum()+1);
                firmDao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
            }

            return "插入成功!";
        }else{
            return "插入失败!";
        }
    }

    @Override
    public String reuploadFile(FileInfo fileInfo) {
        Date date = new Date();
        SimpleDateFormat format = CommonUtils.timeFormater();
        fileInfo.setCreatetime(format.format(date));
        Integer integer = firmDao.updateFile(fileInfo);
        if(integer==1){
            return "更新成功!";
        }else{
            return "更新失败!";
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
    public Boolean addFourDir(FileInfo fileInfo) {
        Date date = new Date();
        SimpleDateFormat format = CommonUtils.timeFormater();
        fileInfo.setCreatetime(format.format(date));
        fileInfo.setType(1);
        fileInfo.setDownload(0);
        if(fileInfo.getDescription().length()==0){
            fileInfo.setDescription(null);
        }
        Integer integer =  firmDao.insertFourDir(fileInfo);
        if(integer==1){
            VersionFile fileDir = firmDao.getFileDir(fileInfo.getVersionid());
            if(fileDir!=null){
                fileDir.setVersionnum(fileDir.getVersionnum()+1);
                firmDao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
            }

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
        if(fileDir!=null){
            firmDao.updateVersionNum(versionid,fileDir.getVersionnum()-1);
        }
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
        List<Integer> ids = new ArrayList<>();
        List<Integer> id2s = firmDao.listFileBy2Dirid(id);
        ids.addAll(id2s);
        for (Integer id2 : id2s) {
            List<Integer> id3s = firmDao.listFileBy2Dirid(id2);
            ids.addAll(id3s);
        }
        for (Integer integer : ids) {
            firmDao.deleteFile(integer);
        }
        Integer integer = firmDao.deleteFileDir(id);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean deleteTDir(Integer id) {
        List<Integer> ids = new ArrayList<>();
        List<Integer> id2s = firmDao.listFileBy1Dirid(id);
        for (Integer id2 : id2s) {
            List<Integer> id3s = firmDao.listFileBy2Dirid(id2);
            ids.addAll(id3s);
            for (Integer id3 : id3s) {
                List<Integer> id4s = firmDao.listFileBy2Dirid(id3);
                ids.addAll(id4s);
            }
        }
        for (Integer i : ids) {
            firmDao.deleteFile(i);
        }
        for (Integer id2 : id2s) {
            firmDao.deleteFileDir(id2);
        }
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
    public Boolean update3Dir(FileInfo fileInfo) {
        Integer integer = firmDao.update3Dir(fileInfo);
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

