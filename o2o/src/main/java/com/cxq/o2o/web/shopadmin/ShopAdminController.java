package com.cxq.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="shopadmin",method= {RequestMethod.GET})
public class ShopAdminController {
	
	@RequestMapping(value = "/shopoperation")
	public String shopoperation() {
		//转发至店铺注册/编辑页面
		return "shop/shopoperation";
		
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		//转发至店铺列表页面
		return "shop/shoplist";
		
	}
	
	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		//转发至店铺管理页面
		return "shop/shopmanagement";
		
	}
	
	@RequestMapping(value = "/productcategorylist")
	public String productCategoryList() {
		//转发至商品分类管理页面
		return "shop/productcategorylist";
		
	}
	
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		//转发至商品添加/编辑页面
		return "shop/productoperation";
		
	}
}
