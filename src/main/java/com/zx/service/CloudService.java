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
    
    public void save(Cloud cloud) {
        cloudMapper.insertSelective(cloud);
    }

    public List<Cloud> getAll(Integer userId) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return cloudMapper.selectByExample(cloudExample);
    }


    public void delete(Integer id) {
        cloudMapper.deleteByPrimaryKey(id);
    }

    public void deleteAll(List<Integer> ids) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andCloudIdIn(ids);
        cloudMapper.deleteByExample(cloudExample);
    }

    public List<Cloud> select(Integer userId, String data) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        criteria.andCloudNameLike(data);
        return cloudMapper.selectByExample(cloudExample);
    }
}
