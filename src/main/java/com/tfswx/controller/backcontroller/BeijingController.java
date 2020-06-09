package com.tfswx.controller.backcontroller;

import com.tfswx.controller.BaseController;
import com.tfswx.pojo.Directory;
import com.tfswx.pojo.PageBean;
import com.tfswx.service.TService;
import com.tfswx.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 北京分公司控制器
 */
@Controller
@RequestMapping("beijing")
public class BeijingController extends BaseController {

    @Autowired
    TService beijingService;

    /**
     * 添加一级目录
     *
     * @param directory
     * @return
     */
    @RequestMapping("addTDir")
    public String addTDir(Directory directory, Model model) {
        beijingService.addTDir(directory);
        model.addAttribute("id", 13);
        return "back/index";
    }

    /**
     * 更新一级分类目录名
     *
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("updateTDirName")
    public String updateTDirName(Integer id, String name, String enname, Model model) {
        beijingService.updateTDirName(id, name, enname);
        model.addAttribute("id", 13);
        return "back/index";
    }

    /**
     * 排序-目录
     * @param op
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("sortDir/{op}/{id}")
    public String sortFront(@PathVariable String op,@PathVariable String id,Model model){
        beijingService.sortDir(CommonUtils.restFulConverter(op),CommonUtils.restFulConverter(id));
        model.addAttribute("id",13);
        return "back/index";
    }
}
