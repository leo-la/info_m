package com.tfswx.controller;

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

@Controller
public class BaseController {
    @Autowired
    TService tService;

    /**
     * 文件上传存储目录
     */
    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    /**
     * 查询一级目录分类
     * @return
     */
    @RequestMapping("searchDirectory")
    @ResponseBody
    public List<Directory> searchDirectory(@RequestBody Map<String,Integer> map){
        return tService.searchDirectory(map.get("id"));
    }

    /**
     * 查询一级目录分类页面
     * @return
     */
    @RequestMapping("searchDirectoryDetails")
    @ResponseBody
    public PageBean searchDirectoryDetails(@RequestBody PageBean pageBean){
        return tService.searchVersionFilesPage(pageBean);
    }

    /**
     * 查询三级分类-文件版本分类页面（versionid）
     * @param pageBean
     * @return
     */
    @RequestMapping("searchHistoryVersion")
    @ResponseBody
    public PageBean searchHistoryVersion(@RequestBody PageBean pageBean){
        return tService.searchHistoryVersionPage(pageBean);
    }

    /**
     * 查询一级目录
     * @param map
     * @return
     */
    @RequestMapping("getDir")
    @ResponseBody
    public Directory getDir(@RequestBody Map<String,Integer> map){
        return tService.searchDirById(map.get("id"));
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
            CommonUtils.uploadFile(file,versionid,description,download,staticWordFilePath,tService);
            model.addAttribute("versionid",versionid);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 添加二级分类-文件目录分类
     * @param dirid
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("addFileDir")
    public String addFileDir(Integer dirid, String name, Model model){
        tService.addFileDir(name, dirid);
        model.addAttribute("dirid",dirid);
        return "back/dirFile";
    }

    /**
     * 删除文件
     * @param map
     * @return
     */
    @RequestMapping("deleteFile")
    @ResponseBody
    public Boolean deleteFile(@RequestBody Map<String,Integer> map){
        return tService.deleteFile(map.get("id"));
    }

    /**
     * 删除二级目录-文件目录
     * @param map
     * @return
     */
    @RequestMapping("deleteFileDir")
    @ResponseBody
    public Boolean deleteFileDir(@RequestBody Map<String,Integer> map){
        return tService.deleteFileDir(map.get("id"));
    }

    /**
     * 删除一级目录
     * @param map
     * @return
     */
    @RequestMapping("deleteTDir")
    @ResponseBody
    public Boolean deleteTDir(@RequestBody Map<String,Integer> map){
        return tService.deleteTDir(map.get("id"));
    }

    /**
     * 更新二级目录-文件目录名
     * @param id
     * @param name
     * @param dirid
     * @param model
     * @return
     */
    @RequestMapping("updateDirName")
    public String updateDirName(Integer id,String name,Integer dirid,Model model){
        tService.updateDirName(id,name);
        model.addAttribute("dirid",dirid);
        return "back/dirFile";
    }


    /**
     * 预览
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("searchFilePreview/{id}/pdf预览")
    @ResponseBody
    public void searchFilePreview(@PathVariable String id, HttpServletResponse response) throws Exception {
        String fileUrl = tService.searchFileUrl(CommonUtils.restFulConverter(id));
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
    public void downloadFile(@PathVariable String id, HttpServletRequest request, HttpServletResponse response){
        String fileUrl = tService.searchFileUrl(CommonUtils.restFulConverter(id));
        CommonUtils.downloadFile(fileUrl,request,response);
    }
}
