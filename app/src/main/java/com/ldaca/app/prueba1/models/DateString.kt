package com.ldaca.app.prueba1.models

class DateString {
    var day = "7"
    var month = "1"
    var year = "1990"

    fun setDate(mDay: Int, mMonth: Int, mYear: Int) {
        day = mDay.toString()
        month = mMonth.toString()
        year = mYear.toString()
    }

    val displayDate: String?
        get() {
            if (day.length < 2) day = "0$day"
            if (month.length < 2) month = "0$month"
            return "$day/$month/$year"
        }
}