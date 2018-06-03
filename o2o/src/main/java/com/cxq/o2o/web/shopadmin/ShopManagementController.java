package com.cxq.o2o.web.shopadmin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cxq.o2o.dto.ShopExecution;
import com.cxq.o2o.entity.Area;
import com.cxq.o2o.entity.PersonInfo;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.entity.ShopCategory;
import com.cxq.o2o.enums.ShopStateEnum;
import com.cxq.o2o.exceptions.ShopOperationException;
import com.cxq.o2o.service.AreaService;
import com.cxq.o2o.service.ShopCategoryService;
import com.cxq.o2o.service.ShopService;
import com.cxq.o2o.util.CodeUtil;
import com.cxq.o2o.util.HttpServletRequestUtil;
import com.cxq.o2o.util.ImageUtil;
import com.cxq.o2o.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
	
	@Autowired
	private  ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getshopinitinfo(){
		//定义一个modelMap
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		}catch(Exception e){
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			
		}
		return modelMap;
		
	}
	
	/**
	 * 注册店铺
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registershop",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> registerShop(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输了错误的验证码");
			return modelMap;
		}
		//1、接收并转化相应的参数，包括店铺信息以及图片信息
		String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		//接收上传的文件
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//判断request中是否有上传的文件流
		if(commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = 
					(MultipartHttpServletRequest) request;
			//将文件保存到CommonsMultipartFile类型的shopImg中
			shopImg = (CommonsMultipartFile)multipartHttpServletRequest.getFile("shopImg");
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		//2、注册店铺
		if(shop != null && shopImg != null) {
			//通过session获取用户id，这里暂时自定义数据
			PersonInfo owner = new PersonInfo();
			owner.setUserId(1L);
			shop.setOwner(owner);
			ShopExecution se;
			try {
				se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
				if(se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
			return modelMap;
		}
		
	}
}
