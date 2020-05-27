package com.tfswx.controller.backcontroller.firm;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class FirmRouteController {

    @RequestMapping("firm/index")
    public String toIndex(Model model){
        model.addAttribute("id",1);
        return "firmPage/index";
    }

    @RequestMapping("firm/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "firmPage/dirFile";
    }

    @RequestMapping("firm/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "firmPage/versionFile";
    }

    @RequestMapping("firm/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "firmPage/addFiles";
    }

}
