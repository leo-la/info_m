package com.tfswx.controller.backcontroller.office;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class OfficeRouteController {

    @RequestMapping("office/index")
    public String toIndex(Model model){
        model.addAttribute("id",7);
        return "officePage/index";
    }

    @RequestMapping("office/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "officePage/dirFile";
    }

    @RequestMapping("office/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "officePage/versionFile";
    }

    @RequestMapping("office/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "officePage/addFiles";
    }

}
