package com.cxq.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	
	//获取classpath的绝对值路径
	//分析：由于方法是通过线程执行，可以通过线程逆推到类加载器，从类加载器得到资源路径
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	/*
	 * 处理缩略图：门面照以及商品的小图
	 * 这个方法处理的是用户传递过来的文件流，使用spring自带的文件处理对象：CommonsMultipartFile；会携带用户对文件的命名名称(不用)
	 * 使用File对象接收文件对象,这里是为了方便使用Junit单元测试， 可以直接传入相关的文件
	 * targetAddr:图片存储路径
	 */
	public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr) {
		String realFileName = getRandomFileName();//获取文件的随机名
		String extension = getFileExtension(fileName);//获取文件的扩展名，如jpg，png等
		makeDirPath(targetAddr);//如果目录不存在，创建目录
		String relativeAddr = targetAddr + realFileName + extension;
		//保存的压缩后的图片需要图片的名称加后缀名
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		try {
			Thumbnails.of(thumbnailInputStream).size(2000,2000)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 1f)
			.outputQuality(0.5f).toFile(dest);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return relativeAddr;
		
	};

	
	/**生成随机文件名，当前年月日小时分钟秒钟+五位随机数
	 * @return
	 */
	public static String getRandomFileName() {
		// 获取随机的五位数
//		从89999到99999之间取值
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	
	/**
	 * 获取输入文件流的扩展名
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		//lastIndexOf:返回的是索引号
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * 创建目标路径所涉及到的目录，即home/work/cxq/xxx.jpg,那么home work cxq这三个目录都需要自动创建
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		//全路径
		String realFilePatentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFilePatentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	
	/*
	 * 测试：改变flower图片的大小，打上“百度经验”水印图片；图片压缩
	 */
	public static void main(String[] args) throws IOException {
		/*size:指定flower图片的大小
		watermark三个参数：
		第一个：指定水印图片放在flower图片的位置，如右下角
		第二个：指定水印图片的路径
		第三个：指定水印透明度
		outputQuality:压缩图片，百分比
		toFile:保存文件位置
		*/
		Thumbnails.of(new File("E:\\image\\flower.jpg"))
		.size(2000, 2000)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 1f)
		.outputQuality(0.5f).toFile("E:\\image\\flower1.jpg");
		System.out.println(basePath + "/watermark.jpg");
	}
}
