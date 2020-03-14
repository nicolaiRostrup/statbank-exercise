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
                createChart(it, chartType)

            }
        })

    }



    override fun onStart() {
        super.onStart()

        viewModel.getData(sharedViewModel.currentUserRequest)


    }

    private fun createChart(twinData: TwinData, chartType: ChartType) {

        val dataList: MutableList<DataEntry> = ArrayList()

        val thisLabelMap = twinData.dataset.dimension.tid.category.indexList
        val reversedLabelMap = thisLabelMap.entries.associateBy({ it.value }) { it.key }

        for ((index, value) in twinData.dataset.value.withIndex()) {

            val thisLabel: String? = reversedLabelMap[index] ?: ""

            dataList.add(ValueDataEntry(thisLabel, value))
        }

        val chart = when(chartType) {
            ChartType.PIE -> {
                AnyChart.pie().apply {
                    this.data(dataList)
                }
            }
            ChartType.LINE -> {
                AnyChart.line().apply {
                    this.data(dataList)
                }
            }
            ChartType.MEKKO -> {
                AnyChart.mekko().apply{
                    this.data(dataList)
                }
            }

        }

        val chartView = binding.root.chart_view
        chartView.setChart(chart)

    }

//    private fun createLineChart(twinData: TwinData) {
//
//        val line = AnyChart.line()
//
//        val data: MutableList<DataEntry> = ArrayList()
//
//        val thisLabelMap = twinData.dataset.dimension.tid.category.indexList
//        val reversedLabelMap = thisLabelMap.entries.associateBy({ it.value }) { it.key }
//
//        for ((index, value) in twinData.dataset.value.withIndex()) {
//
//            val thisLabel: String? = reversedLabelMap[index] ?: ""
//
//            data.add(ValueDataEntry(thisLabel, value))
//        }
//
//        line.data(data)
//
//        val chart = binding.root.line_chart_view
//        chart.setChart(line)
//
//    }


    companion object {
        fun newInstance(chartType: ChartType) = ChartFragment(chartType)
    }


    enum class ChartType {
        LINE, PIE, MEKKO
    }
}