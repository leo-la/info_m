package com.tfswx.controller;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.service.TService;
import com.tfswx.utils.CommonUtils;
import com.tfswx.utils.FileTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
     * 查询四级目录页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("searchThreeDirPage")
    @ResponseBody
    public PageBean searchThreeDirPage(@RequestBody PageBean pageBean){
        return tService.searchFourLevelDirPage(pageBean);
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
     * 添加文件(三级)
     * @param file
     * @param model
     * @return
     */
    @RequestMapping("addNewFile")
    @ResponseBody
    public Boolean addNewFile(MultipartFile file, FileInfo fileInfo, Model model){
        try{
            fileInfo = CommonUtils.uploadFile(file, fileInfo, staticWordFilePath, false);
            tService.addNewFile(fileInfo);
            model.addAttribute("versionid",fileInfo.getVersionid());
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加四级目录
     * @param fileInfo
     * @param model
     * @return
     */
    @RequestMapping("addFourDir")
    public String addNewFile(FileInfo fileInfo, Model model){
        tService.addFourDir(fileInfo);
        model.addAttribute("versionid",fileInfo.getVersionid());
        model.addAttribute("id",fileInfo.getDepid());
        model.addAttribute("fileLevel",fileInfo.getLevel());
        return "back/versionFile";
    }

    /**
     * 重新添加文件
     * @param updatefile
     * @param fileInfo
     * @param model
     * @return
     */
    @RequestMapping("reuploadFile")
    @ResponseBody
    public Boolean reuploadFile(MultipartFile updatefile,FileInfo fileInfo, Model model){
        try{
            CommonUtils.uploadFile(updatefile,fileInfo,staticWordFilePath,true);
            tService.reuploadFile(fileInfo);
            model.addAttribute("versionid",fileInfo.getVersionid());
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
    public String addFileDir(Integer dirid, Integer depid,String name, Model model){
        tService.addFileDir(name, dirid);
        model.addAttribute("dirid",dirid);
        model.addAttribute("id",depid);
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
    public String updateDirName(Integer id,Integer depid,String name,Integer dirid,Model model){
        tService.updateDirName(id,name);
        model.addAttribute("dirid",dirid);
        model.addAttribute("id",depid);
        return "back/dirFile";
    }

    /**
     * 更新二级目录-文件目录名
     * @param fileInfo
     * @param model
     * @return
     */
    @RequestMapping("update3Dir")
    public String update3Dir(FileInfo fileInfo,Model model){
        tService.update3Dir(fileInfo);
        model.addAttribute("versionid",fileInfo.getVersionid());
        model.addAttribute("depid",fileInfo.getDepid());
        return "back/versionFile";
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
        CommonUtils.previewFile(fileUrl,response,staticWordFilePath);
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



//    /**
//     * 认证流程-认证资质文件-图片文件显示
//     * @param image_name
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/image/{image_name}", produces = MediaType.IMAGE_PNG_VALUE)
//    public ResponseEntity<byte[]> getImage(@PathVariable("image_name") String image_name) throws Exception{ byte[] imageContent ;
//        String path = staticWordFilePath+image_name+".png";
//        imageContent = FileTools.fileToByte(new File(path));
//
//        final HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<>(imageContent, headers, HttpStatus.OK);
//    }
}
