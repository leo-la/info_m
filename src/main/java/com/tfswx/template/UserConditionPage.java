package com.tfswx.template;

import com.tfswx.dao.BaseDao;
import com.tfswx.dao.PermissionDao;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.User;

import java.util.List;

/**
 * 用户页面数据查询模板
 */
public class UserConditionPage extends PageSearchTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        String str = pageBean.getStr();
        if(str.contains("%")){
            str = str.replaceAll("%", "\\\\%");
        }
        String condition = "%"+str+"%";
        PermissionDao userDao = (PermissionDao) dao;
        pageBean.setTotalCount(userDao.countUsersByCondition(condition));
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
    public PageBean getData(PageBean pageBean, BaseDao dao) {
        PermissionDao userDao = (PermissionDao) dao;
        String str = pageBean.getStr();
        if(str.contains("%")){
            str = str.replaceAll("%", "\\\\%");
        }
        String condition = "%"+str+"%";
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<User> users = userDao.listUserPageByCondition(start, size,condition);
        pageBean.setPageData(users);
        return pageBean;
    }
}
