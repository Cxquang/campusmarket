package com.cxq.o2o.dao;

import com.cxq.o2o.entity.Shop;

public interface ShopDao {
	
	
	/**
	 * 新增店铺：返回1表示成功；返回-1失败(mybatis 默认返回值)
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺信息
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop); 
}
