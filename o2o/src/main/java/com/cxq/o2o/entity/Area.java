package com.cxq.o2o.entity;

import java.util.Date;


/*
 * 类型使用引用类型
 * 基本类型会为属性赋予默认的值
 */
public class Area {
	//ID
	private Integer areaId;
	//名称
	private String areaName;
	//权重：显示的优先级
	private Integer priority;
	//创建时间
	private Date createTime;
	//最新的修改时间
	private Date lastEditTime;
	
	public Area() {
		
	}
	public Area(String areaName, Integer priority, Date createTime, Date lastEditTime) {
		super();
		this.areaName = areaName;
		this.priority = priority;
		this.createTime = createTime;
		this.lastEditTime = lastEditTime;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
}
