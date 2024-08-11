package com.hust.mymall.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Liubo Ren
 * @version 1.0
 */
@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;


}
