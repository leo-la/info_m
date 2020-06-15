package com.tfswx.template;


import com.tfswx.dao.BaseDao;
import com.tfswx.dao.PermissionDao;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.Role;

import java.util.List;

/**
 * 用户页面数据查询模板
 */
public class RoleAbstractPage extends AbstractPageTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        PermissionDao PermissionDao = (PermissionDao) dao;
        pageBean.setTotalCount(PermissionDao.countRoles());
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
        PermissionDao PermissionDao = (PermissionDao) dao;
        Integer start = (pageBean.getCurrentPage()-1) * pageBean.getPageSize();
        Integer size = pageBean.getPageSize();
        List<Role> roles = PermissionDao.listRolesPage(start, size);
        pageBean.setPageData(roles);
        return pageBean;
    }
}
