package com.tfswx.controller.frontcontroller.main;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route/front")
@Controller
public class FBeijingRouteController {

    @RequestMapping("beijing/index")
    public String toIndex(Model model){
        model.addAttribute("id",13);
        return "front/index";
    }

    @RequestMapping("beijing/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",13);
        return "front/dirFile";
    }

    @RequestMapping("beijing/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",13);
        model.addAttribute("fileLevel",1);
        return "front/versionFile";
    }

    @RequestMapping("beijing/threeDirFiles/{id}")
    public String threeDirFiles(@PathVariable String id, Model model){
        model.addAttribute("threeDirid",CommonUtils.restFulConverter(id));
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        model.addAttribute("id",13);
        model.addAttribute("dirLevel",3);
        model.addAttribute("fileLevel",2);
        return "front/versionFile";
    }

}
