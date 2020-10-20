package com.example.pitcerbatter;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class TestOpenHelper extends SQLiteOpenHelper {

    // データーベースのバージョン管理。データベースは端末に保存されるので新しくしたい場合はこの数字を変更
    private static final int DATABASE_VERSION = 6;

    // データーベースのパーツに名前をつける
    private static final String DATABASE_NAME = "testDB.db";//データベース名（表の集まり）
    private static final String TABLE_NAME1 = "Batters_table";
    private static final String TABLE_NAME2 = "Pitchers_table";//テーブル名（表の名前）
    private static final String _ID = "_id";//これはよくわからん。id
    private static final String COLUMN_NAME_TITLE = "NAME"; //カラム(表の列)

        //テーブル作成
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME1 + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT not null," +
                    "bats" + "  REAL not null,"+
                    "strokes"+"  REAL not null,"+
                    "hit" + "  REAL not null,"+
                    "total_runs" + "  REAL not null,"+
                    "fdball" + "  REAL not null,"+
                    "strikeout" + " INTEGER not null,"+
                    "bant" + " INTEGER not null,"+
                    "fly"+" INTEGER not null,"+
                    "left"+"  REAL not null,"+
                    "centor"+"  REAL not null,"+
                    "right"+"  REAL not null"+
                    ")";

    private static final String SQL_CREATE_ENTRIES2 =
            "CREATE TABLE " + TABLE_NAME2 + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT not null," +
                     "total_out2"+" REAL not null,"+
                     "total_points"+" REAL not null,"+
                     "total_strike_outs"+" REAL not null,"+
                     "total_foul"+" REAL not null,"+
                     "total_swing"+" REAL not null,"+
                     "total_miss_strike"+" REAL not null,"+
                     "total_miss_ball"+" REAL not null,"+
                     "total_goro"+" REAL not null,"+
                     "total_liner"+" REAL not null,"+
                     "total_fly"+" REAL not null,"+
                     "total_fourball"+" REAL not null,"+
                     "bats"+" REAL not null,"+
                     "strokes"+" REAL not null,"+
                     "total_singlehit"+" REAL not null,"+
                     "total_twobasehit"+" REAL not null,"+
                     "total_threebasehit"+" REAL not null,"+
                     "total_homerun"+" REAL not null"+
                    ")";


    private static final String SQL_DELETE_ENTRIES1 =
            "DROP TABLE IF EXISTS " + TABLE_NAME1;

    private static final String SQL_DELETE_ENTRIES2 =
            "DROP TABLE IF EXISTS " + TABLE_NAME2;


    TestOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // 端末にSQLiteファイルがなければSQLiteファイルが作成
        db.execSQL(SQL_CREATE_ENTRIES);
      //  db.execSQL(SQL_CREATE_ENTRIES2);
//最初から値を入れておくならここ
    }
    //ここから下でアップデートの管理をしている
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別
        db.execSQL(
                SQL_DELETE_ENTRIES1
        );
     //   db.execSQL(
       //         SQL_DELETE_ENTRIES2
        //);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
