package com.cxq.o2o.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxq.o2o.dao.ShopDao;
import com.cxq.o2o.dto.ShopExecution;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.enums.ShopStateEnum;
import com.cxq.o2o.exceptions.ShopOperationException;
import com.cxq.o2o.service.ShopService;
import com.cxq.o2o.util.ImageUtil;
import com.cxq.o2o.util.PathUtil;


@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	
	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) {
		/*
		 * 空值判断
		 */
		
		//还需对shop里面的area和category进行非空判断？
		if(shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		
			//给店铺信息赋予初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			//添加店铺信息
			System.out.println(shop.toString());
			int effectedNum = shopDao.insertShop(shop);
			try {
			if(effectedNum <= 0) {
				//只有抛出ShopOperationException时事务才会终止和回滚
				throw new ShopOperationException("店铺创建失败");
			}else {
				if(shopImgInputStream != null) {
					//存储图片,将重组添加信息的图片存储地址保存到shop实体类中
					try {
						addShopImg(shop,shopImgInputStream,fileName);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new ShopOperationException("addshopImgInputStream error：" + e.getMessage());
					}
					
					//更新店铺的图片地址，保存到数据库中
					effectedNum = shopDao.updateShop(shop);
					if(effectedNum <= 0) {
						//只有抛出ShopOperationException时事务才会终止和回滚
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		}catch(Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK,shop);
	}
	private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
		// 获取shop图片目录的相对值路径,在generateThumbnail的方法中会与getImgBasePath中的 绝对路径结合
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		System.out.println(dest);
		String shopImgInputStreamAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName, dest);
		shop.setShopImg(shopImgInputStreamAddr);
		
	}

}
