package com.guan.accountms.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guan.accountms.model.Tb_outaccount;

import java.util.ArrayList;
import java.util.List;

/**
 * 支出信息dao类
 *
 * @author Guan
 * @file com.guan.accountms.dao
 * @date 2015/11/13
 * @Version 1.0
 */
public class OutaccountDAO {
	private DBOpenHelper helper;
	private SQLiteDatabase db;

	public OutaccountDAO(Context context) {
		helper = new DBOpenHelper(context);
	}

	/**
	 * 添加支出信息
	 *
	 * @param tb_outaccount
	 */
	public void add(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();
		// 执行添加支出信息操作
		db.execSQL(
				"insert into tb_outaccount (_id,money,time,type,address,mark) values (?,?,?,?,?,?)",
				new Object[] { tb_outaccount.getid(), tb_outaccount.getMoney(),
						tb_outaccount.getTime(), tb_outaccount.getType(),
						tb_outaccount.getAddress(), tb_outaccount.getMark() });
	}

	/**
	 * 更新支出信息
	 *
	 * @param tb_outaccount
	 */
	public void update(Tb_outaccount tb_outaccount) {
		db = helper.getWritableDatabase();
		// 执行修改支出信息操作
		db.execSQL(
				"update tb_outaccount set money = ?,time = ?,type = ?,address = ?,mark = ? where _id = ?",
				new Object[] { tb_outaccount.getMoney(),
						tb_outaccount.getTime(), tb_outaccount.getType(),
						tb_outaccount.getAddress(), tb_outaccount.getMark(),
						tb_outaccount.getid() });
	}

	/**
	 * 查找支出信息
	 *
	 * @param id
	 * @return
	 */
	public Tb_outaccount find(int id) {
		db = helper.getWritableDatabase();
		Cursor cursor = db
				.rawQuery(
						"select _id,money,time,type,address,mark from tb_outaccount where _id = ?",
						new String[] { String.valueOf(id) });
		if (cursor.moveToNext()) {
			// 将遍历到的支出信息存储到Tb_outaccount类中
			return new Tb_outaccount(
					cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getDouble(cursor.getColumnIndex("money")),
					cursor.getString(cursor.getColumnIndex("time")),
					cursor.getString(cursor.getColumnIndex("type")),
					cursor.getString(cursor.getColumnIndex("address")),
					cursor.getString(cursor.getColumnIndex("mark")));
		}
		return null;
	}

	/**
	 * 刪除支出信息
	 *
	 * @param ids
	 */
	public void detele(Integer... ids) {
		if (ids.length > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				sb.append('?').append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			db = helper.getWritableDatabase();
			// 执行删除支出信息操作
			db.execSQL("delete from tb_outaccount where _id in (" + sb + ")",
					(Object[]) ids);
		}
	}

	/**
	 * 获取支出信息
	 *
	 * @param start
	 *            起始位置
	 * @param count
	 *            每页显示数量
	 * @return
	 */
	public List<Tb_outaccount> getScrollData(int start, int count) {
		List<Tb_outaccount> tb_outaccount = new ArrayList<Tb_outaccount>();
		db = helper.getWritableDatabase();
		// 获取所有支出信息
		Cursor cursor = db.rawQuery("select * from tb_outaccount limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()) {
			// 将遍历到的支出信息添加到集合中
			tb_outaccount.add(new Tb_outaccount(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getDouble(cursor
					.getColumnIndex("money")), cursor.getString(cursor
					.getColumnIndex("time")), cursor.getString(cursor
					.getColumnIndex("type")), cursor.getString(cursor
					.getColumnIndex("address")), cursor.getString(cursor
					.getColumnIndex("mark"))));
		}
		return tb_outaccount;
	}

	/**
	 * 获取总记录数
	 *
	 * @return
	 */
	public long getCount() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(_id) from tb_outaccount", null);
		if (cursor.moveToNext()) {
			return cursor.getLong(0);
		}
		return 0;
	}

	/**
	 * 获取支出最大编号
	 *
	 * @return
	 */
	public int getMaxId() {
		db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select max(_id) from tb_outaccount", null);
		while (cursor.moveToLast()) {
			return cursor.getInt(0);
		}
		return 0;
	}
}
