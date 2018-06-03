package com.cxq.o2o.enums;

public enum ShopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"shop信息为空");
	private int state;
	private String stateInfo;
	
	private ShopStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo =stateInfo;
	}
	
	/*
	 * 依据传入的state返回相应的enum值
	 */
	public static ShopStateEnum stateOf(int state) {
		//values():代表所有的枚举对象
		for (ShopStateEnum stateEnum : values()) {
			if(stateEnum.getState() == state) {
				return stateEnum;
			}
		}
		return null;
	}
	//get方法:获取当前enum对象的状态值
	public int getState() {
		return state;
	}
	//get方法:获取当前enum对象的stateInfo
	public String getStateInfo() {
		return stateInfo;
	}
	
}
