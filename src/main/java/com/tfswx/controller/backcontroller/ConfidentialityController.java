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
 * 保密管理办公室控制器
 */
@Controller
@RequestMapping("confidentiality")
public class ConfidentialityController extends BaseController {

    @Autowired
    TService confidentialityService;

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    @RequestMapping("addTDir")
    public String addTDir(Directory directory,Model model){
        confidentialityService.addTDir(directory);
        model.addAttribute("id",6);
        return "back/index";
    }

    /**
     * 更新一级分类目录名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("updateTDirName")
    public String updateTDirName(Integer id,String name,String enname,Model model){
        confidentialityService.updateTDirName(id,name,enname);
        model.addAttribute("id",6);
        return "back/index";
    }

    /**
     * 排序-目录前移
     * @param lastId
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("sortFront/{lastId}/{id}")
    public String sortFront(@PathVariable String lastId,@PathVariable String id,Model model){
        confidentialityService.sortFront(CommonUtils.restFulConverter(lastId),CommonUtils.restFulConverter(id));
        model.addAttribute("id",6);
        return "back/index";
    }

    /**
     * 排序-目录后移
     * @param nextid
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("sortDown/{nextid}/{id}")
    public String sortDown(@PathVariable String nextid,@PathVariable String id,Model model){
        confidentialityService.sortDown(CommonUtils.restFulConverter(nextid),CommonUtils.restFulConverter(id));
        model.addAttribute("id",6);
        return "back/index";
    }
}
