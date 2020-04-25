package com.omsu.examshelper.db

import android.provider.BaseColumns

object ExamsContract {
    object ExamsEntry : BaseColumns {
        const val TABLE_NAME = "EXAMS"
        const val COLUMN_DEPARTMENT_NAME = "DEPARTMENT_NAME" // 2
        const val COLUMN_NAME = "NAME" // 3
        const val COLUMN_POSITION_TYPE = "POSITION_TYPE" // 4
        const val COLUMN_STUDY_FORM = "STUDY_FORM" // 5
        const val COLUMN_PLAN_NAB = "PLAN_NAB" // 6
        const val COLUMN_PREDM = "PREDM" // 7
        const val COLUMN_U_VUZ_SHORT = "U_VUZ_SHORT" // 12
    }
}