package com.omsu.examshelper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_result.*


class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val result = intent.getStringArrayListExtra("Result")
        result?.sort()
        val listForView = ArrayList<ArrayList<String>>()
        val mapWithSetAsValue = HashMap<ArrayList<String>, HashSet<String>>()
        if (result != null) {
            for (item in result) {
                val temp = item.split("|") as ArrayList<String>
                editSomeStuff(temp)
                val exam = temp[5]
                temp.removeAt(5)
                if (!mapWithSetAsValue.containsKey(temp)) {
                    mapWithSetAsValue[temp] = HashSet()
                }
                mapWithSetAsValue[temp]?.add(exam)
            }
        }
        for (item in mapWithSetAsValue) {
            val exams = ArrayList<String>(item.value)
            val examsString = exams.joinToString(separator = ", ")
            val toList = item.key
            toList.add(examsString)
            listForView.add(toList)
        }
        val recyclerView: RecyclerView = resultRecyclerView
        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        recyclerView.addItemDecoration(decoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListExamsAdapter(listForView)
    }

    private fun editSomeStuff(temp: ArrayList<String>) {
        if (temp[2].equals("ПЛАТ")) {
            temp[2] = "Платное"
        } else if (temp[2].equals("БЮДЖ")) {
            temp[2] = "Бюджет"
        }
        temp[2] = temp[2] + ", " + temp[4] + (" мест")
        temp[3] = temp[3].plus(" форма обучения")
        if (temp[5].equals("БАК")) {
            temp[5] = "Бакалавриат"
        }
        if (temp[5].equals("СПЕЦ")) {
            temp[5] = "Специалитет"
        }
        temp.removeAt(4)
    }

    class ListExamsAdapter(private val items: List<List<String>>) :
        RecyclerView.Adapter<ListExamsAdapter.ViewHolder>() {

        override fun getItemCount() = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.exam_list_layout, parent, false)
            return ViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: ViewHolder, i: Int) {
            holder.textViewName?.text = items[i][0]
            holder.textViewDepartment?.text = items[i][1]
            holder.textViewPosition?.text = items[i][2]
            holder.textViewStudyForm?.text = items[i][3]
            holder.textViewStudyLevel?.text = items[i][4]
            holder.textViewExams?.text = items[i][5]
        }

        class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
            var textViewName: TextView? = null
            var textViewDepartment: TextView? = null
            var textViewPosition: TextView? = null
            var textViewStudyForm: TextView? = null
            var textViewStudyLevel: TextView? = null
            var textViewExams: TextView? = null

            init {
                textViewName = itemView?.findViewById(R.id.list_item_name)
                textViewExams = itemView?.findViewById(R.id.list_item_exams)
                textViewDepartment = itemView?.findViewById(R.id.list_item_department)
                textViewPosition = itemView?.findViewById(R.id.list_item_pos_type)
                textViewStudyForm = itemView?.findViewById(R.id.list_item_study_form)
                textViewStudyLevel = itemView?.findViewById(R.id.list_item_study_level)
            }
        }
    }
}
