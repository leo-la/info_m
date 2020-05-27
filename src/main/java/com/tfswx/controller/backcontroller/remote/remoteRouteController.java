package com.tfswx.controller.backcontroller.remote;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class remoteRouteController {

    @RequestMapping("remote/index")
    public String toIndex(Model model){
        model.addAttribute("id",11);
        return "remotePage/index";
    }

    @RequestMapping("remote/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "remotePage/dirFile";
    }

    @RequestMapping("remote/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "remotePage/versionFile";
    }

    @RequestMapping("remote/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "remotePage/addFiles";
    }

}
