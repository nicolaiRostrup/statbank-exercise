package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.SharedViewModel
import gov.ufst.statbank_exercise.databinding.NavigationFragmentBinding
import kotlinx.android.synthetic.main.navigation_fragment.*

class NavigationFragment : Fragment() {

    private lateinit var binding: NavigationFragmentBinding
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.navigation_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pie_chart_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                             ChartFragment.newInstance(ChartFragment.ChartType.PIE))
                    .commitNow()
            }

        }

        line_chart_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                             ChartFragment.newInstance(ChartFragment.ChartType.LINE))
                    .commitNow()
            }

        }

        mekko_chart_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                             ChartFragment.newInstance(ChartFragment.ChartType.MEKKO))
                    .commitNow()
            }

        }

    }

    companion object {
        fun newInstance() = NavigationFragment()
    }

}
