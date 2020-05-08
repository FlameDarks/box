package com.zx.service;

import com.zx.bean.Cloud;
import com.zx.bean.CloudExample;
import com.zx.dao.CloudMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CloudService {
    
    @Autowired
    CloudMapper cloudMapper;

    /**
     * 插入文件信息
     * @param cloud
     */
    public void save(Cloud cloud) {
        cloudMapper.insertSelective(cloud);
    }

    /**
     * 获取Id下所有文件信息
     * @param userId
     * @return
     */
    public List<Cloud> getAll(Integer userId) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return cloudMapper.selectByExample(cloudExample);
    }

    /**
     * 删除文件
     * @param id
     */
    public void delete(Integer id) {
        cloudMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除文件
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andCloudIdIn(ids);
        cloudMapper.deleteByExample(cloudExample);
    }

    /**
     * 搜索文件信息
     * @param userId
     * @param data
     * @return
     */
    public List<Cloud> select(Integer userId, String data) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andCloudNameLike(data);
        return cloudMapper.selectByExample(cloudExample);
    }

    /**
     * 通过文件Id获取MD5码
     * @param Id
     * @return
     */
    public String getMD5(Integer Id){
        return cloudMapper.selectByPrimaryKey(Id).getCloudMd5();
    }
}
