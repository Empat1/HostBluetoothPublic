package com.example.hostbluetooth

data class MessageBt(var type: Int, var sum: String = "null", var rrn: String = "null"){
    companion object{
        val separator = "|"
    }

    fun toExport(): String {
        return "$type $sum $rrn)"
    }



    fun strToMessage(s: String){
        val field = s.split(" ")

        type = field[0].toInt()
        sum = field[2]
        rrn = field[1]
    }

    fun toDisplay(): String{
        var typeStr = when(this.type){
            1->"Отплата"
            2->"Отмена"
            3->"Возврат"
            4->"Отмена возврата"
            else -> ""
        }

        if(sum != "null" && sum != ""){
            typeStr += " на сумму $sum руб"
        }

        if(rrn != "null" && rrn != ""){
            typeStr += " rnn $rrn"
        }

        return typeStr
    }

}