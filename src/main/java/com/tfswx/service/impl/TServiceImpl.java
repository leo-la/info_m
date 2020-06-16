package com.tfswx.service.impl;

import com.tfswx.template.Templates;
import com.tfswx.dao.Dao;
import com.tfswx.factory.PageTemplateFactory;
import com.tfswx.pojo.*;
import com.tfswx.service.TService;
import com.tfswx.template.AbstractPageTemplate;
import com.tfswx.utils.DateUtils;
import com.tfswx.utils.InfoFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(TServiceImpl.class);

    /**
     * 文件上传地址
     */
    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    @Autowired
    Dao dao;

    @Override
    public List<Directory> searchNo_1DirPage(Integer depid) {
        List<Directory> directories = dao.listDirectory(depid);
        Collections.sort(directories);
        return directories;
    }

    @Override
    public PageBean searchNo_2DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.TWO_DIRECTORY_PAGE);
        return template.run(pageBean,dao);
    }

    @Override
    public Directory searchDirById(Integer id) {
        return dao.getDirById(id);
    }

    @Override
    public PageBean searchNo_3DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.THREE_DIRECTORY_PAGE);
        return template.run(pageBean,dao);
    }

    @Override
    public PageBean searchNo_4DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.Four_DIRECTORY_PAGE);
        return template.run(pageBean,dao);
    }

    @Override
    public void addNo_3File(FileInfo fileInfo) {
        fileInfo.setCreatetime(DateUtils.getNowDate());
        fileInfo.setType(0);
        dao.insertNo_3File(fileInfo);
        DirectoryTwo fileDir = dao.getFileDir(fileInfo.getVersionid());
        if(fileDir!=null){
            fileDir.setVersionnum(fileDir.getVersionnum()+1);
            dao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
        }
    }

    @Override
    public void reuploadFile(FileInfo fileInfo) {
        fileInfo.setCreatetime(DateUtils.getNowDate());
        dao.updateFile(fileInfo);
    }

    @Override
    public void addNo_2Dir(String name, Integer dirid) {
        Date date = new Date();
        dao.insertNo_2Dir(name,date,dirid,0);
    }

    @Override
    public void addNO_1Dir(Directory directory) {
        dao.insert1LevelDir(directory);
        directory.setSortnum(directory.getId());
        dao.updateDirSortNum(directory);
    }

    @Override
    public void addNo_3Dir(FileInfo fileInfo) {
        fileInfo.setCreatetime(DateUtils.getNowDate());
        fileInfo.setType(1);
        fileInfo.setDownload(0);
        if(fileInfo.getDescription().length()==0){
            fileInfo.setDescription(null);
        }
        dao.insertFourDir(fileInfo);
        DirectoryTwo fileDir = dao.getFileDir(fileInfo.getVersionid());
        if(fileDir!=null){
            fileDir.setVersionnum(fileDir.getVersionnum()+1);
            dao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
        }
    }

    @Override
    public void deleteNo_3File(Integer id) {
        FileInfo file = dao.getFileById(id);
        Integer versionid = file.getVersionid();
        DirectoryTwo fileDir = dao.getFileDir(versionid);
        if(fileDir!=null){
            dao.updateVersionNum(versionid,fileDir.getVersionnum()-1);
        }
        dao.deleteFile(id);

        if(file.getName().contains(".")){
            int i = file.getName().lastIndexOf('.');
            String dirname = staticWordFilePath + "\\" + file.getName().substring(0,i);
            InfoFileUtil.deleteAllFilesOfDir(new File(dirname));
            InfoFileUtil.deleteFile(dirname+".pdf");
            InfoFileUtil.deleteFile(dirname+".html");
            InfoFileUtil.deleteFile(staticWordFilePath + "\\" + file.getName());
            InfoFileUtil.deleteFile(dirname+".pdf");
        }else {
            String dirname = staticWordFilePath + "\\" + file.getName();
            InfoFileUtil.deleteAllFilesOfDir(new File(dirname));
            InfoFileUtil.deleteFile(dirname);
        }
    }

    @Override
    public void deleteFileDir(Integer id) {
        List<Integer> ids = new ArrayList<>();
        List<Integer> id2s = dao.listFileBy2Dirid(id);
        ids.addAll(id2s);
        for (Integer id2 : id2s) {
            List<Integer> id3s = dao.listFileBy2Dirid(id2);
            ids.addAll(id3s);
        }
        for (Integer integer : ids) {
            dao.deleteFile(integer);
        }
        Integer integer = dao.deleteFileDir(id);
    }

    @Override
    public void deleteNo_1Dir(Integer id) {
        List<Integer> ids = new ArrayList<>();
        List<Integer> id2s = dao.listFileBy1Dirid(id);
        for (Integer id2 : id2s) {
            List<Integer> id3s = dao.listFileBy2Dirid(id2);
            ids.addAll(id3s);
            for (Integer id3 : id3s) {
                List<Integer> id4s = dao.listFileBy2Dirid(id3);
                ids.addAll(id4s);
            }
        }
        for (Integer i : ids) {
            dao.deleteFile(i);
        }
        for (Integer id2 : id2s) {
            dao.deleteFileDir(id2);
        }
        dao.deleteTDir(id);
    }

    @Override
    public void updateNo_2Dir(Integer id, String name) {
        dao.updateDirName(id,name);
    }

    @Override
    public void updateNO_1Dir(Directory directory) {
        dao.updateNO_1Dir(directory);
    }

    @Override
    public void updateNo_3Dir(FileInfo fileInfo) {
        dao.updateNo_3Dir(fileInfo);
    }

    @Override
    public String searchFileUrl(Integer id) {
        return dao.getFileUrl(id);
    }

    @Override
    public void sortDir(Integer op, Integer id) {
        Directory opp = dao.getDirById(op);
        Directory now = dao.getDirById(id);
        Integer temp = opp.getSortnum();
        opp.setSortnum(now.getSortnum());
        now.setSortnum(temp);
        dao.updateDirSortNum(opp);
        dao.updateDirSortNum(now);
    }
}

