package com.tfswx.template;


import com.tfswx.dao.BaseDao;
import com.tfswx.dao.Dao;
import com.tfswx.pojo.DirectoryTwo;
import com.tfswx.pojo.PageBean;

import java.util.List;

/**
 * 用户页面数据查询模板
 */
public class TwoDirectoryAbstractPage extends AbstractPageTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        Dao Dao = (Dao) dao;
        pageBean.setTotalCount(Dao.countNo_2DirFiles(pageBean.getId()));
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
        List<DirectoryTwo> versionFiles = Dao.listNo_2DirPage(pageBean.getId(),start, size);
        pageBean.setPageData(versionFiles);
        return pageBean;
    }

}
