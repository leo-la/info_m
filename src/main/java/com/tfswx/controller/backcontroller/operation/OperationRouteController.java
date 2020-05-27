package com.tfswx.controller.backcontroller.operation;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class OperationRouteController {

    @RequestMapping("operation/index")
    public String toIndex(Model model){
        model.addAttribute("id",2);
        return "operationPage/index";
    }

    @RequestMapping("operation/fileDir/{id}")
    public String toVersionDir(@PathVariable String id,Model model){
        model.addAttribute("dirid",CommonUtils.restFulConverter(id));
        return "operationPage/dirFile";
    }

    @RequestMapping("operation/fileVersions/{id}")
    public String toVersionFile2(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "operationPage/versionFile";
    }

    @RequestMapping("operation/updateFiles/{id}")
    public String toUpdateFile(@PathVariable String id, Model model){
        model.addAttribute("versionid",CommonUtils.restFulConverter(id));
        return "operationPage/addFiles";
    }

}
