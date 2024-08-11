package com.hust.mymall.service.impl;

import com.hust.mymall.consts.MallConst;
import com.hust.mymall.dao.CategoryMapper;
import com.hust.mymall.pojo.Category;
import com.hust.mymall.service.ICategoryService;
import com.hust.mymall.vo.CategoryVo;
import com.hust.mymall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 耗时：http请求 > 磁盘 > 内存
     * mysql
     * @return
     */
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {

        List<Category> categories = categoryMapper.selectAll();
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(e -> e.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());
        findSubCategory(categoryVoList, categories);


        return ResponseVo.success(categoryVoList);
    }

    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id, resultSet, categories);
    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)){
                resultSet.add(category.getId());

                findSubCategoryId(category.getId(), resultSet, categories);
            }
        }
    }

    private void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {

            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                // 查到内容，设置子目录，继续往下查
                if(categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                findSubCategory(subCategoryVoList,categories);
                categoryVo.setSubCategories(subCategoryVoList);
            }
        }
    }
    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
