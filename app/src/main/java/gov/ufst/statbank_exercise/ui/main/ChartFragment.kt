package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.SharedViewModel
import gov.ufst.statbank_exercise.data.model.TwinData
import gov.ufst.statbank_exercise.databinding.ChartFragmentBinding
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.chart_fragment.view.*


class ChartFragment(private val chartType: ChartType) : Fragment() {

    private lateinit var binding: ChartFragmentBinding
    private lateinit var viewModel: ChartViewModel
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.chart_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settings_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commitNow()
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        binding.viewModel = viewModel

        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)
        }

        viewModel.serverDataReady.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                createChart(prepareData(it), chartType)

            }
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(sharedViewModel.currentUserRequest)

    }

    private fun prepareData(twinData: TwinData): List<ServerDataObject>{
        val thisLabelMap = twinData.dataset.dimension.tid.category.indexList
        val reversedLabelMap = thisLabelMap.entries.associateBy({ it.value }) { it.key }

        var serverDataList: MutableList<ServerDataObject> = ArrayList()

        for ((index, value) in twinData.dataset.value.withIndex()) {
            val year = reversedLabelMap[index]
            year?.let {
                serverDataList.add(ServerDataObject(year = year, value = value))
            }

        }
        if (chartType == ChartType.PIE) {
            serverDataList = reduceDataList(serverDataList)
        }
        return serverDataList

    }

    private fun reduceDataList(dataList: MutableList<ServerDataObject>): MutableList<ServerDataObject> {
        val reduceFactor: Int = when (dataList.size) {
            in 1..10 -> 2
            in 11..50 -> 4
            in 51..100 -> 12
            in 101..200 -> 25
            else -> 1
        }
        val resultList: MutableList<ServerDataObject> = ArrayList()

        for (item in dataList.withIndex()) {
            if (item.index % reduceFactor == 0) {
                resultList.add(item.value)
            }
        }
        return resultList

    }

    private fun createChart(serverDataList: List<ServerDataObject>, chartType: ChartType) {
        val finalDataList: MutableList<DataEntry> = ArrayList()

        for (item in serverDataList.withIndex()) {
                finalDataList.add(ValueDataEntry(serverDataList[item.index].year,
                                                 serverDataList[item.index].value))
        }

        val chart = when (chartType) {
            ChartType.PIE -> {
                AnyChart.pie().apply {
                    this.data(finalDataList)
                }
            }
            ChartType.LINE -> {
                AnyChart.line().apply {
                    this.data(finalDataList)
                }
            }
            ChartType.MEKKO -> {
                AnyChart.mekko().apply {
                    this.data(finalDataList)
                }
            }

        }

        val chartView = binding.root.chart_view
        chartView.setChart(chart)

    }


    companion object {
        fun newInstance(chartType: ChartType) = ChartFragment(chartType)
    }


    enum class ChartType {
        LINE, PIE, MEKKO
    }
}

data class ServerDataObject(
    val year: String,
    val value: Int
)
