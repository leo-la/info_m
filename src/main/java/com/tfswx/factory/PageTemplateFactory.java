package com.tfswx.factory;

import com.tfswx.common.PageTemplateType;
import com.tfswx.template.*;

/**
 * 页面数据模板工厂
 */
public class PageTemplateFactory {
    public static PageSearchTemplate createTemplate(PageTemplateType type){
        switch (type){
            case MEMBER_PAGE:
                return new ManagerPage();
            case TWO_DIRECTORY_PAGE:
                return new TwoDirectoryPage();
            case THREE_DIRECTORY_PAGE:
                return new ThreeDirectoryPage();
            case ROLE_PAGE:
                return  new RolePage();
            case Four_DIRECTORY_PAGE:
                return  new FourDirectoryPage();
                default:
                    return null;
        }
    }
}


