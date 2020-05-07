package com.zx.service;

import com.zx.bean.NoteBook;
import com.zx.bean.NoteBookExample;
import com.zx.dao.NoteBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteBookService {

    @Autowired
    NoteBookMapper noteBookMapper;

    /**
     * 获取用户ID下所有记事本信息
     * @param userId
     * @return
     */
    public List<NoteBook> getAll(Integer userId) {
        NoteBookExample noteBookExample = new NoteBookExample();
        NoteBookExample.Criteria criteria = noteBookExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return noteBookMapper.selectByExample(noteBookExample);
    }

    /**
     * 插入记事本
     * @param noteBook
     */
    public void save(NoteBook noteBook) {
        noteBookMapper.insertSelective(noteBook);
    }

    /**
     * 通过Id获取记事本信息
     * @param id
     * @return
     */
    public NoteBook echoNote(Integer id) {
        return noteBookMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改记事本
     * @param noteBook
     */
    public void update(NoteBook noteBook) {
        noteBookMapper.updateByPrimaryKeySelective(noteBook);
    }

    /**
     * 删除记事本
     * @param id
     */
    public void delete(Integer id) {
        noteBookMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除记事本
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        NoteBookExample noteBookExample = new NoteBookExample();
        NoteBookExample.Criteria criteria = noteBookExample.createCriteria();
        criteria.andNotebookIdIn(ids);
        noteBookMapper.deleteByExample(noteBookExample);
    }

    /**
     * 查询记事本
     * @param userId
     * @param data
     * @param check
     * @return
     */
    public List<NoteBook> select(Integer userId, String data,Integer check){
        NoteBookExample noteBookExample = new NoteBookExample();
        NoteBookExample.Criteria criteria = noteBookExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (check==1){
            criteria.andNotebookTitleLike(data);
        }else{
            criteria.andNotebookContentLike(data);
        }
        return noteBookMapper.selectByExample(noteBookExample);
    }
}
