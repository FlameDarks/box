package com.zx.service;

import com.zx.bean.NoteBook;
import com.zx.dao.NoteBookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteBookService {

    @Autowired
    NoteBookMapper noteBookMapper;
    public List<NoteBook> getAll() {
        return noteBookMapper.selectByExample(null);
    }
}
