package com.omsu.examshelper.parser

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.omsu.examshelper.R
import com.omsu.examshelper.db.ExamsContract
import com.omsu.examshelper.db.ExamsSqlHelper
import java.io.BufferedReader
import java.io.InputStreamReader

object DatabaseController {
    fun parseAndFill(context: Context): Unit {
        val dbHelper = ExamsSqlHelper(context)
        val db = dbHelper.writableDatabase
        val br = BufferedReader(InputStreamReader(context.resources.openRawResource(R.raw.data)))
        br.readLine()
        var s = br.readLine()
        while (s != null) {
            val ss = s.replace("\";\"", "\"|\"").replace("\"", "").split("|")
            val values = ContentValues().apply {
                put(ExamsContract.ExamsEntry.COLUMN_DEPARTMENT_NAME, ss[2])
                put(ExamsContract.ExamsEntry.COLUMN_NAME, ss[3])
                put(ExamsContract.ExamsEntry.COLUMN_POSITION_TYPE, ss[4])
                put(ExamsContract.ExamsEntry.COLUMN_STUDY_FORM, ss[5])
                put(ExamsContract.ExamsEntry.COLUMN_PLAN_NAB, ss[6])
                put(ExamsContract.ExamsEntry.COLUMN_PREDM, ss[7])
                put(ExamsContract.ExamsEntry.COLUMN_U_VUZ_SHORT, ss[12])
            }
            s = br.readLine()
            val newRowId = db?.insert(ExamsContract.ExamsEntry.TABLE_NAME, null, values)
            Log.d(
                "Database",
                "Inserted " + ss[3] + " : " + ss[7] + " : " + ss[5] + " with id = " + newRowId
            )
        }
        db.execSQL("UPDATE EXAMS SET predm = \"Иностранный язык\" WHERE predm IN(\"Английский язык\",\"Немецкий язык\",\"Французский язык\")")
        db.close()
        dbHelper.close()
    }

    fun dropDatabase(context: Context) {
        val dbHelper = ExamsSqlHelper(context)
        val db = dbHelper.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS EXAMS");
    }

    fun selectQuery(context: Context, predms: ArrayList<String>): ArrayList<String>? {
        val dbHelper = ExamsSqlHelper(context)
        val db = dbHelper.readableDatabase
        val examsQuantity = predms.size
        if (examsQuantity < 3) {
            return null
        }
        val flag = predms.contains("Испытание")
        if(flag){
            predms.remove("Испытание")
        }
        var predmsString = "(" + predms.joinToString(separator = "\",\"", prefix = "\"", postfix = "\"") + ")"
        if(flag){
            predmsString += " OR predm LIKE(\"%испытание%\")"
        }
        val query = """
            SELECT e1.name, e1.department_name, e1.position_type, e1.study_form, e1.plan_nab, e1.u_vuz_short, e1.predm from EXAMS e1,

            (SELECT name, department_name, position_type, study_form, plan_nab, u_vuz_short, COUNT(predm) as c
            from EXAMS
            where predm in ${predmsString}
            group by name, department_name, position_type, study_form, plan_nab, u_vuz_short

            INTERSECT

            SELECT name, department_name, position_type, study_form, plan_nab, u_vuz_short, COUNT(predm) as c
            from EXAMS
            group by name, department_name, position_type, study_form, plan_nab, u_vuz_short) e2
            WHERE e1.NAME = e2.name
            AND e1.department_name = e2.department_name
            AND e1.study_form = e2.study_form
            AND e1.plan_nab = e2.plan_nab
            AND e1.u_vuz_short = e2.u_vuz_short
        """.trimIndent()
        Log.d("Grechka", query)
        val cur = db.rawQuery(query, null)
        val list = ArrayList<String>()
        while (cur.moveToNext()) {
            val resultEntry = ArrayList<String>()
            for (i in 0..cur.columnCount - 1) {
                resultEntry.add(cur.getString(i).trim())
            }
            list.add(resultEntry.joinToString(separator = "|"));
            Log.d("Grechka", "Selected: " + list.last())
        }
        cur.close()
        db.close()
        dbHelper.close()
        Log.d("Grechka", list.size.toString())
        return list
    }
}