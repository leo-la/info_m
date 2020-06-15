package com.tfswx.template;

import com.tfswx.dao.BaseDao;
import com.tfswx.pojo.PageBean;

/**
 * 页面数据查询模板骨架
 */
public abstract class AbstractPageTemplate {

    /**
     * 设置总记录数
     * @param pageBean
     * @return
     */
    public abstract PageBean getTotalCount(PageBean pageBean,BaseDao dao);

    /**
     * 设置当前页码
     * @param pageBean
     * @return
     */
    public abstract PageBean getCurrentPage(PageBean pageBean);
    /**
     * 设置总页数
     * @param pageBean
     * @return
     */
    public abstract PageBean getTotalPage(PageBean pageBean);

    /**
     * 设置数据
     * @param pageBean
     * @return
     */
    public abstract PageBean getData(PageBean pageBean,BaseDao dao);

    /**
     * 执行流程
     * @param pageBean
     * @param baseDao
     * @return
     */
    public PageBean run(PageBean pageBean,BaseDao baseDao){
        pageBean = getTotalCount(pageBean, baseDao);
        pageBean = getCurrentPage(pageBean);
        pageBean = getTotalPage(pageBean);
        pageBean = getData(pageBean,baseDao);
        return pageBean;
    }
}
