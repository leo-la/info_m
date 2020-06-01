package com.tfswx.controller.frontcontroller.main;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route/front")
@Controller
public class FOperationRouteController {

    @RequestMapping("operation/index")
    public String toIndex(Model model){
        model.addAttribute("id",2);
        return "front/index";
    }

    @RequestMapping("operation/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",2);
        return "front/dirFile";
    }

    @RequestMapping("operation/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
       model.addAttribute("id",2);
        return "front/versionFile";
    }
}
