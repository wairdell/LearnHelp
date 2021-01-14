package com.wairdell.learnhelp.jetpack.paging

import androidx.paging.ItemKeyedDataSource
import java.util.concurrent.Executors
import kotlin.random.Random

class TestDataSource : ItemKeyedDataSource<Int, TestData>() {

    private val executors = Executors.newSingleThreadExecutor()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<TestData>
    ) {
        executors.execute {
            val loadData = arrayListOf<TestData>()
            for(i in 0 until params.requestedLoadSize) {
                loadData.add(TestData("${params.requestedInitialKey}-$i", params.requestedInitialKey))
                Thread.sleep(Random.Default.nextLong(20, 100))
            }
            callback.onResult(loadData, 0, loadData.size)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<TestData>) {
        executors.execute {
            val loadData = arrayListOf<TestData>()
            for(i in 0 until params.requestedLoadSize) {
                loadData.add(TestData("${params.key}-$i", params.key))
                Thread.sleep(Random.Default.nextLong(20, 100))
            }
            callback.onResult(loadData)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<TestData>) {
    }

    override fun getKey(item: TestData): Int {
        return item.page?.let { it + 1 } ?: 0
    }
}