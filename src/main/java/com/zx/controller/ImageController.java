package com.zx.controller;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.druid.sql.visitor.functions.If;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.bean.*;
import com.zx.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PropertySource(value={"classpath:/url.properties"})
@Controller
@RequestMapping("/image")
public class ImageController {
    @Autowired
    ImageService imageService;
    @Value("${url.upload}")
    String UPLOAD_PATH;
    @Value("${url.download}")
    String DOWNLOAD_PATH;
    @Value("${url.delete}")
    String DELETE_PATH;
    @Value("${url.getInfo}")
    String INFO_PATH;

    @RequestMapping("/selectImage")
    @ResponseBody
    public Msg getImageWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn,@RequestParam(value = "main")String main,@RequestParam(value = "userId",required = false)Integer userId){
        List<ImageMark> imageMarks = new ArrayList<>();
        //先获取用户收藏了那些图片
        if (userId!=null){
            imageMarks = imageService.get(userId);
        }
//        每页显示条数
        PageHelper.startPage(pn,3);
//        升序排列
        if (main.equals("yes")){
            PageHelper.orderBy("image_id asc");
        } else {
            PageHelper.orderBy("image_like desc");
        }
        //我的收藏列表
        if (userId!=null){
            List<Image> images = imageService.getAllById(imageMarks);
            PageInfo pageInfo = new PageInfo(images,3);
            return Msg.success().add("image_pageInfo",pageInfo);
        }else {
            //所有图片和热门图片列表
            List<Image> images = imageService.getAll();
            //        连续显示的页数
            PageInfo pageInfo = new PageInfo(images,3);
            return Msg.success().add("image_pageInfo",pageInfo);
        }
    }

    /**
     * 上传图片
     * @param file
     * @return
     */
    @RequestMapping(value = "/saveImage",method = RequestMethod.POST)
    @ResponseBody
    public Msg save(@RequestParam("data") MultipartFile file){
        String result = null;
        Image image = new Image();
        try {
            InputStreamResource isr = new InputStreamResource(file.getInputStream(),
                    file.getOriginalFilename());
            Map<String, Object> params = new HashMap<>();
            params.put("file", isr);
//            返回json数据
            params.put("output", "json");
            params.put("path","defalut");
//            返回的信息
            result = HttpUtil.post(UPLOAD_PATH, params);
            JSONObject jsonObject1 = new JSONObject(result);
//            插入MD5
            image.setImageMd5(jsonObject1.getStr("md5"));
//            获取文件名
            image.setImageName(file.getOriginalFilename());
//            获取下载地址
            image.setImageUrl(DOWNLOAD_PATH+jsonObject1.getStr("path"));
            imageService.save(image);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("传输错误");
        }
        return Msg.success().add("result",result);
    }

    /**
     * 删除图片
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delImage",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delete(@RequestParam(value = "Id")String ids){
        if (ids.contains("-")){
            String[] str_ids = ids.split("-");
            List<Integer> del_ids = new ArrayList<>();
            for (String string : str_ids){
                Map<String, Object> params = new HashMap<>();
                del_ids.add(Integer.parseInt(string));
                String MD5 = imageService.getMD5(Integer.parseInt(string));
                params.put("md5",MD5);
                try {
                    HttpUtil.post(DELETE_PATH,params);
                }catch (Exception e){
                    return Msg.fail();
                }
            }
            imageService.deleteAll(del_ids);
            return Msg.success();
        }else {
            Map<String, Object> params = new HashMap<>();
            Integer id = Integer.parseInt(ids);
            String MD5 = imageService.getMD5(id);
            params.put("md5",MD5);
            try {
                HttpUtil.post(DELETE_PATH,params);
            }catch (Exception e){
                return Msg.fail();
            }
            imageService.delete(id);
            return Msg.success();
        }
    }

    /**
     * 点赞
     * @param like
     * @param user
     * @return
     */
    @RequestMapping(value = "/isLike")
    @ResponseBody
    public Msg isLike(@RequestParam("like")Integer like,@RequestParam("user")Integer user){
        if (!imageService.isLike(like, user)) {
            return Msg.fail().add("msg","已经赞过了");
        }
        imageService.like(like,user);
        return Msg.success().add("msg","点赞成功");
    }

    /**
     * 收藏
     * @param mark
     * @param user
     * @return
     */
    @RequestMapping(value = "/isMark")
    @ResponseBody
    public Msg isMark(@RequestParam("mark")Integer mark,@RequestParam("user")Integer user){
        if (!imageService.isMark(mark, user)) {
            return Msg.fail().add("msg","已经收藏过了");
        }
        imageService.Mark(mark,user);
        return Msg.success().add("msg","收藏成功");
    }

    /**
     * 取消收藏
     * @param mark
     * @param user
     * @return
     */
    @RequestMapping(value = "/delMark",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg delMark(@RequestParam("mark")Integer mark,@RequestParam("user")Integer user){
        imageService.delMark(mark,user);
        return Msg.success();
    }
}
