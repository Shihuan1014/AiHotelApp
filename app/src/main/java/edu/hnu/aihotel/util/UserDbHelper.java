package edu.hnu.aihotel.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import edu.hnu.aihotel.model.User;

public class UserDbHelper {
    public final SQLiteDatabase db;

    public UserDbHelper(Context context){
        DatabaseHelper helper = new DatabaseHelper(context,"AiHotel",null,1);
        db = helper.getWritableDatabase();
    }

    public Map<String,String> getLoginUser(){
        Map<String,String> map = new HashMap<String , String>();
        User user = new User();
        Cursor cursor = db.rawQuery("select * from user where status = '1'",null);
        cursor.moveToFirst();
        try {
            if (!cursor.isAfterLast()){
                map.put("tel",cursor.getString(1));
                map.put("password",cursor.getString(2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public void insertUser(String id,String tel, String password){
        ContentValues values = new ContentValues();
        values.put("id",id);
        values.put("tel",tel);
        values.put("password",password);
        values.put("status","1");
        db.insert("user",null,values);
    }

    public Map<String,String> getUserById(String id){
        Map<String,String> map = new HashMap<String , String>();
        User user = new User();
        String sql = "select * from user where id = '"+id+"'";
        System.out.println(sql);
        Cursor cursor = db.rawQuery(sql ,null);
        cursor.moveToFirst();
        try {
            if (!cursor.isAfterLast()){
                map.put("tel",cursor.getString(1));
                map.put("password",cursor.getString(2));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

}

