package com.tfswx.service.impl;

import com.tfswx.common.Templates;
import com.tfswx.controller.ResultHandler;
import com.tfswx.dao.Dao;
import com.tfswx.factory.PageTemplateFactory;
import com.tfswx.pojo.*;
import com.tfswx.service.TService;
import com.tfswx.template.AbstractPageTemplate;
import com.tfswx.utils.CommonUtils;
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
public class TServiceImpl extends ResultHandler implements TService {

    private static final Logger LOG = LoggerFactory.getLogger(TServiceImpl.class);

    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    @Autowired
    Dao firmDao;

    @Override
    public List<Directory> searchNo_1DirPage(Integer depid) {
        List<Directory> directories = firmDao.listDirectory(depid);
        Collections.sort(directories);
        return directories;
    }

    @Override
    public List<VersionFile> searchVersionFiles(Integer dirid) {
        return firmDao.listVersionFiles(dirid);
    }

    @Override
    public PageBean searchNo_2DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.TWO_DIRECTORY_PAGE);
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
    public PageBean searchNo_3DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.THREE_DIRECTORY_PAGE);
        return template.run(pageBean,firmDao);
    }

    @Override
    public PageBean searchNo_4DirPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.Four_DIRECTORY_PAGE);
        return template.run(pageBean,firmDao);
    }

    @Override
    public RequestResult addNo_3File(FileInfo fileInfo) {
        try {
            fileInfo.setCreatetime(DateUtils.getNowDate());
            fileInfo.setType(0);
            firmDao.insertNo_3File(fileInfo);
            VersionFile fileDir = firmDao.getFileDir(fileInfo.getVersionid());
            if(fileDir!=null){
                fileDir.setVersionnum(fileDir.getVersionnum()+1);
                firmDao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
            }
            return success(null, "");
        } catch (Exception e) {
            LOG.error("重新上传文件失败-Failed to reupload a file:  [{}]",e.getMessage());
            return failure(null, "");
        }
    }

    @Override
    public RequestResult reuploadFile(FileInfo fileInfo) {
        Integer integer = null;
        try {
            fileInfo.setCreatetime(DateUtils.getNowDate());
            firmDao.updateFile(fileInfo);
            return success(null, "");
        } catch (Exception e) {
            LOG.error("重新上传文件失败-Failed to reupload a file:  [{}]",e.getMessage());
            return failure(null, "");
        }
    }

    @Override
    public RequestResult addNo_2Dir(String name, Integer dirid) {
        try {
            Date date = new Date();
            firmDao.insertNo_2Dir(name,date,dirid,0);
            return success(null, "");
        } catch (Exception e) {
            LOG.error("创建二级目录失败-Failed to create two level directory:  [{}]",e.getMessage());
            return failure(null, "");
        }
    }

    @Override
    public RequestResult addNO_1Dir(Directory directory) {
        try {
            firmDao.insert1LevelDir(directory);
            directory.setSortnum(directory.getId());
            firmDao.updateDirSortNum(directory);
            return success(null, "");
        } catch (Exception e) {
            LOG.error("创建一级目录失败-Failed to create one level directory: 错误信息：[{}],栈堆跟踪：[{}]",e.getMessage(),e.getStackTrace());
            throw new RuntimeException();
        }
    }

    @Override
    public RequestResult addNo_3Dir(FileInfo fileInfo) {
        try {
            fileInfo.setCreatetime(DateUtils.getNowDate());
            fileInfo.setType(1);
            fileInfo.setDownload(0);
            if(fileInfo.getDescription().length()==0){
                fileInfo.setDescription(null);
            }
            firmDao.insertFourDir(fileInfo);
            VersionFile fileDir = firmDao.getFileDir(fileInfo.getVersionid());
            if(fileDir!=null){
                fileDir.setVersionnum(fileDir.getVersionnum()+1);
                firmDao.updateVersionNum(fileInfo.getVersionid(),fileDir.getVersionnum());
            }
            return success("","");
        } catch (Exception e) {
            LOG.error("创建三级目录失败-Failed to create the three level directory: [{}]",e.getMessage());
            return failure(null,"");
        }
    }

    @Override
    public RequestResult deleteNo_3File(Integer id) {
        try {
            FileInfo file = firmDao.getFileById(id);
            Integer versionid = file.getVersionid();
            VersionFile fileDir = firmDao.getFileDir(versionid);
            if(fileDir!=null){
                firmDao.updateVersionNum(versionid,fileDir.getVersionnum()-1);
            }
            firmDao.deleteFile(id);

            if(file.getName().contains(".")){
                int i = file.getName().lastIndexOf(".");
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
            return success("","");
        } catch (Exception e) {
            LOG.error("删除三级目录文件失败-Failed to delete the three level file: [{}]",e.getMessage());
            return failure(null,"");
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
    public RequestResult deleteNo_1Dir(Integer id) {
        Integer integer = null;
        try {
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
            firmDao.deleteTDir(id);
            return success("","");
        } catch (Exception e) {
            LOG.error("删除一级目录失败-Failed to delete the one level directory:  [{}]",e.getMessage());
            return failure(null,"");
        }
    }

    @Override
    public RequestResult updateNo_2Dir(Integer id, String name) {
        try {
            firmDao.updateDirName(id,name);
            return success(null,"");
        } catch (Exception e) {
            LOG.error("更新二级目录失败-Failed to update the two level directory:  [{}]",e.getMessage());
            return failure(null,"");
        }
    }

    @Override
    public RequestResult updateNO_1Dir(Directory directory) {
        try {
            firmDao.updateNO_1Dir(directory);
            return success(null,"");
        } catch (Exception e) {
            LOG.error("更新一级目录失败-Failed to update the one level directory:  [{}]",e.getMessage());
            return failure(null,"");
        }
    }

    @Override
    public RequestResult updateNo_3Dir(FileInfo fileInfo) {
        try {
            firmDao.updateNo_3Dir(fileInfo);
            return success(null,"");
        } catch (Exception e) {
            LOG.error("更新3级目录失败-Failed to update the Three level directory:  [{}]",e.getMessage());
            return failure(null,"");
        }
    }

    @Override
    public String searchFileUrl(Integer id) {

        return firmDao.getFileUrl(id);
    }

    @Override
    public RequestResult sortDir(Integer op, Integer id) {
        try{
            Directory opp = firmDao.getDirById(op);
            Directory now = firmDao.getDirById(id);
            Integer temp = opp.getSortnum();
            opp.setSortnum(now.getSortnum());
            now.setSortnum(temp);
            firmDao.updateDirSortNum(opp);
            firmDao.updateDirSortNum(now);
            return success(null,"");
        } catch (Exception e) {
            LOG.error("排序目录失败-Failed to sort directory: [{}]",e.getMessage());
            return failure(null,"");
        }
    }
}

