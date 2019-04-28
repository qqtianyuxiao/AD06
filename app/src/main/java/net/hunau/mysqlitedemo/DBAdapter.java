package net.hunau.mysqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBAdapter {
    private  final String DB_NAME = "user.db";
    private  final String DB_TABLE = "tb_user";
    private  final int DB_VERSION = 1;

    public  final String KEY_ID = "id";
    public  final String KEY_NAME = "name";
    public  final String KEY_PWD = "pwd";
    public  final String KEY_SEXY = "sexy";
    public  final String KEY_ISUSED = "isused";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context _context) {
        context = _context;
    }

    /** Close the database */
    public void close() {
        if (db != null){
            db.close();
            db = null;
        }
    }

    /** Open the database */
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        }
        catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }


    public long insert(User user) {
        ContentValues newValues = new ContentValues();

        newValues.put(KEY_NAME, user.getName());
        newValues.put(KEY_PWD, user.getPwd());
        newValues.put(KEY_SEXY, user.getSexy());
        newValues.put(KEY_ISUSED, user.isIsused());

        return db.insert(DB_TABLE, null, newValues);
    }


    public User[] queryAllData() {
        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_PWD, KEY_SEXY,KEY_ISUSED},
                null, null, null, null, null);
        return ConvertToPeople(results);
    }

    public User[] queryOneData(long id) {
        Cursor results =  db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_PWD, KEY_SEXY,KEY_ISUSED},
                KEY_ID + "=" + id, null, null, null, null);
        return ConvertToPeople(results);
    }

    private User[] ConvertToPeople(Cursor cursor){
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()){
            return null;
        }
        User[] users = new User[resultCounts];
        for (int i = 0 ; i<resultCounts; i++){
            users[i] = new User();
            users[i].setID(cursor.getInt(0));
            users[i].setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));;
            users[i].setPwd(cursor.getString(cursor.getColumnIndex(KEY_PWD)));
            users[i].setSexy(cursor.getString(cursor.getColumnIndex(KEY_SEXY)));
            users[i].setIsused(cursor.getInt(cursor.getColumnIndex(KEY_ISUSED))==1?true:false);//cursor.getInt(cursor.getColumnIndex(KEY_ISUSED))==1?true:false;

            cursor.moveToNext();
        }
        return users;
    }

    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    public long deleteOneData(long id) {
        return db.delete(DB_TABLE,  KEY_ID + "=" + id, null);
    }

    public long updateOneData(long id , User users){
        ContentValues updateValues = new ContentValues();
        updateValues.put(KEY_NAME, users.getName());
        updateValues.put(KEY_PWD, users.getPwd());
        updateValues.put(KEY_SEXY, users.getSexy());
        updateValues.put(KEY_ISUSED,users.isIsused());

        return db.update(DB_TABLE, updateValues,  KEY_ID + "=" + id, null);
    }

    /** 静态Helper类，用于建立、更新和打开数据库*/
    private  class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private  final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME+ " text not null, " + KEY_PWD+ " text not null," + KEY_SEXY + " text not null,"+KEY_ISUSED + " boolean);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
        }
    }
}
