package com.tfswx.controller.frontcontroller.main;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route/front")
@Controller
public class FMilitaryRouteController {

    @RequestMapping("military/index")
    public String toIndex(Model model){
        model.addAttribute("id",9);
        return "front/military/index";
    }

    @RequestMapping("military/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "front/military/dirFile";
    }

    @RequestMapping("military/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "front/military/versionFile";
    }

    @RequestMapping("military/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "front/military/addFiles";
    }

}
