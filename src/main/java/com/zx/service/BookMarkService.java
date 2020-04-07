package com.zx.service;

import com.zx.bean.BookMark;
import com.zx.bean.BookMarkExample;
import com.zx.bean.Contact;
import com.zx.bean.ContactExample;
import com.zx.dao.BookMarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMarkService {

    @Autowired
    BookMarkMapper bookMarkMapper;
    public List<BookMark> getAll(Integer userid) {
        BookMarkExample bookmarkExample = new BookMarkExample();
        BookMarkExample.Criteria criteria = bookmarkExample.createCriteria();
        criteria.andUserIdEqualTo(userid);
        return bookMarkMapper.selectByExample(bookmarkExample);
    }

    public void save(BookMark bookmark) {
        bookMarkMapper.insertSelective(bookmark);
    }

    public BookMark echobookmarks(Integer id) {
        return bookMarkMapper.selectByPrimaryKey(id);
    }

    public void update(BookMark bookmark) {
        bookMarkMapper.updateByPrimaryKeySelective(bookmark);
    }

    public void delete(Integer id) {
        bookMarkMapper.deleteByPrimaryKey(id);
    }

    public void deleteAll(List<Integer> ids) {
        BookMarkExample bookmarkExample = new BookMarkExample();
        BookMarkExample.Criteria criteria = bookmarkExample.createCriteria();
        criteria.andBookmarkIdIn(ids);
        bookMarkMapper.deleteByExample(bookmarkExample);
    }
}
