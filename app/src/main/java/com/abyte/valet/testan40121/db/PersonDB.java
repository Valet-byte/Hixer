package com.abyte.valet.testan40121.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.abyte.valet.testan40121.model.person.Person;

public class PersonDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "persons.db";

    public static final String DB_CREATED = "CREATE TABLE " + Contracts.PersonContract.TABLE_NAME +
            " (" + Contracts.PersonContract.COLUMN_ID +  " INTEGER PRIMARY KEY," +
            Contracts.PersonContract.COLUMN_NAME + " TEXT NOT NULL," +
            Contracts.PersonContract.COLUMN_PASSWORD + " TEXT NOT NULL)";

    public static final String DROP_TABLE = "DROP TABLE " + Contracts.PersonContract.TABLE_NAME;


    public PersonDB(@Nullable Context context) {
        super(context, DB_NAME, null, Contracts.PersonContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DB_CREATED);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void addPerson(Person person){

        if (person != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Contracts.PersonContract.COLUMN_NAME, person.getName());
            cv.put(Contracts.PersonContract.COLUMN_PASSWORD, person.getPassword());
            cv.put(Contracts.PersonContract.COLUMN_ID, person.getId());

            db.delete(Contracts.PersonContract.TABLE_NAME, null, null);
            db.insert(Contracts.PersonContract.TABLE_NAME, null, cv);
        }
    }

    public Person getPerson(){

        SQLiteDatabase db = this.getReadableDatabase();
        Person p = null;

        Cursor cursor = db.query(Contracts.PersonContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            int numId = cursor.getColumnIndex(Contracts.PersonContract.COLUMN_ID);
            int numName = cursor.getColumnIndex(Contracts.PersonContract.COLUMN_NAME);
            int numPassword = cursor.getColumnIndex(Contracts.PersonContract.COLUMN_PASSWORD);

            p = new Person(cursor.getLong(numId), cursor.getString(numName), cursor.getString(numPassword));
            cursor.close();
        }
        return p;
    }

    public void deletedPerson(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Contracts.PersonContract.TABLE_NAME, null, null);
    }
}

