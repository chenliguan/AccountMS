package com.guan.accountms.model;

/**
 * 便签信息实体类
 *
 * @author Guan
 * @file com.guan.accountms.model
 * @date 2015/11/13
 * @Version 1.0
 */
public class Tb_flag {
	// 存储便签编号
	private int _id;
	// 存储便签信息
	private String flag;

	public Tb_flag() {
		super();
	}

	// 定义有参构造函数，用来初始化便签信息实体类中的各个字段
	public Tb_flag(int id, String flag) {
		super();
		this._id = id;
		this.flag = flag;
	}

	public int getid() {
		return _id;
	}

	public void setid(int id) {
		this._id = id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
