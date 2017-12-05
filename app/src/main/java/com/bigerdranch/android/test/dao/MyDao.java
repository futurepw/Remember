package com.bigerdranch.android.test.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/12/2.
 */

public class MyDao {
    private SQLiteDatabase sqLiteDatabase;
    private DatabaseHelper databaseHelper;

    public MyDao(Context context) {
        databaseHelper=new DatabaseHelper(context,"pwd.db",null,1);
    }

    public Cursor getAll(String orderBy, String where) {//返回表中的数据,where是调用时候传进来的搜索内容,orderby是设置中传进来的列表排序类型
        String sql = "SELECT * FORM pwdinf";
        StringBuffer stringBuffer = new StringBuffer(sql);
        if (where != null) {
            stringBuffer.append("WHERE");
            stringBuffer.append(where);
        }
        if (orderBy != null) {
            stringBuffer.append("ORDER BY");
            stringBuffer.append(orderBy);
        }
        return (databaseHelper.getReadableDatabase().rawQuery(stringBuffer.toString(), null));
    }

    public Cursor getAll(){
         Cursor cursor = databaseHelper.getWritableDatabase().query("pwdinf",null,null,null,null,null,null);
        return cursor;
    }

    public Cursor getByName(PwdData pwdData) {//根据点击事件获取name,查询数据库
        String sql = "SELECT * FORM pwdinf WHERE name=?";
        String[] name = {pwdData.getName()};
        return (databaseHelper.getReadableDatabase().rawQuery(sql, name));
    }

    public Cursor getByUserName(PwdData pwdData) {//根据点击事件获取username,查询数据库
        String sql = "SELECT * FORM pwdinf WHERE name=?";
        String[] name = {pwdData.getUsername()};
        return (databaseHelper.getReadableDatabase().rawQuery(sql, name));
    }

    public void insert(PwdData pwdData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", pwdData.getName());
        contentValues.put("username", pwdData.getUsername());
        contentValues.put("password", pwdData.getPassword());
        databaseHelper.getWritableDatabase().insert("pwdinf", null, contentValues);
    }

    public void update(PwdData pwdData, String id) {
        ContentValues contentValues = new ContentValues();
        String[] args = {id};
        contentValues.put("name", pwdData.getName());
        contentValues.put("username", pwdData.getUsername());
        contentValues.put("password", pwdData.getPassword());
        databaseHelper.getWritableDatabase().update("pwdinf", contentValues, "id=?", args);
    }

    public void delete(String id){
        String[] args = {id};
        databaseHelper.getWritableDatabase().delete("pwdinf","id=?",args);
    }

    public String getId(Cursor cursor) {
        return (cursor.getString(cursor.getColumnIndex("id")));
    }

    public String getName(Cursor cursor) {
        return (cursor.getString(cursor.getColumnIndex("name")));
    }

    public String getUserName(Cursor cursor) {
        return (cursor.getString(cursor.getColumnIndex("username")));
    }

    public String getPassword(Cursor cursor) {
        return (cursor.getString(cursor.getColumnIndex("password")));
    }
}
