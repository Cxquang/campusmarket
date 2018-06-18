package com.cxq.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	/**
	 * 通过shop id查询店铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	
	
	/**
	 * 分页查询店铺，可输入的条件有：店铺名(模糊),店铺状态，店铺类别，区域Id，owner
	 * 这里使用param注解的原因是因为有多个参数，要指定参数的唯一标识
	 * 
	 * @param shopCondition
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize返回的条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
			@Param("rowIndex")int rowIndex,@Param("pageSize") int pageSize);
	
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition")Shop shopCondition);
}
