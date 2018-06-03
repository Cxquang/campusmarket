package com.cxq.o2o.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.cxq.o2o.BaseTest;
import com.cxq.o2o.util.ImageUtil;
import com.cxq.o2o.util.PathUtil;

public class Test1 extends BaseTest{
	
	@Test
	public void FileTest() {
		File shopFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
		try {
			shopFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
