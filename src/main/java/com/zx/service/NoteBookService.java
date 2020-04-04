package com.zx.service;

import com.zx.bean.Msg;
import com.zx.bean.NoteBook;
import com.zx.bean.NoteBookExample;
import com.zx.bean.UserExample;
import com.zx.dao.NoteBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class NoteBookService {

//    查询全部数据
    @Autowired
    NoteBookMapper noteBookMapper;

    public List<NoteBook> getAll(int userid) {
        NoteBookExample noteBookExample = new NoteBookExample();
        NoteBookExample.Criteria criteria = noteBookExample.createCriteria();
        criteria.andUserIdEqualTo(userid);
        return noteBookMapper.selectByExample(noteBookExample);
    }

//    保存数据
    public void save(NoteBook noteBook) {
        noteBookMapper.insertSelective(noteBook);
    }
}
