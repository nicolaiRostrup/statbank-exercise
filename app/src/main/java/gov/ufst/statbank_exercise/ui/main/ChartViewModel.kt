package gov.ufst.statbank_exercise.ui.main


import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.charts.Mekko
import com.anychart.charts.Pie
import com.anychart.core.SeparateChart
import gov.ufst.statbank_exercise.data.model.*
import gov.ufst.statbank_exercise.ui.helpers.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.ceil


class ChartViewModel(
    val repository: Repository,
    val userRequest: UserRequest) : ViewModel(), Observable {


    private var client: ApiCalls = RetrofitClient.create()

    lateinit var chosenChartType: ChartType

    val chartReady = MutableLiveData<Event<SeparateChart>>()

    val fetchingData = MutableLiveData<Boolean>()


    fun getData() {
        fetchingData.value = true

        client.getTwinData(manufactureTwinDataRequest()).enqueue(object : Callback<TwinData> {
            override fun onResponse(call: Call<TwinData>, response: Response<TwinData>) {
                if (response.isSuccessful) {
                    Log.i("server response: ", " successful ${response.body()}")
                    fetchingData.value = false

                    response.body()?.let {
                        parseDeliveryData(it)
                        executeUserRequest()
                    }

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

    fun executeUserRequest() {
        //var chartDataEntries: MutableList<DataEntry> = ArrayList()
        val thisChart: SeparateChart = when (chosenChartType) {
            ChartType.PIE -> preparePieChart(userRequest)
            ChartType.LINE -> prepareLineChart(userRequest)
            ChartType.MEKKO -> prepareMekkoChart(userRequest)
        }
        chartReady.postValue(Event(thisChart))
    }

    private fun preparePieChart(userRequest: UserRequest): Pie {
        val dataList: MutableList<ServerDataObject> = mutableListOf()
        repository.allDeliveryData.forEach{
            if((it.year.toInt() in (userRequest.fromYear..userRequest.untilYear)) && it.deliveryType == userRequest.deliveryType){
                dataList.add(it)
            }
        }
        val reduceFactor: Int = (ceil(dataList.size.toDouble() / 3.0)).toInt()
        if (dataList.size > 5) {
            dataList.filterIndexed { index, _ -> index % reduceFactor != 0 }
        }
        val chartDataList: MutableList<DataEntry> = ArrayList()
        for (item in dataList) {
            chartDataList.add(ValueDataEntry(item.year, item.count))
        }
        val pie = AnyChart.pie()
        pie.data(chartDataList)
        return pie
    }

    private fun prepareLineChart(userRequest: UserRequest): Cartesian {
        return AnyChart.line()
    }

    private fun prepareMekkoChart(userRequest: UserRequest): Mekko {
        return AnyChart.mekko()
    }

    private fun manufactureTwinDataRequest(): DataRequest {

        val yearList: MutableList<String> = mutableListOf()

        for(year in 1850..2018){
            yearList.add(year.toString())
        }

        val foedType = Variable(
            code = "FØDTYPE", placement = "Stub", values = listOf("09", "10", "11", "12")
        )
        val tid = Variable(
            code = "TID", placement = "Stub", values = yearList
        )
        val dataRequest = DataRequest(
            lang = "en", table = "FOD8", format = "JSONSTAT", variables = listOf(foedType, tid)

        )
        return dataRequest
    }

    private fun parseDeliveryData(twinData: TwinData) {

        val twinValueList = twinData.dataset.value as MutableList
        val yearRange = twinValueList.size
        //Delivery count list size is always divisible by four, since it is derived from a (4 * numbers of years) calculation
        if (yearRange % 4 != 0) throw Exception("List of delivery types should allways be divisibel by 4")

        val singleDeliveries: List<Int> = twinValueList.take(yearRange)
        twinValueList.drop(yearRange)
        val twinDeliveries: List<Int> = twinValueList.take(yearRange)
        twinValueList.drop(yearRange)
        val tripletDeliveries: List<Int> = twinValueList.take(yearRange)
        twinValueList.drop(yearRange)
        val quadDeliveries: List<Int> = twinValueList

        val resultList: MutableList<ServerDataObject> = mutableListOf()

        fillResultList(singleDeliveries, resultList, DeliveryType.SINGLE, yearRange)
        fillResultList(twinDeliveries, resultList, DeliveryType.TWIN, yearRange)
        fillResultList(tripletDeliveries, resultList, DeliveryType.TRIPLET, yearRange)
        fillResultList(quadDeliveries, resultList, DeliveryType.QUADRUPLET, yearRange)
        repository.allDeliveryData = resultList
        repository.setUpdateTime()
    }

    private fun fillResultList(sourceList: List<Int>,
                               resultList: MutableList<ServerDataObject>,
                               deliveryType: DeliveryType,
                               yearRange: Int) : MutableList<ServerDataObject>{
        for (i in 0 until yearRange) {
            val year = (i + DefaultSettings.minimumYear).toString()
            val count = sourceList[i]
            resultList.add(ServerDataObject(year = year, count = count, deliveryType = deliveryType))
        }
        return resultList
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}
