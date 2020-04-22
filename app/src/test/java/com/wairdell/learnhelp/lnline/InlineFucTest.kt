package com.wairdell.learnhelp.lnline

import com.wairdell.learnhelp.bean.Student
import org.junit.Before
import org.junit.Test

class InlineFucTest {

    var student :Student? = null

    var student2 :Student? = null

    @Test
    fun normalTest() {
        student?.name = "张三"
        student?.age = 16
        student?.sno = "202004220311"
        student?.run()
        student?.eat()
        student2?.name = "李四"
        student2?.age = 17
    }

    @Test
    fun letTest() {
        student.let {
            it?.name = "张三"
            it?.age = 16
            it?.sno = "202004220311"
            it?.run()
            it?.eat()
        }
        student2.let {
            it?.name = "李四"
            it?.age = 17
        }

        var result = student?.let {
            it.name = "张三"
            it.age = 16
            it.sno = "202004220311"
            it.run()
            it.eat()
        }
        println("student = $student, result = $result")
    }

    @Test
    fun withTest() {
        var result = with(student) {
            this?.name = "with"
            this?.age = 16
            "withResult"
        }
        with(student!!) {
            name = "李四"
            age = 17
        }
        println("student = $student, result = $result")
    }

    @Test
    fun runTest() {
        student?.run {
            name = "张三"
            age = 17
            runTest()
        }
    }

    @Test
    fun applyTest() {
        var applyResult = student?.apply {
            name = "张三"
            age = 17
            "result"
        }
        println("return = $applyResult")

        var runResult = student?.run {
            name = "张三"
            age = 17
            "result"
        }
        print("return = $runResult")
    }

    @Test
    fun alsoTest() {
        var alsoResult = student?.also {
            it.name = "张三"
            it.age = 17
            "aaaa"
        }
        println("return = $alsoResult") //打印"return = [name = 张三, age = 17, sno = null]"

        var letResult = student?.let {
            it.name = "张三"
            it.age = 17
            "aaaa"
        }
        println("return = $letResult") //打印"return = aaaa"
    }

    @Before
    fun initData() {
        student = Student()
        student2 = Student()
    }



}