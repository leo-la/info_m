package com.tfswx.controller.backcontroller.confidentiality;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class ConfidentialityRouteController {

    @RequestMapping("confidentiality/index")
    public String toIndex(Model model){
        model.addAttribute("id",6);
        return "confidentialityPage/index";
    }

    @RequestMapping("confidentiality/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "confidentialityPage/dirFile";
    }

    @RequestMapping("confidentiality/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "confidentialityPage/versionFile";
    }

    @RequestMapping("confidentiality/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "confidentialityPage/addFiles";
    }

}
