package com.cxq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.entity.Area;
import com.cxq.o2o.entity.PersonInfo;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest{
	@Autowired
	private ShopDao shopDao;
	@Test
	@Ignore
	public void insertShopTest() {
		
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();
		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);
		Shop shop = new Shop("蔡贤权的店铺","test","test","13680442679",
				"test",1,new Date(),new Date(),1,"审核中",area,owner,shopCategory);
		int effectedNum = shopDao.insertShop(shop);
		System.out.println(effectedNum);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void updateShopTest() {
		Shop shop = new Shop("测试的店铺","测试描述","测试地址","13680442679",
				"test",1,new Date(),new Date(),1,"审核中",null,null,null);
		shop.setShopId(1L);
		int effectedNum = shopDao.updateShop(shop);
		assertEquals(1, effectedNum);
	}
	
	@Test
	public void queryByShopIdTest() {
		Shop shop = shopDao.queryByShopId(1L);
		System.out.println("areaId: " + shop.getArea().getAreaId());
		System.out.println("areaName: " + shop.getArea().getAreaName());
		
	}
	
	@Test
	public void queryShopListTest() {
		Shop shopCondition = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		shopCondition.setOwner(personInfo);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		for (Shop shop : shopList) {
			System.out.println(shop.toString());
		}
	}
	
	@Test
	public void queryShopCountTest() {
		Shop shopCondition = new Shop();
		PersonInfo personInfo = new PersonInfo();
		personInfo.setUserId(1L);
		shopCondition.setOwner(personInfo);
		int shopListCount = shopDao.queryShopCount(shopCondition);
		System.out.println(shopListCount);
	}
}
