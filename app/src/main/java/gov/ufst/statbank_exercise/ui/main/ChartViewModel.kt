package gov.ufst.statbank_exercise.ui.main


import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gov.ufst.statbank_exercise.data.model.*
import gov.ufst.statbank_exercise.ui.helpers.Event
import gov.ufst.statbank_exercise.ui.helpers.UserRequest
import gov.ufst.statbank_exercise.ui.helpers.toServerFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChartViewModel : ViewModel(), Observable {

    private var client: ApiCalls = RetrofitClient.create()

    val serverDataReady = MutableLiveData<Event<TwinData>>()

    val fetchingData= MutableLiveData<Boolean>()



    fun getData(userRequest: UserRequest) {

        fetchingData.value = true

        client.getTwinData(getTwinDataRequest(userRequest)).enqueue(object : Callback<TwinData> {
            override fun onResponse(call: Call<TwinData>, response: Response<TwinData>) {
                if (response.isSuccessful) {
                    Log.i("server response: ", " successful ${response.body()}")
                    fetchingData.value = false
                    handleDataByEvent(response.body())

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


    private fun handleDataByEvent(twinData: TwinData?) {
        if (twinData != null) {
            serverDataReady.postValue(Event(twinData))

        }

    }


    private fun getTwinDataRequest(userRequest: UserRequest): DataRequest {

        val deliveryType = userRequest.deliveryType.toServerFormat()
        val fromYear = userRequest.fromYear + 1850
        val untilYear = userRequest.untilYear + 1850
        val yearList = mutableListOf<String>()

        var includeYear = fromYear
        while (includeYear  <= untilYear){
            yearList.add(includeYear.toString())
            includeYear++
        }

        val foedType = Variable(
            code = "FØDTYPE", placement = "Stub", values = listOf(deliveryType)
        )
        val tid = Variable(
            code = "TID", placement = "Stub", values = yearList
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
