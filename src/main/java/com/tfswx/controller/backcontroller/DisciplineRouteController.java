package com.tfswx.controller.backcontroller;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class DisciplineRouteController {

    @RequestMapping("discipline/index")
    public String toIndex(Model model){
        model.addAttribute("id",12);
        return "back/index";
    }

    @RequestMapping("discipline/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",12);
        return "back/dirFile";
    }

    @RequestMapping("discipline/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",12);
        return "back/versionFile";
    }

}