package com.tfswx.controller;

import com.tfswx.pojo.Directory;
import com.tfswx.pojo.FileInfo;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.RequestResult;
import com.tfswx.service.TService;
import com.tfswx.utils.CommonUtils;
import com.tfswx.utils.InfoFileUtil;
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
public class ScoController extends ResultHandler {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ScoController.class);

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
    @RequestMapping("searchNo_1DirPage")
    @ResponseBody
    public List<Directory> searchNo_1DirPage(@RequestBody Map<String,Integer> map){

        return tService.searchNo_1DirPage(map.get("id")==null?1:map.get("id"));
    }

    /**
     * 查询一级目录分类页面
     * @return
     */
    @RequestMapping("searchNo_2DirPage")
    @ResponseBody
    public PageBean searchNo_2DirPage(@RequestBody PageBean pageBean){
        return tService.searchNo_2DirPage(pageBean);
    }

    /**
     * 查询三级分类-文件版本分类页面（versionid）
     * @param pageBean
     * @return
     */
    @RequestMapping("searchNo_3DirPage")
    @ResponseBody
    public PageBean searchNo_3DirPage(@RequestBody PageBean pageBean){
        return tService.searchNo_3DirPage(pageBean);
    }

    /**
     * 查询四级目录页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("searchNo_4DirPage")
    @ResponseBody
    public PageBean searchNo_4DirPage(@RequestBody PageBean pageBean){
        return tService.searchNo_4DirPage(pageBean);
    }

    /**
     * 查询一级目录
     * @param map
     * @return
     */
    @RequestMapping("getNo_1DirInfo")
    @ResponseBody
    public Directory getNo_1DirInfo(@RequestBody Map<String,Integer> map){
        return tService.searchDirById(map.get("id"));
    }

    /**
     * 添加文件(三级)
     * @param file
     * @return
     */
    @RequestMapping("addNo_3File")
    @ResponseBody
    public RequestResult addNo_3File(MultipartFile file, FileInfo fileInfo){
        String filename = CommonUtils.filenameUnique(file);
        fileInfo.setName(filename);
        String url = InfoFileUtil.uploadFile(file, fileInfo.getName(), staticWordFilePath);
        fileInfo.setUrl(url);
        RequestResult requestResult = tService.addNo_3File(fileInfo);
        return requestResult;
    }

    /**
     * 添加三级目录
     * @param fileInfo
     * @return
     */
    @RequestMapping("addNo_3Dir")
    @ResponseBody
    public RequestResult addNo_3Dir(@RequestBody FileInfo fileInfo){
        return tService.addNo_3Dir(fileInfo);
    }

    /**
     * 重新添加文件
     * @param updatefile
     * @param fileInfo
     * @return
     */
    @RequestMapping("reuploadFile")
    @ResponseBody
    public RequestResult reuploadFile(MultipartFile updatefile,FileInfo fileInfo){
        String filename = CommonUtils.filenameUnique(updatefile);
        fileInfo.setName(filename);
        String url = InfoFileUtil.uploadFile(updatefile, fileInfo.getName(), staticWordFilePath);
        fileInfo.setUrl(url);
        RequestResult requestResult = tService.reuploadFile(fileInfo);
        return requestResult;
    }

    /**
     * 添加二级分类-文件目录分类
     * @param map
     * @return
     */
    @RequestMapping("addNo_2Dir")
    @ResponseBody
    public RequestResult addNo_2Dir(@RequestBody Map<String,String> map){
        return tService.addNo_2Dir(map.get("name"), Integer.parseInt(map.get("dirid")));
    }

    /**
     * 删除文件
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_3FileDir")
    @ResponseBody
    public RequestResult deleteNo_3FileDir(@RequestBody Map<String,Integer> map){
        return tService.deleteNo_3File(map.get("id"));
    }

    /**
     * 删除二级目录-文件目录
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_2Dir")
    @ResponseBody
    public Boolean deleteFileDir(@RequestBody Map<String,Integer> map){
        return tService.deleteFileDir(map.get("id"));
    }

    /**
     * 删除一级目录
     * @param map
     * @return
     */
    @RequestMapping("deleteNo_1Dir")
    @ResponseBody
    public RequestResult deleteNo_1Dir(@RequestBody Map<String,Integer> map){
        RequestResult id = tService.deleteNo_1Dir(map.get("id"));
        return id;
    }

    /**
     * 更新二级目录-文件目录名
     * @param map
     * @return
     */
    @RequestMapping("updateNo_2Dir")
    @ResponseBody
    public RequestResult updateNo_2Dir(@RequestBody Map<String,String> map){
        return tService.updateNo_2Dir(Integer.parseInt(map.get("id")),map.get("name"));
    }

    /**
     * 更新二级目录-文件目录名
     * @param fileInfo
     * @return
     */
    @RequestMapping("updateNo_3Dir")
    @ResponseBody
    public RequestResult updateNo_3Dir(@RequestBody FileInfo fileInfo){
        return tService.updateNo_3Dir(fileInfo);
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
    }

    /**
     * 目录排序
     *
     * @param map
     * @return
     */
    @RequestMapping("sortDir")
    @ResponseBody
    public RequestResult sortDir(@RequestBody Map<String,Integer> map){
        return tService.sortDir(map.get("opid"), map.get("id"));
    }

    /**
     * 添加一级目录
     * @param directory
     * @return
     */
    @RequestMapping("addNO_1Dir")
    @ResponseBody
    public RequestResult addTDir(@RequestBody Directory directory){
        try {
            return tService.addNO_1Dir(directory);
        } catch (Exception e) {
            return failure("","");
        }
    }

    /**
     * 更新一级分类目录名
     * @param directory
     * @return
     */
    @RequestMapping("updateNO_1Dir")
    @ResponseBody
    public RequestResult updateNO_1Dir(@RequestBody Directory directory){
        return tService.updateNO_1Dir(directory);
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
