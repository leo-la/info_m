package com.tfswx.template;


import com.tfswx.dao.BaseDao;
import com.tfswx.dao.Dao;
import com.tfswx.dao.PermissionDao;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.User;
import com.tfswx.pojo.VersionFile;

import java.util.List;

/**
 * 用户页面数据查询模板
 */
public class TwoDirectoryPage extends PageSearchTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        Dao Dao = (Dao) dao;
        pageBean.setTotalCount(Dao.countVersionFiles(pageBean.getId()));
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
        List<VersionFile> versionFiles = Dao.listVersionFilesPage(start, size, pageBean.getId());
        pageBean.setPageData(versionFiles);
        return pageBean;
    }

}
