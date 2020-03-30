package com.zx.service;

import com.zx.bean.BookMark;
import com.zx.dao.BookMarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMarkService {
    @Autowired
    BookMarkMapper bookMarkMapper;
    public List<BookMark> getAll() {
        return bookMarkMapper.selectByExample(null);
    }
}
