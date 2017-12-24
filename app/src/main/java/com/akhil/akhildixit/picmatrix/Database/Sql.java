package com.akhil.akhildixit.picmatrix.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by Akhil Dixit on 12/23/2017.
 */

public class Sql extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="picmatrix.db";
    public static final int DATABASE_VERSION=1;
    SQLiteDatabase database;
    String tableName="MASTER_TABLE";
    String fields[]=new String[]{"INPUT","SOLUTION","ALTERNATIVE","PLOT"};
    String query_branch_table="CREATE TABLE IF NOT EXISTS MASTER_TABLE(INPUT TEXT,SOLUTION TEXT,ALTERNATIVE TEXT,PLOT BLOB)";


    public Sql(Context context) {
        super(context, Environment.getExternalStorageDirectory()+ File.separator+"PicMatrix"+File.separator+ DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
    }

    public Sql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query_branch_table);

    }
    public void addDataToTable()
    {
        byte array[]=getBitmapAsByteArray(Setter.bitmap);
        ContentValues contentValues=new ContentValues();
        contentValues.put(fields[0],Setter.input);
        contentValues.put(fields[1],Setter.solution);
        contentValues.put(fields[2],Setter.alternativeform);
        contentValues.put(fields[3],array);
        database.insert(tableName,null,contentValues);
        Log.e("Works","w..."+Setter.input);

    }
    public ArrayList<Setter> getDataFromTable()
    {
     ArrayList<Setter> arrayList=new ArrayList<>();
        Cursor cursor=database.query(tableName,fields,null,null,null,null,null);
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext())
            {
                Setter setter=new Setter();
                setter.inputnon=cursor.getString(0);
                setter.solutionnon=cursor.getString(1);
                setter.alternativeformnon=cursor.getString(2);
                byte[] arr=cursor.getBlob(3);
                setter.bitmapnon= BitmapFactory.decodeByteArray(arr,0,arr.length);
                arrayList.add(setter);
            }
        }


       return arrayList;
    }
    public byte[] getBitmapAsByteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
