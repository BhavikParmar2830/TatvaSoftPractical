package com.tatvasoftpractical.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.tatvasoftpractical.base.BaseViewModel
import com.tatvasoftpractical.model.BaseModel
import com.tatvasoftpractical.model.UserDetailsModel
import com.tatvasoftpractical.retrofit.RetrofitClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val TAG = "MainActivityViewModel"

    var userListSize : MutableLiveData<Int> = MutableLiveData(0)
    var userList: MutableLiveData<ArrayList<UserDetailsModel>> =
        MutableLiveData(ArrayList<UserDetailsModel>())
    var loading: MutableLiveData<Boolean> = MutableLiveData(true)
    var offset: Int = 0
    var hasMore: Boolean = true


    fun getUserDetailsCall() {

        val call: Call<BaseModel> = RetrofitClass.getClient.getUserDetails(offset, 10)

        call.enqueue(object : Callback<BaseModel> {
            override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                if (response.isSuccessful) {
                    if (response.body()!!.status!!) {
                        if (response.body()!!.data!!.users!!.size > 0) {

                            if (userList.value!!.size > 0) {
                                userList.value!!.addAll(response.body()!!.data!!.users!!)
                            }else{
                                userList.value = response.body()!!.data!!.users!!
                            }

                            hasMore = response.body()!!.data!!.hasMode!!
                            userListSize.value = userList.value!!.size
                        }
                    } else {
                        Log.e(TAG, response.message())
                    }
                }
            }

            override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                Log.e(TAG, t.message!!)
            }
        })
    }
}