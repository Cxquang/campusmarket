package com.cxq.o2o.service;

import java.io.File;
import java.io.InputStream;

import com.cxq.o2o.dto.ShopExecution;
import com.cxq.o2o.entity.Shop;

public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName);
}
