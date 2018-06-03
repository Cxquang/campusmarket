package com.cxq.o2o.web.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cxq.o2o.entity.Area;
import com.cxq.o2o.service.AreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaController {
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@Autowired
	private AreaService areaService;
	
	@RequestMapping(value = "/listarea",method = RequestMethod.GET)
	@ResponseBody 
	private Map<String,Object> listArea(){
		logger.info("====start====");
		long startTime = System.currentTimeMillis();
		Map<String,Object> modelMap = new HashMap<String,Object>();
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			//这里的key值与前端框架所需要的集合名称保持一致
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		}catch(Exception e){
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg",e.toString());
		}
		logger.error("test error!");
		long endTime = System.currentTimeMillis();
		//相减得到的值会传入到花括号里面
		logger.debug("costTime:[{}ms]",endTime - startTime);
		logger.info("====end=====");
		//测试user.dir的路径
		System.out.println(System.getProperty("user.dir"));
		return modelMap;
		
	}
}
