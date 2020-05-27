package com.tfswx.controller.backcontroller.quality;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class QualityRouteController {

    @RequestMapping("quality/index")
    public String toIndex(Model model){
        model.addAttribute("id",5);
        return "qualityPage/index";
    }

    @RequestMapping("quality/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "qualityPage/dirFile";
    }

    @RequestMapping("quality/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "qualityPage/versionFile";
    }

    @RequestMapping("quality/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "qualityPage/addFiles";
    }

}
