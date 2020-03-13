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
import gov.ufst.statbank_exercise.databinding.MainFragmentBinding
import kotlinx.android.synthetic.main.main_fragment.view.*


class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)
        }

        viewModel.birthData.observe(viewLifecycleOwner, Observer { event ->
            event?.getContentIfNotHandledOrReturnNull()?.let {
                createPieChart(it)

            }
        })

    }

    override fun onStart(){
        super.onStart()
        viewModel.getData()


    }

    private fun createPieChart(twinData: TwinData){

        val pie = AnyChart.pie()

        val data: MutableList<DataEntry> = ArrayList()
//        val thisLabelMap = twinData.dataset.dimension.tid.category.indexList.apply {
//            this.entries.associateBy({ it.value }) { it.key }
//        }

        val thisLabelMap = twinData.dataset.dimension.tid.category.indexList
        val reversedLabelMap = thisLabelMap.entries.associateBy({ it.value }) { it.key }

        for((index, value) in twinData.dataset.value.withIndex()){

            val thisLabel: String? = reversedLabelMap[index] ?: ""

            data.add(ValueDataEntry(thisLabel, value))
        }
//        data.add(ValueDataEntry("John", 10000))
//        data.add(ValueDataEntry("Jake", 12000))
//        data.add(ValueDataEntry("Peter", 18000))

        pie.data(data)

        val chart = binding.root.any_chart_view
        chart.setChart(pie)

    }


    companion object {
        fun newInstance() = MainFragment()
    }


}
