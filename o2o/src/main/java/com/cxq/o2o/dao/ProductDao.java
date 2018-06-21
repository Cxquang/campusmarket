package com.cxq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cxq.o2o.entity.Product;

public interface ProductDao {

	/**
	 * 查询商品列表并分页，可输入的条件有 : 商品名(模糊),商品状态,店铺Id,商品类别
	 * 
	 * @param productCondition
	 * @param beginIndex
	 * @param pageSize
	 * @return
	 */
	List<Product> queryProductList(@Param("productCondition") Product productCondition,
			@Param("beginIndex") int beginIndex,@Param("pageSize") int pageSize);
	
	/**
	 * 通过productId查询唯一的商品信息
	 * @param productId
	 * @return
	 */
	Product queryProductByProductId(long productId);
	
	/**
	 * 插入商品信息
	 * 
	 * @param product
	 * @return
	 */
	int insertProduct(Product product);
	
	/**
	 * 更新商品信息
	 * @param product
	 * @return
	 */
	int updateProduct(Product product);
}
