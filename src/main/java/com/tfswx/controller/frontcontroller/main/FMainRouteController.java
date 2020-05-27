package com.tfswx.controller.frontcontroller.main;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class FMainRouteController {

    @RequestMapping("main")
    public String toIndex(Model model){
        model.addAttribute("id",1);
        return "front/main/index";
    }

    @RequestMapping("main/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "front/main/dirFile";
    }

    @RequestMapping("main/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "front/main/versionFile";
    }

    @RequestMapping("main/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "front/main/addFiles";
    }

}
