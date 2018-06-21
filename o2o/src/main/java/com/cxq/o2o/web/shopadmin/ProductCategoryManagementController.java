package com.cxq.o2o.web.shopadmin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cxq.o2o.dto.ImageHolder;
import com.cxq.o2o.dto.ProductCategoryExecution;
import com.cxq.o2o.dto.ProductExecution;
import com.cxq.o2o.entity.Product;
import com.cxq.o2o.entity.ProductCategory;
import com.cxq.o2o.entity.Shop;
import com.cxq.o2o.enums.ProductCategoryStateEnum;
import com.cxq.o2o.enums.ProductStateEnum;
import com.cxq.o2o.service.ProductCategoryService;
import com.cxq.o2o.service.ProductService;
import com.cxq.o2o.util.CodeUtil;
import com.cxq.o2o.util.HttpServletRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ProductService productService;
	
	//支持上传商品详情图的最大数量
	private static final int IMAGEMEXCOUNT = 6;
	
	/**
	 * 展示店铺内类别列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> getProductCategoryList(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		long shopId = currentShop.getShopId();
		if(shopId <= 0) {
			modelMap.put("redirect", true);
			modelMap.put("url", "/shopadmin/shoplist"); 
		}else {
			try {
				List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(shopId);
				modelMap.put("productCategoryList", productCategoryList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		}
		return modelMap;
		
	}
	
	/**
	 * 增加商品类别
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		for(ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());//这里使用后端来添加shopId是为了反正前端用户的随意添加
			pc.setCreateTime(new Date());
		}
		if(productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
		
	}
	
	/**
	 * 移除商品类别
	 * @param productCategoryId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		if(productCategoryId != null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success",false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}
	
	/**
	 * 增加商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		// 验证码校验
		if(!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
		}
		//接收前端参数的变量的初始化,包括商品,缩略图,详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = HttpServletRequestUtil.getString(request, "productStr");
		MultipartHttpServletRequest multipartHttpServletRequest = null;
		ImageHolder thumbnail = null;
		List<ImageHolder> productImgList = new ArrayList<ImageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		try {
			//若请求中存在文件流,则取出相关的文件（包括缩略图和详情图）
			if(multipartResolver.isMultipart(request)) {
				multipartHttpServletRequest = (MultipartHttpServletRequest) request;
				//取出缩略图并构建ImageHolder对象
				CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile("thumbnail");
				thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
				//取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
				for(int i = 0;i < IMAGEMEXCOUNT;i++) {
					CommonsMultipartFile productImgFile = (CommonsMultipartFile)multipartHttpServletRequest
							.getFile("productImg" + i);
					if(productImgFile != null) {
						//若取出的第i个详情图片文件不为空，则将其加入详情图列表
						ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
						productImgList.add(productImg);
					}else {
						//若取出的第i个详情图文件流为空，则终止循环
						break;
					}
				}
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "上传图片不能为空");
				return modelMap;
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单String流并将其转换为Product实体类
			product = mapper.readValue(productStr, Product.class);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
		if(product != null && thumbnail != null && productImgList.size() > 0) {
			try {
				//从session中获取当前店铺的Id并赋值给product,减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				Shop shop = new Shop();
				shop.setShopId(currentShop.getShopId());
				product.setShop(shop);
				//执行添加操作
				ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState() == ProductStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch(RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}
}
