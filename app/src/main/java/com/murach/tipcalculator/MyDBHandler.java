package com.murach.tipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "tips.db";
    public static final String TABLE_TIPS = "tips";
    public static final String COLUMN_ID = "_id";
    public static final int COLUMN_ID_NO = 0;
    public static final String COLUMN_BILL_DATE = "bill_date";
    public static final int COLUMN_BILL_DATE_NO = 1;
    public static final String COLUMN_BILL_AMOUNT = "bill_amount";
    public static final int COLUMN_BILL_AMOUNT_NO = 2;
    public static final String COLUMN_TIP_PERCENT = "tip_percent";
    public static final int COLUMN_TIP_PERCENT_NO = 3;

    ArrayList<Tip> tips = new ArrayList<Tip>();
    int entries = 0;

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_TIPS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BILL_DATE + " TEXT, " + COLUMN_BILL_AMOUNT + " TEXT, " + COLUMN_TIP_PERCENT + " TEXT " + ");";
        db.execSQL(query);
        insertTestData(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPS);
        onCreate(db);
    }


    public void insertTestData(SQLiteDatabase db){
        ContentValues value1 = new ContentValues();
        value1.put(COLUMN_BILL_DATE, 0);
        value1.put(COLUMN_BILL_AMOUNT, 50.00);
        value1.put(COLUMN_TIP_PERCENT, 0.15);

        ContentValues value2 = new ContentValues();
        value2.put(COLUMN_BILL_DATE, 0);
        value2.put(COLUMN_BILL_AMOUNT, 75.15);
        value2.put(COLUMN_TIP_PERCENT, 0.15);

        //SQLiteDatabase db = getWritableDatabase();  //get reference to DB
        db.insert(TABLE_TIPS, null, value1);
        db.insert(TABLE_TIPS, null, value2);
        //db.close();

    }


    public ArrayList<Tip> getTips(int num){

        if(num == 1) {

            String query = "SELECT * FROM " + TABLE_TIPS + " WHERE 1";
            SQLiteDatabase db = getWritableDatabase();

            //cursor point to a location in your results
            Cursor c = db.rawQuery(query, null);
            //move to the first row in your results
            c.moveToFirst();
            while (!c.isAfterLast()) {



                int id = c.getInt(COLUMN_ID_NO);
                int date = c.getInt(COLUMN_BILL_DATE_NO);
                int amount = c.getInt(COLUMN_BILL_AMOUNT_NO);
                int percent = c.getInt(COLUMN_TIP_PERCENT_NO);

                Tip tip = new Tip(id, date, amount, percent);
                tips.add(tip);
                entries++;
                c.moveToNext();

            }
            db.close();
        }

        return tips;
    }


  /*  private Tip cursorToProduct(Cursor c) {
        Tip tip = new Tip( );
        tip.setId(c.getLong(0));
        tip.setDateMillis(c.getLong(1));
        tip.setBillAmount(c.getFloat(2));
        tip.setTipPercent(c.getFloat(3));

        return tip;
    }
*/
    public void saveCalculation(float amount, float percent){
        long date = System.currentTimeMillis();
        int entry = entries + 1;

        Tip savedTips = new Tip(entry, date, amount, percent);

        String insert = "INSERT INTO " + TABLE_TIPS + " values " + entry + ", " + date + ", " + amount + "' " + percent;


        SQLiteDatabase db = getWritableDatabase();  //get reference to DB
        db.execSQL(insert);
        entries++;
        tips.add(savedTips);
        db.close();

    }

    public ArrayList<Tip> lastTip(int num) {

        if(num == 1) {

            String query = "SELECT * FROM " + TABLE_TIPS + " WHERE 1";
            SQLiteDatabase db = getWritableDatabase();

            //cursor point to a location in your results
            Cursor c = db.rawQuery(query, null);
            //move to the first row in your results
            c.moveToLast();
            while (c.isLast()) {



                int id = c.getInt(COLUMN_ID_NO);
                int date = c.getInt(COLUMN_BILL_DATE_NO);
                int amount = c.getInt(COLUMN_BILL_AMOUNT_NO);
                int percent = c.getInt(COLUMN_TIP_PERCENT_NO);

                Tip tip = new Tip(id, date, amount, percent);
                tips.add(tip);
                //entries++;
                //c.moveToNext();

            }
            db.close();
        }

        return tips;


    }
}