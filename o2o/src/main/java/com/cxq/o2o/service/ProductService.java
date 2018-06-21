package com.cxq.o2o.service;

import java.io.InputStream;
import java.util.List;

import com.cxq.o2o.dto.ImageHolder;
import com.cxq.o2o.dto.ProductExecution;
import com.cxq.o2o.entity.Product;
import com.cxq.o2o.exceptions.ProductOperationException;

public interface ProductService {
	
	/**
	 * 添加商品信息以及图片处理
	 * 
	 * @return
	 * @throws ProductOperationException
	 */
	ProductExecution addProduct(Product product,ImageHolder thumbnail,
			List<ImageHolder> productImgList) 
					throws ProductOperationException;
}
