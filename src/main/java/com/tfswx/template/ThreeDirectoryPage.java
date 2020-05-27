package com.tfswx.template;

import com.tfswx.dao.BaseDao;
import com.tfswx.dao.Dao;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.VersionFile;

import java.util.List;

/**
 * 公司信息管理历史版本文件数据查询模板
 */
public class ThreeDirectoryPage extends PageSearchTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        Dao Dao = (Dao) dao;
        pageBean.setTotalCount(Dao.countHistoryVersionsByVersionid(pageBean.getId()));
        return pageBean;
    }

    @Override
    public PageBean getCurrentPage(PageBean pageBean) {
        if(pageBean.getCurrentPage()==null){
            pageBean.setCurrentPage(1);
        }
        return pageBean;
    }

    @Override
    public PageBean getTotalPage(PageBean pageBean) {
        Integer totalPage = pageBean.getTotalCount() % pageBean.getPageSize() == 0 ?
                pageBean.getTotalCount() / pageBean.getPageSize() : (pageBean.getTotalCount() / pageBean.getPageSize() + 1);
        pageBean.setTotalPage(totalPage);
        return pageBean;
    }

    @Override
    public PageBean getData(PageBean pageBean,BaseDao dao) {
        Dao Dao = (Dao) dao;
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<FileInfo> fileInfos = Dao.listHistoryVersionPageByVersionid(start, size, pageBean.getId());
        for (FileInfo fileInfo : fileInfos) {
            if(fileInfo.getName().contains("_")){
                String name = fileInfo.getName().substring(0, fileInfo.getName().indexOf("_"));
                fileInfo.setName(name);
            }else if(fileInfo.getName().contains(".")){
                String name = fileInfo.getName().substring(0, fileInfo.getName().indexOf("."));
                fileInfo.setName(name);
            }

        }
        pageBean.setPageData(fileInfos);
        return pageBean;
    }
}
