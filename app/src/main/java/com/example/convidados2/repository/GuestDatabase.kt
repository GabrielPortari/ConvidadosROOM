package com.example.convidados2.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.convidados2.constants.MyConstants

/* CRIAR O BANCO DE DADOS // ATUALIZAR O BANCO DE DADOS */
class GuestDatabase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    companion object{
        private const val NAME = "guestdb"
        private const val VERSION = 1

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE "+ MyConstants.DATABASE.TABLE_NAME +"(" +
                MyConstants.DATABASE.COLUMNS.ID + " integer primary key autoincrement, " +
                MyConstants.DATABASE.COLUMNS.NAME + " text, " +
                MyConstants.DATABASE.COLUMNS.PRESENCE + " integer);"

        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}