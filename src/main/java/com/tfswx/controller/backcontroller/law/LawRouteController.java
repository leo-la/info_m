package com.tfswx.controller.backcontroller.law;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class LawRouteController {

    @RequestMapping("law/index")
    public String toIndex(Model model){
        model.addAttribute("id",8);
        return "lawPage/index";
    }

    @RequestMapping("law/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "lawPage/dirFile";
    }

    @RequestMapping("law/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "lawPage/versionFile";
    }

    @RequestMapping("law/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "lawPage/addFiles";
    }

}
