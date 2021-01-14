package com.wairdell.learnhelp.jetpack.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class TestDataSourceFactory: DataSource.Factory<Int, TestData>() {

    val sourceLiveData = MutableLiveData<TestDataSource>()
    var latestSource: TestDataSource? = null

    override fun create(): DataSource<Int, TestData> {
        latestSource = TestDataSource()
        sourceLiveData.postValue(latestSource)
        return latestSource!!
    }
}