package com.zx.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.Cloud;
import com.zx.bean.Msg;
import com.zx.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@PropertySource(value={"classpath:/url.properties"})
@Controller
@RequestMapping("/cloud")
public class CloudController {
    @Autowired
    CloudService cloudService;
    @Value("${url.upload}")
    String UPLOAD_PATH;
    @Value("${url.delete}")
    String DELETE_PATH;
    @Value("${url.getInfo}")
    String INFO_PATH;
    @Value("${url.download}")
    String DOWNLOAD_PATH;

    /**
     * 获取文件列表
     * @param pn
     * @param id
     * @return
     */
    @RequestMapping("/selectCloud")
    @ResponseBody
    public Msg getCloudWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "userId")Integer id){
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        PageHelper.orderBy("cloud_id asc");
//        获取数据
        List<Cloud> clouds = cloudService.getAll(id);
//        连续显示的页数
        PageInfo pageInfo = new PageInfo(clouds,3);
        return Msg.success().add("cloud_pageInfo",pageInfo);
    }

    /**
     * 发送并存储文件
     * @param file
     * @param userId
     * @return
     */
    @RequestMapping(value = "/saveCloud",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(@RequestParam("data")MultipartFile file,@RequestParam("userId")Integer userId){
        String result = null;
        Cloud cloud = new Cloud();;
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(),
                    file.getOriginalFilename());
            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
//            返回json数据
            params.put("output", "json");
//            存储地址
            params.put("path","/"+userId);
//            返回的信息
            result = HttpUtil.post(UPLOAD_PATH, params);
            JSONObject jsonObject1 = new JSONObject(result);
//            插入MD5
            cloud.setCloudMd5(jsonObject1.getStr("md5"));
//            获取文件名
            cloud.setCloudName(file.getOriginalFilename());
//            获取下载地址
            cloud.setCloudUrl(jsonObject1.getStr("path"));
            cloud.setUserId(userId);
            cloudService.save(cloud);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("传输错误");
        }
        return Msg.success().add("result",result);
    }

    /**
     * 获取真实的地址
     * @param Id
     * @return
     */
    @RequestMapping("/download")
    @ResponseBody
    public Msg download(@RequestParam("Id")Integer Id){
        String trueUrl = DOWNLOAD_PATH+cloudService.getUrl(Id);
        return Msg.success().add("url",trueUrl);
    }

    /**
     * 删除文件
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delCloud",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){

        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                Map<String, Object> params = new HashMap<>();
                del_ids.add(Integer.parseInt(string));
                String MD5 = cloudService.getMD5(Integer.parseInt(string));
                params.put("md5",MD5);
                try {
                    HttpUtil.post(DELETE_PATH,params);
                }catch (Exception e){
                    return Msg.fail();
                }
            }
            cloudService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Map<String, Object> params = new HashMap<>();
            Integer id = Integer.parseInt(ids);
            String MD5 = cloudService.getMD5(id);
            params.put("md5",MD5);
            try {
                HttpUtil.post(DELETE_PATH,params);
            }catch (Exception e){
                return Msg.fail();
            }
            cloudService.delete(id);
            return Msg.success();
        }
    }
}
