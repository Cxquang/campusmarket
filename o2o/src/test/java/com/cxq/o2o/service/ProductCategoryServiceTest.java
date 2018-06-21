package com.cxq.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.entity.ProductCategory;

public class ProductCategoryServiceTest extends BaseTest {
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Test
	public void productCategoryServiceTest() {
		List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(1L);
		System.out.println(productCategoryList.size());
	}
}
