package gov.ufst.statbank_exercise.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.charts.Pie
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gov.ufst.statbank_exercise.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel : ViewModel() {

    private var client: ApiCalls = RetrofitClient.create()

    val birthData = MutableLiveData<Event<TwinData>>()

    val livePie = MutableLiveData<Pie>()


    fun getData() {


        client.getTwinData(getTwinDataRequest()).enqueue(object : Callback<TwinData> {
            override fun onResponse(call: Call<TwinData>, response: Response<TwinData>) {
                if (response.isSuccessful) {
                    Log.i("server response: ", " successful ${response.body()}")

                    handleDataByEvent(response.body())


                } else {
                    Log.i("server response: ", " unsuccessful ${response.body()}")

                }
            }

            override fun onFailure(call: Call<TwinData>, t: Throwable) {
                t.printStackTrace()
                Log.e("server request error", "")
            }
        })


    }

    private fun handleDataByEvent(twinData: TwinData?){

                    Log.i("posted value 1: ", twinData.toString())
                    if (twinData != null) {
                        birthData.postValue(Event(twinData))

                    }

    }


    private fun getTwinDataRequest(): DataRequest {

        val foedType = Variable(
            code = "FØDTYPE", placement = "Stub", values = listOf("10")
        )
        val tid = Variable(
            code = "TID", placement = "Stub", values = listOf("1850", "1851")
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


}
