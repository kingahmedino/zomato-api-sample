package com.app.zomatoapisample.repositories

import com.app.zomatoapisample.interfaces.DataRepoResponseListener

class DataRepository {
    var mDataRepoResponseListener: DataRepoResponseListener? = null

    fun setDataRepoResponseListener(dataRepoResponseListener: DataRepoResponseListener){
        mDataRepoResponseListener = dataRepoResponseListener
    }
}