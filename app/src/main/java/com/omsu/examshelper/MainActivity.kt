package com.omsu.examshelper

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView

import com.omsu.examshelper.FirstRunChecker.checkFirstRun
import com.omsu.examshelper.parser.DatabaseController.selectQuery

class MainActivity : AppCompatActivity() {
    val map = HashMap<CheckBox, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        checkFirstRun(applicationContext)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCheckboxMap()
    }

    fun onNextClick(view: View) {
        val predms = ArrayList<String>()
        for (check in map) {
            if (check.key.isChecked) {
                Log.d("Grechka", check.value)
                predms.add(check.value)
            }
        }
        val result = selectQuery(applicationContext, predms)

        if (result != null && result.size > 0) {
            for (string in result) {
                Log.d("Grechka", string)
            }
            val intent = Intent(this, ResultActivity::class.java)
            intent.putStringArrayListExtra("Result", result)
            startActivity(intent)
            result.clear()
        } else {
            findViewById<TextView>(R.id.textView).visibility = TextView.VISIBLE
        }
    }

    private fun initCheckboxMap() {
        map[findViewById(R.id.checkbox_bio)] = "Биология"
        map[findViewById(R.id.checkbox_chem)] = "Химия"
        map[findViewById(R.id.checkbox_comps)] = "Информатика и ИКТ"
        map[findViewById(R.id.checkbox_deu)] = "Иностранный язык"
        map[findViewById(R.id.checkbox_eng)] = "Иностранный язык"
        map[findViewById(R.id.checkbox_french)] = "Иностранный язык"
        map[findViewById(R.id.checkbox_geo)] = "География"
        map[findViewById(R.id.checkbox_hist)] = "История"
        map[findViewById(R.id.checkbox_liter)] = "Литература"
        map[findViewById(R.id.checkbox_math)] = "Математика"
        map[findViewById(R.id.checkbox_phys)] = "Физика"
        map[findViewById(R.id.checkbox_rus)] = "Русский язык"
        map[findViewById(R.id.checkbox_soc)] = "Обществознание"
        map[findViewById(R.id.checkbox_vstup)] = "Испытание"
    }
}
