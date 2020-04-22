package com.wairdell.learnhelp.bean

class Student {

    var name: String? = null
    var age: Int? = null
    var sno: String? = null

    fun eat() {
        print("$name 在吃饭")
    }

    fun run() {
        print("$name 在跑步")
    }

    override fun toString(): String {
        return "[name = $name, age = $age, sno = $sno]"
    }
}