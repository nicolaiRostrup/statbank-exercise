package gov.ufst.statbank_exercise.ui.main


import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gov.ufst.statbank_exercise.SharedViewModel
import gov.ufst.statbank_exercise.data.model.TwinData
import gov.ufst.statbank_exercise.ui.helpers.Event


class SettingsViewModel : ViewModel(), Observable {



    val seekBarValueDeliveryType = MutableLiveData<Int>()
    val seekBarValueYearFrom = MutableLiveData<Int>()
    val seekBarValueYearUntil = MutableLiveData<Int>()

    lateinit var sharedViewModel: SharedViewModel

//
//    private fun handleDataByEvent(twinData: TwinData?) {
//        if (twinData != null) {
//            birthDataTwins.postValue(Event(twinData))
//            serverInfoTwins.postValue(twinData)
//        }
//
//    }



    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}
