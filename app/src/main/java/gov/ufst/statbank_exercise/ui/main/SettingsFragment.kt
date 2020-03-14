package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.SharedViewModel
import gov.ufst.statbank_exercise.databinding.SettingsFragmentBinding
import gov.ufst.statbank_exercise.ui.helpers.DeliveryType
import gov.ufst.statbank_exercise.ui.helpers.UserRequest
import gov.ufst.statbank_exercise.ui.helpers.toDeliveryType
import gov.ufst.statbank_exercise.ui.helpers.toInt
import kotlinx.android.synthetic.main.settings_fragment.view.*


class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private lateinit var viewModel: SettingsViewModel
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {


        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        binding.viewModel = viewModel

        activity?.let {
            sharedViewModel = ViewModelProvider(it).get(SharedViewModel::class.java)

        }
        viewModel.sharedViewModel = sharedViewModel
        sharedViewModel.currentUserRequest = UserRequest(DeliveryType.SINGLE, 0, 0)

    }

    override fun onStart() {
        super.onStart()

        val seekBarBirth = binding.root.seekbar_birthtype
        val seekBarFrom = binding.root.seekbar_year_from
        val seekBarUntil = binding.root.seekbar_year_until

        setUpSeekBar(SeekBarType.DELIVERY, seekBarBirth)
        setUpSeekBar(SeekBarType.FROM_YEAR, seekBarFrom)
        setUpSeekBar(SeekBarType.UNTIL_YEAR, seekBarUntil)

        seekBarBirth.progress = sharedViewModel.currentUserRequest.deliveryType.toInt()
        seekBarFrom.progress = sharedViewModel.currentUserRequest.fromYear
        seekBarUntil.progress = sharedViewModel.currentUserRequest.untilYear
    }

    private fun setUpSeekBar(seekBarType: SeekBarType, thisSeekBar: SeekBar) {


        thisSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progressChangedValue = 0

            override fun onProgressChanged(seekBar: SeekBar,
                                           progress: Int,
                                           fromUser: Boolean) {
                progressChangedValue = progress

                if (seekBarType == SeekBarType.DELIVERY) {
                    viewModel.seekBarValueDeliveryType.postValue(progress)
                }
                if (seekBarType == SeekBarType.FROM_YEAR) {
                    viewModel.seekBarValueYearFrom.postValue(progress)
                }
                if (seekBarType == SeekBarType.UNTIL_YEAR) {
                    viewModel.seekBarValueYearUntil.postValue(progress)
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

                if (seekBarType == SeekBarType.DELIVERY) {
                    sharedViewModel.currentUserRequest.deliveryType =
                        progressChangedValue.toDeliveryType()
                }
                if (seekBarType == SeekBarType.FROM_YEAR) {
                    sharedViewModel.currentUserRequest.fromYear =
                        progressChangedValue
                }
                if (seekBarType == SeekBarType.UNTIL_YEAR) {
                    sharedViewModel.currentUserRequest.untilYear =
                        progressChangedValue
                }

                Toast.makeText(context, sharedViewModel.currentUserRequest.toString(),
                               Toast.LENGTH_SHORT).show()

            }
        })

    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

    enum class SeekBarType {
        DELIVERY, FROM_YEAR, UNTIL_YEAR
    }

}
