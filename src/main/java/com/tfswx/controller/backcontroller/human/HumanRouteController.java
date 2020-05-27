package com.tfswx.controller.backcontroller.human;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class HumanRouteController {

    @RequestMapping("human/index")
    public String toIndex(Model model){
        model.addAttribute("id",3);
        return "humanPage/index";
    }

    @RequestMapping("human/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "humanPage/dirFile";
    }

    @RequestMapping("human/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "humanPage/versionFile";
    }

    @RequestMapping("human/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "humanPage/addFiles";
    }

}
