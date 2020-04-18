package com.zx.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.lang.Console;
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
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Controller
public class CloudController {
    @Autowired
    CloudService cloudService;


    @RequestMapping("/cloud")
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
        System.out.println("连续显示的页数："+pageInfo.getPages()+"时间戳："+System.currentTimeMillis());
        return Msg.success().add("cloud_pageInfo",pageInfo);
    }


    @RequestMapping(value = "/saveCloud",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(@RequestParam("data")MultipartFile file,@RequestParam("userId")Integer userId){
        System.out.println("文件大小"+file.getSize());
//        if (file.getSize()>10240){
//            return Msg.fail().add("result","文件过大");
//        }
        String result = "";
        JSONObject jsonObject1;
        JSONObject jsonObject2;
        JSONObject jsonObject3;
        Cloud cloud = new Cloud();;
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(),
                    file.getOriginalFilename());
            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
            params.put("output", "json");
            params.put("path","/"+userId);
//            params.put("rename","123");
            String UPLOAD_PATH = "http://39.106.190.142:8081/group1/upload";
            String resp = HttpUtil.post(UPLOAD_PATH, params);
            Console.log("resp: {}", resp);
            result = resp;
            jsonObject1 = new JSONObject(result);
            String data1 =HttpUtil.get("http://39.106.190.142:8081/group1/get_file_info?md5="+jsonObject1.getStr("md5"));
            System.out.println(data1);
            jsonObject2 = new JSONObject(data1);
            jsonObject3 = jsonObject2.getJSONObject("data");
            System.out.println(jsonObject3);
            String fileName = jsonObject3.getStr("name");
            cloud.setCloudName(fileName);
            System.out.println(cloud.getCloudName());
            cloud.setCloudUrl(jsonObject1.getStr("url"));
            System.out.println(cloud.getCloudUrl());
            cloud.setUserId(userId);
            cloudService.save(cloud);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("传输错误");
        }
        System.out.println("返回值："+result);

        return Msg.success().add("result",result);
    }



    @RequestMapping(value = "/delCloud",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            cloudService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Integer id = Integer.parseInt(ids);
            System.out.println("删除的id："+id);
            cloudService.delete(id);
            return Msg.success();
        }
    }

//    @ExceptionHandler
//    public Msg doException(Exception e, HttpServletRequest request) throws Exception {
//        Map<String,Object> map = new HashMap<String,Object>();
//        if (e instanceof MaxUploadSizeExceededException) {
//            long maxSize = ((MaxUploadSizeExceededException) e)
//                    .getMaxUploadSize();
//            return Msg.fail().add("error","上传文件太大");
////            map.put("error", "上传文件太大，不能超过" + maxSize / 1024 + "k");
//        }else if(e instanceof RuntimeException){
////            map.put("error", "未选中文件");
//            return Msg.fail().add("error","未选中文件");
//        }else{
////            map.put("error", "上传失败");
//            return Msg.fail().add("error","上传失败");
//        }
//    }
}
