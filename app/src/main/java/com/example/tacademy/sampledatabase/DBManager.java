package com.example.tacademy.sampledatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2016-08-11.
 */
public class DBManager extends SQLiteOpenHelper{

    private static DBManager instance;
    public static DBManager getInstance(){

    if(instance == null){
        instance = new DBManager();
    }
        return instance;
    }

    private static final String DB_NAME = "address_db"; //디비 이름 설정
    private static final int DB_VERSION = 1; // 디비 버젼 설정

    private DBManager(){
        super(MyApplication.getContext(), DB_NAME, null, DB_VERSION); //(context ,디비이름, 커서팩토리, 디비버젼)
    }


    @Override //테이블 생성
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + AddressContract.Address.TABLE+" (" +
                AddressContract.Address._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                AddressContract.Address.COLUMN_NAME +" TEXT NOT NULL," +
                AddressContract.Address.COLUMN_AGE +" INTEGER, " +
                AddressContract.Address.COLUMN_PHONE +" TEXT, " +
                AddressContract.Address.COLUMN_ADDRESS +" TEXT);";

        db.execSQL(sql); //실행
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Person insert(Person p){
        if(p.id == -1){
            SQLiteDatabase db = getReadableDatabase(); //getReadableDatabase() 는 데이타베이스를 사용하겠다고 오픈 하는 순간 onCreate()를 호출함
                                                        //DB 만든걸 사용 함
            ContentValues values = new ContentValues(); //값을 넣어주기 위한 ContentValues() 객체 생성
            values.put(AddressContract.Address.COLUMN_NAME, p.name); // 값을 넣어줌
            values.put(AddressContract.Address.COLUMN_AGE, p.age);
            values.put(AddressContract.Address.COLUMN_PHONE, p.phone);
            values.put(AddressContract.Address.COLUMN_ADDRESS, p.address);

            long id = db.insert(AddressContract.Address.TABLE, null, values);
            p.id = id;
        }else{
            update(p);
        }
        return p;
    }

    public Person update(Person p) {
        if (p.id != -1) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(AddressContract.Address.COLUMN_NAME, p.name);
            values.put(AddressContract.Address.COLUMN_AGE, p.age);
            values.put(AddressContract.Address.COLUMN_PHONE, p.phone);
            values.put(AddressContract.Address.COLUMN_ADDRESS, p.address);
            String where = AddressContract.Address._ID + " = ?";
            String[] args = {"" + p.id};
            db.update(AddressContract.Address.TABLE, values, where, args);
            return p;
        } else {
            return insert(p);
        }
    }

    public void delete(Person p) {
        if (p.id != -1) {
            SQLiteDatabase db = getWritableDatabase();
            String where = AddressContract.Address._ID + " = ?";
            String[] args = {"" + p.id};
            db.delete(AddressContract.Address.TABLE, where, args);
            p.id = -1;
        }
    }

    public List<Person> getPersonList(String keyword){
        List<Person> list = new ArrayList<>();
        Cursor c = getPersonCursor(keyword);
        while(c.moveToNext()){ //커서 다음으로 이동
            Person p = new Person();
            p.id = c.getLong(c.getColumnIndex(AddressContract.Address._ID));
            p.name = c.getString(c.getColumnIndex(AddressContract.Address.COLUMN_NAME));
            p.age = c.getInt(c.getColumnIndex(AddressContract.Address.COLUMN_AGE));
            p.phone = c.getString(c.getColumnIndex(AddressContract.Address.COLUMN_PHONE));
            p.address = c.getString(c.getColumnIndex(AddressContract.Address.COLUMN_ADDRESS));

        list.add(p);
        }
        c.close();
        return list;
    }

    public Cursor getPersonCursor(String keyword) { //person 테이블에 커서 부분?
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {AddressContract.Address._ID,
                AddressContract.Address.COLUMN_NAME,
                AddressContract.Address.COLUMN_AGE,
                AddressContract.Address.COLUMN_PHONE,
                AddressContract.Address.COLUMN_ADDRESS};
        String selection = null;
        String[] selectionArgs = null;
        if (!TextUtils.isEmpty(keyword)) {
            selection = AddressContract.Address.COLUMN_NAME + " LIKE ?";
            selectionArgs = new String[]{
                    "%" + keyword + "%"
            };
        }
        String groupBy = null;
        String having = null;
        String orderBy = AddressContract.Address.COLUMN_NAME + " COLLATE LOCALIZED ASC";
        Cursor c = db.query(AddressContract.Address.TABLE,
                columns, selection, selectionArgs, groupBy, having, orderBy);
        return c;
    }
}