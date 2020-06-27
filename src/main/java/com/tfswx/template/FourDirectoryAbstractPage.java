package com.tfswx.template;

import com.tfswx.dao.BaseDao;
import com.tfswx.dao.Dao;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.utils.CommonUtils;

import java.util.List;

/**
 * 公司信息管理历史版本文件数据查询模板
 */
public class FourDirectoryAbstractPage extends AbstractPageTemplate {

    @Override
    public PageBean getTotalCount(PageBean pageBean, BaseDao dao) {
        Dao Dao = (Dao) dao;
        pageBean.setTotalCount(Dao.countNo_4DirFiles(pageBean.getId()));
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
        List<FileInfo> fileInfos = Dao.listNo_4DirPage(start, size, pageBean.getId());
        for (FileInfo fileInfo : fileInfos) {
            fileInfo.setName(CommonUtils.operateFileNames(fileInfo.getName()));
        }
        pageBean.setPageData(fileInfos);
        return pageBean;
    }
}
