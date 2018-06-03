package com.cxq.o2o.util;

public class PathUtil {
	//获取系统的文件路径分隔符
	private static String seperator = System.getProperty("file.separator");
	
	
	/**
	 * 返回项目图片的根路径
	 * @return
	 */
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")) {
			basePath="E:/image/";
		}else {
			basePath= "/home/cxq/image/";
		}
//		将basePath的斜杠分隔符替换为系统 的文件路径分隔符
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	
	/**
	 * 根据项目的不同需求返回项目图片的子路径
	 * @param shopId
	 * @return
	 */
	public static String getShopImagePath(long shopId) {
		String imagePath = "upload/item/shop/" + shopId + "/";
		return imagePath.replace("/", seperator);
	}
}
