package com.wairdell.learnhelp.jetpack.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.PagedList.Config
import androidx.paging.toLiveData

class TestViewModel: ViewModel() {

    private val dataSourceFactory = TestDataSourceFactory()

    private val config = Config.Builder().setInitialLoadSizeHint(20).setPageSize(20).setEnablePlaceholders(true).build()

    val testList: LiveData<PagedList<TestData>> = dataSourceFactory.toLiveData(config, initialLoadKey = 0)

    fun invalidateDataSource() = dataSourceFactory.sourceLiveData.value?.invalidate()

}