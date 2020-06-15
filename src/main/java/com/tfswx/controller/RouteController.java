package com.tfswx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class RouteController {

    @RequestMapping("front/index/{id}")
    public String front(@PathVariable String id, Model model){
        int i = Integer.parseInt(id.substring(1, id.length() - 1));

        switch (i){
            case 1:
                model.addAttribute("parentDirId",1);
                model.addAttribute("level",1);
                model.addAttribute("id",1);
                break;
            case 2:
                model.addAttribute("parentDirId",2);
                model.addAttribute("level",1);
                model.addAttribute("id",2);
                break;
            case 3:
                model.addAttribute("parentDirId",3);
                model.addAttribute("level",1);
                model.addAttribute("id",3);
                break;
            case 4:
                model.addAttribute("parentDirId",4);
                model.addAttribute("level",1);
                model.addAttribute("id",4);
                break;
            case 5:
                model.addAttribute("parentDirId",5);
                model.addAttribute("level",1);
                model.addAttribute("id",5);
                break;
            case 6:
                model.addAttribute("parentDirId",6);
                model.addAttribute("level",1);
                model.addAttribute("id",6);
                break;
            case 7:
                model.addAttribute("parentDirId",7);
                model.addAttribute("level",1);
                model.addAttribute("id",7);
                break;
            case 8:
                model.addAttribute("parentDirId",8);
                model.addAttribute("level",1);
                model.addAttribute("id",8);
                break;
            case 9:
                model.addAttribute("parentDirId",9);
                model.addAttribute("level",1);
                model.addAttribute("id",9);
                break;
            case 11:
                model.addAttribute("parentDirId",11);
                model.addAttribute("level",1);
                model.addAttribute("id",11);
                break;
            case 12:
                model.addAttribute("parentDirId",12);
                model.addAttribute("level",1);
                model.addAttribute("id",12);
                break;
            case 13:
                model.addAttribute("parentDirId",13);
                model.addAttribute("level",1);
                model.addAttribute("id",13);
                break;
            default:
                model.addAttribute("parentDirId",1);
                model.addAttribute("level",1);
                model.addAttribute("id",1);
        }


        return "index";
    }


    @RequestMapping("index/{id}")
    public String back(@PathVariable String id, Model model){
        int i = Integer.parseInt(id.substring(1, id.length() - 1));

        switch (i){
            case 1:
                model.addAttribute("parentDirId",1);
                model.addAttribute("level",1);
                model.addAttribute("id",1);
                break;
            case 2:
                model.addAttribute("parentDirId",2);
                model.addAttribute("level",1);
                model.addAttribute("id",2);
                break;
            case 3:
                model.addAttribute("parentDirId",3);
                model.addAttribute("level",1);
                model.addAttribute("id",3);
                break;
            case 4:
                model.addAttribute("parentDirId",4);
                model.addAttribute("level",1);
                model.addAttribute("id",4);
                break;
            case 5:
                model.addAttribute("parentDirId",5);
                model.addAttribute("level",1);
                model.addAttribute("id",5);
                break;
            case 6:
                model.addAttribute("parentDirId",6);
                model.addAttribute("level",1);
                model.addAttribute("id",6);
                break;
            case 7:
                model.addAttribute("parentDirId",7);
                model.addAttribute("level",1);
                model.addAttribute("id",7);
                break;
            case 8:
                model.addAttribute("parentDirId",8);
                model.addAttribute("level",1);
                model.addAttribute("id",8);
                break;
            case 9:
                model.addAttribute("parentDirId",9);
                model.addAttribute("level",1);
                model.addAttribute("id",9);
                break;
            case 11:
                model.addAttribute("parentDirId",11);
                model.addAttribute("level",1);
                model.addAttribute("id",11);
                break;
            case 12:
                model.addAttribute("parentDirId",12);
                model.addAttribute("level",1);
                model.addAttribute("id",12);
                break;
            case 13:
                model.addAttribute("parentDirId",13);
                model.addAttribute("level",1);
                model.addAttribute("id",13);
                break;
            default:
                model.addAttribute("parentDirId",1);
                model.addAttribute("level",1);
                model.addAttribute("id",1);
        }


        return "back/index";
    }
}
