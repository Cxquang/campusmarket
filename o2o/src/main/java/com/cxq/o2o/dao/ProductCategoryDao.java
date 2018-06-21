package com.cxq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxq.o2o.entity.ProductCategory;

public interface ProductCategoryDao {
	/**
	 * 通过shop id 查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategory(Long shopId);
	
	/**
	 * 批量新增商品类别
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);
	
	/**
	 * 删除指定商品类别
	 * 这里由于mybatis解析不出两个参数，需要指定param
	 * @param productCategoryId
	 * @param shopId
	 * @return 返回受影响的行数
	 */
	int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}
