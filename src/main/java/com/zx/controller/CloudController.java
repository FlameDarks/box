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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/cloud")
public class CloudController {
    @Autowired
    CloudService cloudService;

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
            String UPLOAD_PATH = "http://127.0.0.1:8081/group1/upload";
//            返回的信息
            result = HttpUtil.post(UPLOAD_PATH, params);
            JSONObject jsonObject1 = new JSONObject(result);
//            通过MD5获取文件
            String data1 =HttpUtil.get("http://127.0.0.1:8081/group1/get_file_info?md5="+jsonObject1.getStr("md5"));
            cloud.setCloudMd5(jsonObject1.getStr("md5"));
//            获得文件信息
            JSONObject jsonObject3 = new JSONObject(data1).getJSONObject("data");
//            获取文件名
            String fileName = jsonObject3.getStr("name");
            cloud.setCloudName(fileName);
//            获取下载地址
            cloud.setCloudUrl(jsonObject1.getStr("url"));
            cloud.setUserId(userId);
            cloudService.save(cloud);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("传输错误");
        }
        return Msg.success().add("result",result);
    }


    /**
     * 删除文件
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delCloud",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        String DELETE_PATH = "http://127.0.0.1:8081/group1/delete";
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
