package com.example.a12tomasik;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BazaDanych extends SQLiteOpenHelper {
    Context context;


    public BazaDanych(Context context) {
        super(context, "MojeKontakty.db", null, 1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table telefony");
        sqLiteDatabase.execSQL(
                "create table telefony(lp integer primary key autoincrement,"+
                        "imie text," +
                        "nazwisko text," +
                        "telefon text)"
        );



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void dodajKontakt(String imie, String nazw, String telefon)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("imie", imie);
        wartosci.put("nazwisko", nazw);
        wartosci.put("telefon", telefon);
        db.insertOrThrow("telefony", null, wartosci);
    }


    public void ZmienKontakt(int LP, String telefon)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("telefon", telefon);
        db.update("telefony", cv,"lp=?",new String[]{ String.valueOf(LP)});
    }


    public Cursor select(String selection, String[] selectionsArgs)
    {
        String[] kolumny = {"lp", "imie", "nazwisko", "telefon"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor = db.query("telefony", kolumny,selection, selectionsArgs,null,null,null);
        //Cursor kursor = db.rawQuery("select * from telefony where lp=?", new String[]{"3"});
        //Cursor kursor = db.rawQuery("select * from telefony", null);
        return kursor;
    }

    public void UsunKontakt(int LP)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("telefony", "lp=?", new String[]{String.valueOf(LP)});
    }


}
