package com.cxq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.dto.ImageHolder;
import com.cxq.o2o.dto.ProductExecution;
import com.cxq.o2o.entity.Product;
import com.cxq.o2o.entity.ProductCategory;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.enums.ProductStateEnum;
import com.cxq.o2o.exceptions.ShopOperationException;

public class ProductServiceTest extends BaseTest {
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void addProductTest() throws FileNotFoundException,ShopOperationException {
		//创建shopId为1且ProductCategoryId为1的商品实例并给其成员变量赋值
		Product product = new Product();
		Shop shop = new Shop();
		shop.setShopId(1L);
		product.setShop(shop);
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryId(2L);
		product.setProductCategory(productCategory);
		product.setProductName("测试商品1");
		product.setProductDesc("测试商品1");
		product.setPriority(20);
		product.setCreateTime(new Date());
		product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
		
		//创建缩略图文件流
		File thumbnailFile = new File("E:\\image\\bear.jpg");
		InputStream is = new FileInputStream(thumbnailFile);
		ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);
		//创建两个商品详情图片文件流并将它们添加到详情图列表中
		File productImg1 = new File("E:\\image\\view.jpg");
		File productImg2 = new File("E:\\image\\view.jpg");
		InputStream is1 = new FileInputStream(productImg1);
		InputStream is2 = new FileInputStream(productImg2);
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		productImgList.add(new ImageHolder(productImg1.getName(), is1));
		productImgList.add(new ImageHolder(productImg2.getName(), is2));
		//添加商品验证
		ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
		assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
		
		
	}
}
