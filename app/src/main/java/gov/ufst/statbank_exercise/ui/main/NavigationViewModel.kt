package gov.ufst.statbank_exercise.ui.main

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gov.ufst.statbank_exercise.data.model.*
import gov.ufst.statbank_exercise.ui.helpers.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NavigationViewModel : ViewModel(), Observable {

    private var client: ApiCalls = RetrofitClient.create()

    val birthDataTwins = MutableLiveData<Event<TwinData>>()

    val seekBarValue = MutableLiveData<Int>()

    val fetchingData= MutableLiveData<Boolean>()

    val showLineChart = MutableLiveData<Event<Int>>()

    val serverInfoTwins = MutableLiveData<TwinData>()


    fun getData() {

        fetchingData.value = true

        client.getTwinData(getTwinDataRequest()).enqueue(object : Callback<TwinData> {
            override fun onResponse(call: Call<TwinData>, response: Response<TwinData>) {
                if (response.isSuccessful) {
                    Log.i("server response: ", " successful ${response.body()}")
                    fetchingData.value = false
                    serverInfoTwins.postValue(response.body())

                } else {
                    Log.i("server response: ", " unsuccessful ${response.body()}")
                    fetchingData.value = false
                }
            }

            override fun onFailure(call: Call<TwinData>, t: Throwable) {
                t.printStackTrace()
                fetchingData.value = false
                Log.e("server request error", "")
            }
        })


    }


//    private fun handleDataByEvent(twinData: TwinData?) {
//        if (twinData != null) {
//            birthDataTwins.postValue(Event(twinData))
//            serverInfoTwins.postValue(twinData)
//        }
//
//    }


    private fun getTwinDataRequest(): DataRequest {

        val foedType = Variable(
            code = "FØDTYPE", placement = "Stub", values = listOf("10")
        )
        val tid = Variable(
            code = "TID", placement = "Stub", values = listOf("1850", "1851", "1852", "1853")
        )
        val dataRequest = DataRequest(
            lang = "en", table = "FOD8", format = "JSONSTAT", variables = listOf(foedType, tid)

        )


        val builder = GsonBuilder()
        val gson: Gson = builder.create()
        val jsonString = gson.toJson(dataRequest)

        Log.i("data request", dataRequest.toString())
        Log.i("json rep", jsonString)


        return dataRequest

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}
