package com.haomins.www.stockwatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by haominshi on 3/3/18.
 */

public class DBHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "StockWatchDB";
	private static final String TABLE_NAME = "StockWatchTable";
	private static final String CODE = "StockCode";
	private static final String NAME = "StockName";
	private static final String TAG = "DBHandler";

	//db sql: create
	private static final String SQL_CREATE_TABLE =
			"CREATE TABLE " + TABLE_NAME + " ( " + CODE + " TEXT not null unique, " + NAME +" TEXT not null)";
	private SQLiteDatabase db;

	public DBHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		//nothing
	}

	public void addStock(Stock s){
		ContentValues values = new ContentValues();
		values.put(CODE, s.getCode());
		values.put(NAME, s.getName());
		db.insert(TABLE_NAME,null, values);
		Log.d(TAG, "addStock: inserted"+s.getCode()+s.getName());
	}

	

	public void deleteStock(String c){
		int cnt = db.delete(TABLE_NAME, CODE +" = ?", new String[] {c});
	}

	public ArrayList<String[]> loadStock(){
		ArrayList<String[]> stock = new ArrayList<>();
		Cursor cursor = db.query(TABLE_NAME, new String[]{CODE, NAME}, null, null, null , null, null);
		if (cursor != null){
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++){
				String c = cursor.getString(0);
				String n = cursor.getString(1);
				stock.add(new String[]{c, n});
				cursor.moveToNext();
			}
			cursor.close();
		}
		return stock;
	}






}
