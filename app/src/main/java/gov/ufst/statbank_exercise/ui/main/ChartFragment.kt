package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anychart.core.SeparateChart
import gov.ufst.statbank_exercise.MainActivity
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.databinding.ChartFragmentBinding
import gov.ufst.statbank_exercise.ui.helpers.ChartType
import gov.ufst.statbank_exercise.ui.helpers.Event
import kotlinx.android.synthetic.main.chart_fragment.view.*
import org.koin.android.ext.android.inject
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

        if (viewModel.repository.lastUpdate == null) viewModel.getData()
        else viewModel.executeUserRequest()

    }

    companion object {
        fun newInstance(chartType: ChartType) = ChartFragment(chartType)
    }


}


