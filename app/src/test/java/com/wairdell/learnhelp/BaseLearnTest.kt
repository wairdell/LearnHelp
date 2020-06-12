package com.wairdell.learnhelp

import org.junit.Test

class BaseLearnTest {

    @Test
    fun testFor() {
        var start = 0
        var end = 10


        //输出结果0 1 2 3 4 5 6 7 8 9 10
        for (i in start..end) {
            print("$i ")
        }
        println()


        //输出结果10 9 8 7 6 5 4 3 2 1 0
        for (i in end downTo start) {
            print("$i ")
        }
        println()

        //输出结果0 2 4 6 8 10
        for (i in start..end step 2) {
            print("$i ")
        }
        println()

        //输出结果0 1 2 3 4 5 6 7 8 9
        for (i in start until end) {
            print("$i ")
        }
        println()
    }
}