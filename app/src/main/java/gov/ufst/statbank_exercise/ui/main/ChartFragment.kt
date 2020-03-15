package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.SeparateChart
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.data.model.Repository
import gov.ufst.statbank_exercise.data.model.TwinData
import gov.ufst.statbank_exercise.databinding.ChartFragmentBinding
import gov.ufst.statbank_exercise.ui.helpers.ChartType
import kotlinx.android.synthetic.main.chart_fragment.*
import kotlinx.android.synthetic.main.chart_fragment.view.*
import org.koin.android.viewmodel.compat.ViewModelCompat.getViewModel
import org.koin.android.viewmodel.ext.android.getViewModel


class ChartFragment(private val chartType: ChartType) : Fragment() {

    private lateinit var binding: ChartFragmentBinding
    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.chart_fragment, container, false)
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

        viewModel = getViewModel()
        viewModel.chosenChartType = chartType
        binding.viewModel = viewModel

        viewModel.chartReady.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                createChart(it)

            }
        })

    }

    private fun createChart(chart: SeparateChart) {
        val chartView = binding.root.chart_view
        chartView.setChart(chart)

    }

    override fun onStart() {
        super.onStart()

//        Toast.makeText(context, viewModel.repository.lastUpdate,
//                               Toast.LENGTH_SHORT).show()

        if (viewModel.repository.lastUpdate == null) viewModel.getData()
        else viewModel.executeUserRequest()

    }


//    private fun prepareMekkoChart(dataList: MutableList<ServerDataObject>): MutableList<ServerDataObject> {
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(new open fun CustomDataEntry())
//        data.add(new open fun CustomDataEntry())
//        data.add(new open fun CustomDataEntry())
//        data.add(new open fun CustomDataEntry())
//
//        val set: Set = Set.instantiate()
//        set.data(data)
//        val series1Data: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
//        val series2Data: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
//        val series3Data: Mapping = set.mapAs("{ x: 'x', value: 'value3' }")
//        val series4Data: Mapping = set.mapAs("{ x: 'x', value: 'value4' }")
//
//        mekkoChart.mekko(series1Data)
//            .open  fun name()
//
//        mekkoChart.mekko(series2Data)
//            .open  fun name()
//
//        mekkoChart.mekko(series3Data)
//            .open  fun name()
//
//        mekkoChart.mekko(series4Data)
//            .open  fun name()
//
//        mekkoChart.xAxis(0).open  fun orientation()
//
//        mekkoChart.labels().open  fun format()
//
//        mekkoChart.tooltip().open  fun format()
//
//    }

    private class CustomDataEntry internal constructor(x: String?,
                                                       value: Number?,
                                                       value2: Number?,
                                                       value3: Number?,
                                                       value4: Number?) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
            setValue("value4", value4)
        }
    }


    companion object {
        fun newInstance(chartType: ChartType) = ChartFragment(chartType)
    }


}


