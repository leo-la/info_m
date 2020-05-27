package com.tfswx.controller.backcontroller.beijing;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class BeijingRouteController {

    @RequestMapping("beijing/index")
    public String toIndex(Model model){
        model.addAttribute("id",13);
        return "beijingPage/index";
    }

    @RequestMapping("beijing/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "beijingPage/dirFile";
    }

    @RequestMapping("beijing/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "beijingPage/versionFile";
    }

    @RequestMapping("beijing/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "beijingPage/addFiles";
    }

}
