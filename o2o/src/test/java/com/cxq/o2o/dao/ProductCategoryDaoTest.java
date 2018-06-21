package com.cxq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.PrioritizedParameterNameDiscoverer;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.entity.ProductCategory;

public class ProductCategoryDaoTest extends BaseTest {

	
	@Autowired
	private ProductCategoryDao productCategoryDao;
	
	@Test
	public void queryShopCategoryTest() {
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(1L);
		System.out.println(productCategoryList.size());
	}
	
	@Test
	public void batchInsertProductCategoryTest() {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("蔡贤权的商品1");
		productCategory.setPriority(30);
		productCategory.setCreateTime(new Date());
		productCategory.setShopId(1L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("蔡贤权的商品2");
		productCategory2.setPriority(31);
		productCategory2.setCreateTime(new Date());
		productCategory2.setShopId(1L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);
		int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
		assertEquals(2, effectedNum);
		
	}
	
	@Test
	public void deleteProductCategoryTest() {
		long shopId  = 1;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(shopId);
		for(ProductCategory pc : productCategoryList) {
			if("蔡贤权的商品1".equals(pc.getProductCategoryName()) || "蔡贤权的商品2".equals(pc.getProductCategoryName())) {
				int effectedNum = productCategoryDao.deleteProductCategory(pc.getProductCategoryId(), shopId);
				assertEquals(1, effectedNum);
			}
		}
	}
}
