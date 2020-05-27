package com.tfswx.controller.backcontroller.military;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.PageBean;
import com.tfswx.service.TService;
import com.tfswx.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 公司运营控制器
 */
@Controller
@RequestMapping("military")
public class MilitaryController {

    @Autowired
    TService militaryService;

    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    /**
     * 查询公司信息目录
     * @return
     */
    @RequestMapping("searchDirectory")
    @ResponseBody
    public List<Directory> searchDirectory(@RequestBody Map<String,Integer> map){
        return militaryService.searchDirectory(map.get("id"));
    }


    /**
     * 查询公司信息管理文件分类详情
     * @return
     */
    @RequestMapping("searchDirectoryDetails")
    @ResponseBody
    public PageBean searchDirectoryDetails(@RequestBody PageBean pageBean){
        return militaryService.searchVersionFilesPage(pageBean);
    }

    /**
     * 查询公司信息管理文件版本分类-versionid
     * @param pageBean
     * @return
     */
    @RequestMapping("searchHistoryVersion")
    @ResponseBody
    public PageBean searchHistoryVersion2(@RequestBody PageBean pageBean){
        return militaryService.searchHistoryVersionPage(pageBean);
    }

    /**
     * 查询目录信息
     * @param map
     * @return
     */
    @RequestMapping("getDir")
    @ResponseBody
    public Directory getDir(@RequestBody Map<String,Integer> map){
        return militaryService.searchDirById(map.get("id"));
    }

    /**
     * 添加文件
     * @param file
     * @param versionid
     * @param description
     * @param download
     * @param model
     * @return
     */
    @RequestMapping("addNewFile")
    @ResponseBody
    public Boolean addNewFile(MultipartFile file, Integer versionid, String description, Boolean download, Model model){
       try{
           CommonUtils.uploadFile(file,versionid,description,download,staticWordFilePath,militaryService);
           model.addAttribute("versionid",versionid);
           return true;
       }catch (Exception e){
            e.printStackTrace();
            return false;
       }
    }

    /**
     * 添加文件分类
     * @param dirid
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("addFileDir")
    public String addFileDir(Integer dirid, String name, Model model){
        militaryService.addFileDir(name, dirid);
        model.addAttribute("dirid",dirid);
        return "militaryPage/dirFile";
    }

    /**
     * 添加一级目录
     * @param name
     * @return
     */
    @RequestMapping("addTDir")
    public String addTDir(String name, String enname,Integer depid,Model model){
        militaryService.addTDir(name, enname,depid);
        model.addAttribute("id",9);
        return "militaryPage/index";
    }

    /**
     * 删除文件
     * @param map
     * @return
     */
    @RequestMapping("deleteFile")
    @ResponseBody
    public Boolean deleteFile(@RequestBody Map<String,Integer> map){
        return militaryService.deleteFile(map.get("id"));
    }

    /**
     * 删除文件目录
     * @param map
     * @return
     */
    @RequestMapping("deleteFileDir")
    @ResponseBody
    public Boolean deleteFileDir(@RequestBody Map<String,Integer> map){
        return militaryService.deleteFileDir(map.get("id"));
    }

    /**
     * 删除一级目录
     * @param map
     * @return
     */
    @RequestMapping("deleteTDir")
    @ResponseBody
    public Boolean deleteTDir(@RequestBody Map<String,Integer> map){
        return militaryService.deleteTDir(map.get("id"));
    }

    /**
     * 更新文件目录名
     * @param id
     * @param name
     * @param dirid
     * @param model
     * @return
     */
    @RequestMapping("updateDirName")
    public String updateDirName(Integer id,String name,Integer dirid,Model model){
        militaryService.updateDirName(id,name);
        model.addAttribute("dirid",dirid);
        return "militaryPage/dirFile";
    }

    /**
     * 更新一级分类目录名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("updateTDirName")
    public String updateTDirName(Integer id,String name,String enname,Model model){
        militaryService.updateTDirName(id,name,enname);
        model.addAttribute("id",9);
        return "militaryPage/index";
    }

    /**
     *  
     * 预览
     *
     * @throws Exception
     */
    @RequestMapping("searchFilePreview/{id}")
    @ResponseBody
    public void searchFilePreview(@PathVariable String id, HttpServletResponse response) throws Exception {
        String fileUrl = militaryService.searchFileUrl(CommonUtils.restFulConverter(id));
        CommonUtils.previewFile(fileUrl,response);
    }

    /**
     * 下载文件
     * @param id
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("downloadFile/{id}")
    @ResponseBody
    public void downloadFile(@PathVariable String id, HttpServletRequest request,HttpServletResponse response){
        String fileUrl = militaryService.searchFileUrl(CommonUtils.restFulConverter(id));
        CommonUtils.downloadFile(fileUrl,request,response);
    }
}
