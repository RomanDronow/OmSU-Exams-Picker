package com.omsu.examshelper.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${ExamsContract.ExamsEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${ExamsContract.ExamsEntry.COLUMN_NAME} TEXT," +
            "${ExamsContract.ExamsEntry.COLUMN_DEPARTMENT_NAME} TEXT," +
            "${ExamsContract.ExamsEntry.COLUMN_POSITION_TYPE} TEXT," +
            "${ExamsContract.ExamsEntry.COLUMN_STUDY_FORM} TEXT," +
            "${ExamsContract.ExamsEntry.COLUMN_PLAN_NAB} INTEGER," +
            "${ExamsContract.ExamsEntry.COLUMN_PREDM} TEXT," +
            "${ExamsContract.ExamsEntry.COLUMN_U_VUZ_SHORT} TEXT)"

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${ExamsContract.ExamsEntry.TABLE_NAME}"

class ExamsSqlHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "ExamsDatabase.db"
    }
}

