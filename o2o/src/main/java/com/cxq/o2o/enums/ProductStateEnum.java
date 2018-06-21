package com.cxq.o2o.enums;

public enum ProductStateEnum {
	PULLDOWN(0,"商品下架"),OFFLINE(-1,"非法商品"),SUCCESS(1,"操作成功"),
	INNER_ERROR(-1001,"内部系统错误"),
	NULL_PRODUCTID(-1002,"ProductId为空"),EMPTY(-1003,"商品信息为空");
	private int state;
	private String stateInfo;
	
	private ProductStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo =stateInfo;
	}
	
	/*
	 * 依据传入的state返回相应的enum值
	 */
	public static ProductStateEnum stateOf(int state) {
		//values():代表所有的枚举对象
		for (ProductStateEnum stateEnum : values()) {
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
