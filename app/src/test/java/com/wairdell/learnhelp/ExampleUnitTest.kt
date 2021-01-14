package com.wairdell.learnhelp

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    inline fun runSome(params1: String, run: () -> Unit) {
        print(params1)
        run()
        var count = 0
        for (i in 0..10) {
            count += i
        }
        print(count)
    }

    fun execRun() {
        runSome("test1") {
            for (j in 0..9) {
                for (i in 0..9) {
                    if(i == 1 && j == 3) {
                        print(i)
                        return@runSome
                    }
                }
            }

        }
        print("execRun")
    }

    fun execRun2() {
        runSome("test2") {
            print("test2")
        }
    }


    inline fun runSome2(run: () -> Unit, run2: (String) -> Char) {
        run()
        run2.invoke("xxxxxxx")
    }

    fun execRun3(): Boolean {
        runSome2({
            print("1111")
        }) { string1 ->
            for (c in string1) {
                if(c == 'x') {
                    return@runSome2 'c'
                }
            }
            return@runSome2 'x'
        }
        return false
    }

    inline fun inlineFun1() {
        print("inlineFun1")
        print("inlineFun1111")
    }

    inline fun inlineFun2() {
        inlineFun1()
        print("inlineFun2")
        print("inlineFun2222")
    }

    inline fun inlineFun3() {
        inlineFun2()
        print("inlineFun3")
        print("inlineFun3333")
    }

    inline fun inlineFun3_1(num: Int) {
        if(num == 2) {
            return
        }
        inlineFun2()
        print("inlineFun3_1")
        print("inlineFun3333_1")
    }

    fun callFun(num: Int) {
        inlineFun3_1(num)
        print("111111111")
    }

    inline fun test1(num: Int) {
        print("test1")
        if(num < 0) {
            return
        }
        test3(num - 1)
    }

    fun test2(num: Int) {
        print("test2")
        test1(num)
    }

    inline fun test3(num: Int) {
        print("test3")
        if(num < 0) {
            return
        }
        test1(num - 2)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


}
