package com.guan.accountms.model;

/**
 * 支出信息实体类
 *
 * @author Guan
 * @file com.guan.accountms.model
 * @date 2015/11/13
 * @Version 1.0
 */
public class Tb_outaccount {
	// 存储支出编号
	private int _id;
	// 存储支出金额
	private double money;
	// 存储支出时间
	private String time;
	// 存储支出类别
	private String type;
	// 存储支出地点
	private String address;
	// 存储支出备注
	private String mark;

	public Tb_outaccount() {
		super();
	}

	// 定义有参构造函数，用来初始化支出信息实体类中的各个字段
	public Tb_outaccount(int id, double money, String time, String type,
						 String address, String mark) {
		super();
		this._id = id;
		this.money = money;
		this.time = time;
		this.type = type;
		this.address = address;
		this.mark = mark;
	}

	public int getid() {
		return _id;
	}

	public void setid(int id) {
		this._id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
