package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.databinding.NavigationFragmentBinding
import gov.ufst.statbank_exercise.ui.helpers.ChartType
import kotlinx.android.synthetic.main.navigation_fragment.*


class NavigationFragment : Fragment() {

    private lateinit var binding: NavigationFragmentBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.navigation_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        pie_chart_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                             ChartFragment.newInstance(ChartType.PIE))
                    .commitNow()
            }

        }

        line_chart_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                             ChartFragment.newInstance(ChartType.LINE))
                    .commitNow()
            }

        }

        settings_button.setOnClickListener {
            activity?.let {
                it.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance())
                    .commitNow()
            }
        }

    }

    companion object {
        fun newInstance() = NavigationFragment()
    }

}
