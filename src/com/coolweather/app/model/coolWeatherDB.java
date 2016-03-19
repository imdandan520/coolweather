package com.coolweather.app.model;

import java.util.ArrayList;
import java.util.List;

import com.coolweather.app.db.coolWeatherOpenHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class coolWeatherDB {
	
	public static final String DB_NAME="cool_weather";
	
	public static final int VERSION=1;
	private static coolWeatherDB coolWeatherDB;
	private SQLiteDatabase db;
	
	/*���췽��˽�л�*/
	private coolWeatherDB(Context context){
		coolWeatherOpenHelper dbHelper=new coolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	
	/*��ȡcoolWeatherDB��ʵ��*/
	public synchronized static coolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null){
			coolWeatherDB=new coolWeatherDB(context);
		}
		return coolWeatherDB;
		
	}
	
	/*��Provinceʵ���洢�����ݿ�*/
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
			
		}
	}
	
	/*�����ݿ��ȡȫ������ʡ����Ϣ*/
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query("Provance", null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_namecode")));
				list.add(province);
			}while (cursor.moveToNext());
		}
			if(cursor !=null){
				cursor.close();
			}
			return list;		
	}
	
	/*��Cityʵ���洢�����ݿ�*/
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
			
		}
	}
	
	/*�����ݿ��ȡĳʡ�����г�����Ϣ*/
	public List<City> loadCities(int provinceId){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query("City", null, "province_id=?", new String[]{String.valueOf(provinceId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_namecode")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while (cursor.moveToNext());
		}
			if(cursor !=null){
				cursor.close();
			}
			return list;		
	}
	
	/*��Countryʵ���洢�����ݿ�*/
	public void saveCountry(Country country){
		if(country!=null){
			ContentValues values=new ContentValues();
			values.put("country_name", country.getCountryName());
			values.put("country_code", country.getCountryCode());
			values.put("province_id", country.getCityId());
			db.insert("Country", null, values);
			
		}
	}
	
	/*�����ݿ��ȡĳ��������������Ϣ*/
	public List<Country> loadCountries(int cityId){
		List<Country> list=new ArrayList<Country>();
		Cursor cursor=db.query("Country", null, "city_id=?", new String[]{String.valueOf(cityId)}, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Country country=new Country();
				country.setId(cursor.getInt(cursor.getColumnIndex("id")));
				country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
				country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_namecode")));
				country.setCityId(cityId);
				list.add(country);
			}while (cursor.moveToNext());
		}
			if(cursor !=null){
				cursor.close();
			}
			return list;		
	}

}