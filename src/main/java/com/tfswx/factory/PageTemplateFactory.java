package com.tfswx.factory;

import com.tfswx.common.Templates;
import com.tfswx.template.*;

/**
 * 页面数据模板工厂
 */
public class PageTemplateFactory {
    public static AbstractPageTemplate createTemplate(Templates type){
        switch (type){
            case MEMBER_PAGE:
                return new ManagerAbstractPage();
            case TWO_DIRECTORY_PAGE:
                return new TwoDirectoryAbstractPage();
            case THREE_DIRECTORY_PAGE:
                return new ThreeDirectoryAbstractPage();
            case ROLE_PAGE:
                return  new RoleAbstractPage();
            case Four_DIRECTORY_PAGE:
                return  new FourDirectoryAbstractPage();
                default:
                    return null;
        }
    }
}


