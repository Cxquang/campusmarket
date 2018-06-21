package com.cxq.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.dao.ShopDao;
import com.cxq.o2o.dto.ImageHolder;
import com.cxq.o2o.dto.ShopExecution;
import com.cxq.o2o.entity.Area;
import com.cxq.o2o.entity.PersonInfo;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.entity.ShopCategory;
import com.cxq.o2o.enums.ShopStateEnum;
import com.cxq.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest{
	@Autowired
	private ShopService shopService;

	
	
	@Test
	public void testAddShop() throws FileNotFoundException {
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		Shop shop = new Shop();
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺3");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");
		File shopImg = new File("E:\\image\\flower.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder thumbnail = new ImageHolder(shopImg.getName(), is);
		ShopExecution se = shopService.addShop(shop,thumbnail);
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

		
	}
	
	@Test
	public void testModifyShop() throws ShopOperationException,FileNotFoundException{
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺名称4");
		File shopImg = new File("E:\\image\\flower.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder thumbnail = new ImageHolder("flower.jpg",is);
		ShopExecution shopExecution = shopService.modifyShop(shop, thumbnail);
		System.out.println("新的图片地址： " + shopExecution.getShop().getShopImg());
		System.out.println("新的店铺名称：" + shopExecution.getShop().getShopName());
	}
	
	@Test
	public void testGetShopList() {
		Shop shopCondition = new Shop();
		ShopCategory sc = new ShopCategory();
		sc.setShopCategoryId(1L);
		shopCondition.setShopCategory(sc);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺列表数： " + se.getShopList().size());
		System.out.println("店铺总数： " + se.getCount());
		
	}
}

