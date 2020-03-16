package gov.ufst.statbank_exercise.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import gov.ufst.statbank_exercise.R
import gov.ufst.statbank_exercise.databinding.SettingsFragmentBinding
import gov.ufst.statbank_exercise.ui.helpers.*
import org.koin.android.viewmodel.ext.android.getViewModel
import kotlinx.android.synthetic.main.settings_fragment.*



class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding
    private lateinit var viewModel: SettingsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.settings_fragment, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

    }

    override fun onStart() {
        super.onStart()

        val userRequest = viewModel.userRequest
        seekbar_birthtype.progress = userRequest.deliveryType.toInt()
        seekbar_year_from.progress = userRequest.fromYear - DefaultSettings.minimumYear
        seekbar_year_until.progress = userRequest.untilYear - DefaultSettings.minimumYear
        result_birthtype.text = (userRequest.deliveryType.toInt() + 1).toString()
        result_year_from.text = userRequest.fromYear.toString()
        result_year_until.text = userRequest.untilYear.toString()

        setUpSeekBar(SeekBarType.DELIVERY, seekbar_birthtype)
        setUpSeekBar(SeekBarType.FROM_YEAR, seekbar_year_from)
        setUpSeekBar(SeekBarType.UNTIL_YEAR, seekbar_year_until)

    }

    private fun setUpSeekBar(seekBarType: SeekBarType, thisSeekBar: SeekBar) {

        thisSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var progressChangedValue = 0

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress

                if (seekBarType == SeekBarType.DELIVERY) {
                    //result_birthtype.text = progressChangedValue.toString()
                    result_birthtype.text = (progressChangedValue.toString().toInt() + 1).toString()
                }
                if (seekBarType == SeekBarType.FROM_YEAR) {
                    if( progress > seekbar_year_until.progress){
                        thisSeekBar.progress = seekbar_year_until.progress

                    }
                    else{
                        result_year_from.text =  (progressChangedValue + DefaultSettings.minimumYear).toString()
                    }

                }
                if (seekBarType == SeekBarType.UNTIL_YEAR) {
                    if( progress < seekbar_year_from.progress){
                        thisSeekBar.progress = seekbar_year_from.progress
                    }
                    else{
                        result_year_until.text = (progressChangedValue + DefaultSettings.minimumYear).toString()
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (seekBarType == SeekBarType.DELIVERY) {
                    viewModel.userRequest.deliveryType = (result_birthtype.text.toString().toInt() - 1).toDeliveryType()
                }
                if (seekBarType == SeekBarType.FROM_YEAR) {
                    viewModel.userRequest.fromYear = result_year_from.text.toString().toInt()

                }
                if (seekBarType == SeekBarType.UNTIL_YEAR) {
                    viewModel.userRequest.untilYear = result_year_until.text.toString().toInt()

                }
            }
        })

    }

    companion object {
        fun newInstance() = SettingsFragment()
    }

}
