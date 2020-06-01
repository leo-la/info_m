package com.tfswx.controller.frontcontroller.main;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route/front")
@Controller
public class FFirmRouteController {

    @RequestMapping("firm/index")
    public String firmF(Model model){
        model.addAttribute("id",1);
        return "front/index";
    }

    @RequestMapping("firm/fileDir/{id}")
    public String fileDirF(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",1);
        return "front/dirFile";
    }

    @RequestMapping("firm/fileVersions/{id}")
    public String fileVersionsF(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",1);
        return "front/versionFile";
    }
}
