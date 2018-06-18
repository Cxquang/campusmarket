package com.cxq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxq.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	List<ProductCategory> queryShopCategory(@Param("productCategory")
	ProductCategory productCategory);
}
