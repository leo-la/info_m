package com.tfswx.controller;

import com.tfswx.exception.ResultBody;
import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.service.TService;
import com.tfswx.utils.CommonUtils;
import com.tfswx.utils.InfoFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
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
public class ScoController {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ScoController.class);

    @Autowired
    TService tService;

    /**
     * 文件上传存储目录
     */
    @Value("${web.upload-word-path}")
    private String staticWordFilePath;

    /**
     * 查询一级目录页面
     * @return
     */
    @RequestMapping("searchNo_1DirPage")
    @ResponseBody
    public ResultBody searchNo_1DirPage(@RequestBody Map<String,Integer> map){
        return ResultBody.success(tService.searchNo_1DirPage(map.get("id")==null?1:map.get("id")));
    }

    /**
     * 查询二级目录页面
     * @return
     */
    @RequestMapping("searchNo_2DirPage")
    @ResponseBody
    public ResultBody searchNo_2DirPage(@RequestBody PageBean pageBean){
        return ResultBody.success(tService.searchNo_2DirPage(pageBean));
    }

    /**
     * 查询三级目录页面
     * @param pageBean
     * @return
     */
    @RequestMapping("searchNo_3DirPage")
    @ResponseBody
    public ResultBody searchNo_3DirPage(@RequestBody PageBean pageBean){
        return ResultBody.success(tService.searchNo_3DirPage(pageBean));
    }

    /**
     * 查询四级目录页面
     * @param pageBean
     * @return
     */
    @RequestMapping("searchNo_4DirPage")
    @ResponseBody
    public ResultBody searchNo_4DirPage(@RequestBody PageBean pageBean){
        return ResultBody.success(tService.searchNo_4DirPage(pageBean));
    }

    /**
     * 查询一级目录信息
     * @param map
     * @return
     */
    @RequestMapping("getNo_1DirInfo")
    @ResponseBody
    public ResultBody getNo_1DirInfo(@RequestBody Map<String,Integer> map){
        return ResultBody.success(tService.searchDirById(map.get("id")));
    }

    /**
     * 添加三级目录文件
     * @param file
     * @return
     */
    @RequestMapping("addNo_3File")
    @ResponseBody
    public Boolean addNo_3File(MultipartFile file, FileInfo fileInfo){
        String filename = CommonUtils.filenameUnique(file);
        fileInfo.setName(filename);
        String url = InfoFileUtil.uploadFile(file, fileInfo.getName(), staticWordFilePath);
        fileInfo.setUrl(url);
        tService.addNo_3File(fileInfo);
        LOG.info("添加三级目录文件：{}",file.getOriginalFilename());
        return true;
    }

    /**
     * 添加三级目录
     * @param fileInfo
     * @return
     */
    @RequestMapping("addNo_3Dir")
    @ResponseBody
    public Boolean addNo_3Dir(@RequestBody FileInfo fileInfo){
        tService.addNo_3Dir(fileInfo);
        LOG.info("添加三级目录：{}",fileInfo.getName());
        return true;
    }

    /**
     * 更新文件
     * @param updatefile
     * @param fileInfo
     * @return
     */
    @RequestMapping("reuploadFile")
    @ResponseBody
    public Boolean reuploadFile(MultipartFile updatefile,FileInfo fileInfo){
        String filename = CommonUtils.filenameUnique(updatefile);
        fileInfo.setName(filename);
        String url = InfoFileUtil.uploadFile(updatefile, fileInfo.getName(), staticWordFilePath);
        fileInfo.setUrl(url);
        tService.reuploadFile(fileInfo);
        LOG.info("更新文件：{}",fileInfo.getName());
        return true;
    }

    /**
     * 创建二级目录
     * @param map
     * @return
     */
    @RequestMapping("addNo_2Dir")
    @ResponseBody
    public Boolean addNo_2Dir(@RequestBody Map<String,String> map){
        tService.addNo_2Dir(map.get("name"), Integer.parseInt(map.get("dirid")));
        LOG.info("创建二级目录：{}",map.get("name"));
        return true;
    }

    /**
     * 删除三级目录文件
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_3FileDir")
    @ResponseBody
    public Boolean deleteNo_3FileDir(@RequestBody Map<String,Integer> map){
        tService.deleteNo_3File(map.get("id"));
        LOG.info("删除三级目录文件：{}",map.get("name"));
        return true;
    }

    /**
     * 删除二级目录
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_2Dir")
    @ResponseBody
    public Boolean deleteFileDir(@RequestBody Map<String,Integer> map){
        tService.deleteFileDir(map.get("id"));
        LOG.info("删除二级目录id：{}",map.get("id"));
        return true;
    }

    /**
     * 删除一级目录
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_1Dir")
    @ResponseBody
    public Boolean deleteNo_1Dir(@RequestBody Map<String,Integer> map){
        tService.deleteNo_1Dir(map.get("id"));
        LOG.info("删除一级目录id：{}",map.get("id"));
        return true;
    }

    /**
     * 更新二级目录
     * @param map
     * @return
     */
    @RequestMapping("updateNo_2Dir")
    @ResponseBody
    public Boolean updateNo_2Dir(@RequestBody Map<String,String> map){
        tService.updateNo_2Dir(Integer.parseInt(map.get("id")),map.get("name"));
        LOG.info("更新二级目录：{}",map.get("name"));
        return true;
    }

    /**
     * 更新三级目录
     * @param fileInfo
     * @return
     */
    @RequestMapping("updateNo_3Dir")
    @ResponseBody
    public Boolean updateNo_3Dir(@RequestBody FileInfo fileInfo){
        tService.updateNo_3Dir(fileInfo);
        LOG.info("更新三级目录：{}",fileInfo.getName());
        return true;
    }

    /**
     * 预览
     * @param id
     * @param response
     * @throws Exception
     */
    @RequestMapping("searchFilePreview/{id}/pdf")
    public void searchFilePreview(@PathVariable String id, HttpServletResponse response) throws Exception {
        String fileUrl = tService.searchFileUrl(CommonUtils.restFulConverter(id));
        InfoFileUtil.previewFile(fileUrl,response,staticWordFilePath);
        LOG.info("预览文件id:{}",CommonUtils.restFulConverter(id));
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
        InfoFileUtil.downloadFile(fileUrl,request,response);
        LOG.info("下载文件id:{}",CommonUtils.restFulConverter(id));
    }

    /**
     * 目录排序
     *
     * @param map
     * @return
     */
    @RequestMapping("sortDir")
    @ResponseBody
    public Boolean sortDir(@RequestBody Map<String,Integer> map){
        tService.sortDir(map.get("opid"), map.get("id"));
        LOG.info("目录顺序交换:{}-{}",map.get("opid"), map.get("id"));
        return true;
    }

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    @RequestMapping("addNO_1Dir")
    @ResponseBody
    public Boolean addTDir(@RequestBody Directory directory){
        tService.addNO_1Dir(directory);
        LOG.info("创建一级目录:{}",directory.getDirname());
        return true;

    }

    /**
     * 更新一级目录
     * @param directory
     * @return
     */
    @RequestMapping("updateNO_1Dir")
    @ResponseBody
    public Boolean updateNO_1Dir(@RequestBody Directory directory){
        tService.updateNO_1Dir(directory);
        LOG.info("更新一级目录:{}",directory.getDirname());
        return true;
    }

}
