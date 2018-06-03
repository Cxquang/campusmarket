package com.cxq.o2o.util;

import javax.servlet.http.HttpServletRequest;

/*
 * 判断验证码
 */
public class CodeUtil {
	public static boolean checkVerifyCode(HttpServletRequest request) {
		//从会话中获取验证码图片
		String verifyCodeExpected = (String)request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
		System.out.println(verifyCodeExpected);
		System.out.println(verifyCodeActual);
		if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		}
		return true;
	}
}
