package com.zx.service;

import com.zx.bean.*;
import com.zx.dao.ImageLikeMapper;
import com.zx.dao.ImageMapper;
import com.zx.dao.ImageMarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    ImageLikeMapper imageLikeMapper;
    @Autowired
    ImageMarkMapper imageMarkMapper;

    public void save(Image image) {
        imageMapper.insertSelective(image);
    }

    public List<Image> getAll() {
        return imageMapper.selectByExample(null);
    }

    /**
     * 删除图片
     * @param id
     */
    public void delete(Integer id) {
        imageMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除图片
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        ImageExample imageExample = new ImageExample();
        ImageExample.Criteria criteria = imageExample.createCriteria();
        criteria.andImageIdIn(ids);
        imageMapper.deleteByExample(imageExample);
    }

    /**
     * 搜索图片信息
     * @param data
     * @return
     */
    public List<Image> select(String data) {
        ImageExample imageExample = new ImageExample();
        ImageExample.Criteria criteria = imageExample.createCriteria();
        criteria.andImageNameLike(data);
        return imageMapper.selectByExample(imageExample);
    }

    /**
     * 通过图片d获取MD5码
     * @param Id
     * @return
     */
    public String getMD5(Integer Id){
        return imageMapper.selectByPrimaryKey(Id).getImageMd5();
    }

    /**
     * 判断是否点赞
     * @param like
     * @param user
     * @return
     */
    public boolean isLike(Integer like,Integer user){
        ImageLikeExample imagelikeExample = new ImageLikeExample();
        ImageLikeExample.Criteria criteria = imagelikeExample.createCriteria();
        criteria.andLikeImageIdEqualTo(like);
        criteria.andLikeUserIdEqualTo(user);
        long like1 = imageLikeMapper.countByExample(imagelikeExample);
        return like1 == 0;
    }

    /**
     * 点赞数更新
     * @param like
     * @param user
     */
    public void like(Integer like,Integer user){
        ImageLike likes = new ImageLike();
        likes.setLikeImageId(like);
        likes.setLikeUserId(user);
        imageLikeMapper.insertSelective(likes);
        ImageLikeExample imagelikeExample = new ImageLikeExample();
        ImageLikeExample.Criteria criteria = imagelikeExample.createCriteria();
        criteria.andLikeImageIdEqualTo(like);
        long a = imageLikeMapper.countByExample(imagelikeExample);
        Image image = imageMapper.selectByPrimaryKey(like);
        image.setImageLike((int)a);
        imageMapper.updateByPrimaryKeySelective(image);
    }

    /**
     * 根据匹配的图片ID添加进新列表
     * @return
     */
    public List<Image> getAllById(List<ImageMark> imageMarks) {
        List<Image> images = new ArrayList<>();
        for (ImageMark imageMark : imageMarks) {
            int j = imageMark.getImagemarkImageId();
            images.add(imageMapper.selectByPrimaryKey(j));
        }
        return images;
    }

    /**
     * 判断是否收藏
     * @param mark
     * @param user
     * @return
     */
    public boolean isMark(Integer mark, Integer user) {
        ImageMarkExample imageExample = new ImageMarkExample();
        ImageMarkExample.Criteria criteria = imageExample.createCriteria();
        criteria.andImagemarkUserIdEqualTo(user);
        criteria.andImagemarkImageIdEqualTo(mark);
        long like1 = imageMarkMapper.countByExample(imageExample);
        return like1 == 0;
    }

    /**
     * 收藏图片
     * @param mark
     * @param user
     */
    public void Mark(Integer mark, Integer user) {
        ImageMark likes = new ImageMark();
        likes.setImagemarkImageId(mark);
        likes.setImagemarkUserId(user);
        imageMarkMapper.insertSelective(likes);
    }

    /**
     * 获取图片收藏表中用户ID符合的内容
     * @param userId
     * @return
     */
    public List<ImageMark> get(Integer userId){
        ImageMarkExample imageMarkExample = new ImageMarkExample();
        ImageMarkExample.Criteria criteria = imageMarkExample.createCriteria();
        criteria.andImagemarkUserIdEqualTo(userId);
        return imageMarkMapper.selectByExample(imageMarkExample);
    }

    /**
     * 取消收藏
     * @param mark
     * @param user
     */
    public void delMark(Integer mark, Integer user) {
        ImageMarkExample imageMarkExample = new ImageMarkExample();
        ImageMarkExample.Criteria criteria = imageMarkExample.createCriteria();
        criteria.andImagemarkUserIdEqualTo(user);
        criteria.andImagemarkImageIdEqualTo(mark);
        imageMarkMapper.deleteByExample(imageMarkExample);
    }

    /**
     * 我的收藏搜索
     * @param imageMarks
     * @param imagelists
     * @return
     */
    public List<Image> selectById(List<ImageMark> imageMarks,List<Image> imagelists) {
        List<Image> images = new ArrayList<>();
        List<Image> imagelist = new ArrayList<>();
        for (ImageMark imageMark : imageMarks) {
            int j = imageMark.getImagemarkImageId();
            images.add(imageMapper.selectByPrimaryKey(j));
        }
        int i,j;
        System.out.println(images.size()+"\t"+imagelists.size());
        for (i = 0;i < images.size();i++){
            System.out.println(i);
            for (j = 0;j< imagelists.size();j++){
                System.out.println(j);
                if (imagelists.get(j).getImageId().equals(images.get(i).getImageId())){
                    imagelist.add(images.get(j));
                    System.out.println(imagelist.get(0).toString());
                    break;
                }
            }
        }
        return imagelist;
    }
}
