package com.zx.service;

import com.zx.bean.BookMark;
import com.zx.bean.BookMarkExample;
import com.zx.dao.BookMarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookMarkService {

    @Autowired
    BookMarkMapper bookMarkMapper;

    /**
     * 获取用户ID下的所有书签
     * @param userId
     * @return
     */
    public List<BookMark> getAll(Integer userId) {
        BookMarkExample bookmarkExample = new BookMarkExample();
        BookMarkExample.Criteria criteria = bookmarkExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return bookMarkMapper.selectByExample(bookmarkExample);
    }

    /**
     * 插入书签
     * @param bookmark
     */
    public void save(BookMark bookmark) {
        bookMarkMapper.insertSelective(bookmark);
    }

    /**
     * 根据Id获取书签信息
     * @param id
     * @return
     */
    public BookMark echobookmarks(Integer id) {
        return bookMarkMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新书签
     * @param bookmark
     */
    public void update(BookMark bookmark) {
        bookMarkMapper.updateByPrimaryKeySelective(bookmark);
    }

    /**
     * 删除书签
     * @param id
     */
    public void delete(Integer id) {
        bookMarkMapper.deleteByPrimaryKey(id);
    }

    /**
     * 批量删除书签
     * @param ids
     */
    public void deleteAll(List<Integer> ids) {
        BookMarkExample bookmarkExample = new BookMarkExample();
        BookMarkExample.Criteria criteria = bookmarkExample.createCriteria();
        criteria.andBookmarkIdIn(ids);
        bookMarkMapper.deleteByExample(bookmarkExample);
    }

    /**
     * 搜索书签
     * @param userId
     * @param data
     * @param check
     * @return
     */
    public List<BookMark> select(Integer userId, String data, Integer check) {
        BookMarkExample bookmarkExample = new BookMarkExample();
        BookMarkExample.Criteria criteria = bookmarkExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
//        模糊查询书签标题
        if (check == 1){
            criteria.andBookmarkTitleLike(data);
        }
//        模糊查询书签内容
        else {
            criteria.andBookmarkContentLike(data);
        }
        return bookMarkMapper.selectByExample(bookmarkExample);
    }
}
