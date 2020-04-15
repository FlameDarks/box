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
        System.out.println("没有");
        cloudMapper.insertSelective(cloud);
        System.out.println("有");
    }

    public List<Cloud> getAll(Integer id) {
        CloudExample cloudExample = new CloudExample();
        CloudExample.Criteria criteria = cloudExample.createCriteria();
        criteria.andUserIdEqualTo(id);
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
}
