package com.guan.accountms.model;

/**
 * 密码数据表实体类
 *
 * @author Guan
 * @file com.guan.accountms.model
 * @date 2015/11/13
 * @Version 1.0
 */
public class Tb_pwd {
	// 定义字符串，表示用户密码
	private String password;

	public Tb_pwd() {
		super();
	}

	public Tb_pwd(String password) {
		super();
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
