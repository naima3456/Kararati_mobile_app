package com.example.project

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context, "MyDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE ARRETS (NUMAR INTEGER PRIMARY KEY, ChambreAr TEXT, ReferenceAr TEXT, AnneeRefAr INTEGER, numRef TEXT, numPageRef TEXT, DateAr TEXT, PartiesAr TEXT, PrincipeAr TEXT, DecisionAr TEXT, DecisionOperAR TEXT, CompositionAr TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        db?.execSQL("drop Table if exists ARRETS")
    }

    fun getdata(): Cursor? {
        val db = this.writableDatabase
        return db.rawQuery("Select * from ARRETS", null)
    }


}