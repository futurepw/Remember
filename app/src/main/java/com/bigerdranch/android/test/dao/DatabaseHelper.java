package com.bigerdranch.android.test.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "pwd.db";//数据库名称
    private static final String tableName = "pwdinf";
    private static final String  databasePath = "/data/data/com.bigerdranch.android.test/databases/";
    public DatabaseHelper(Context context, String name, CursorFactory factory,int version) {//构造函数,接收上下文作为参数,直接调用的父类的构造函数
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }
    public DatabaseHelper(Context context) {//构造函数,接收上下文作为参数,直接调用的父类的构造函数
        super(context,databaseName,null,1);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {//创建数据表pwdinf(id,name,username,password)
        String str = "CREATE TABLE "+tableName+" ('id'  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,'name'  VARCHAR(100) NOT NULL,'username'  VARCHAR(100) NOT NULL,'password'  VARCHAR(200) NOT NULL)";
        sqLiteDatabase.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+tableName);
        onCreate(sqLiteDatabase);
    }

}