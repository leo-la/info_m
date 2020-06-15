package com.tfswx.template;


import com.tfswx.dao.BaseDao;
import com.tfswx.dao.PermissionDao;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.User;

import java.util.List;

/**
 * 用户页面数据查询模板
 */
public class ManagerAbstractPage extends AbstractPageTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        PermissionDao userDao = (PermissionDao) dao;
        pageBean.setTotalCount(userDao.countManagers());
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
        PermissionDao userDao = (PermissionDao) dao;
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<User> users = userDao.listManagerPage(start, size);
        pageBean.setPageData(users);
        return pageBean;
    }
}
