package com.hust.mymall.service;

import com.hust.mymall.vo.CategoryVo;
import com.hust.mymall.vo.ResponseVo;

import java.util.List;
import java.util.Set;

/**
 * @author Liubo Ren
 * @version 1.0
 */
public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

    void findSubCategoryId(Integer id, Set<Integer> resultSet);
}
