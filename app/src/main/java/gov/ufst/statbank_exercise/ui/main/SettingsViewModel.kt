package gov.ufst.statbank_exercise.ui.main


import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gov.ufst.statbank_exercise.ui.helpers.*


class SettingsViewModel(val userRequest: UserRequest) : ViewModel(), Observable {

    //Bound to seek bars (equals values in user request minus '1850'
//    val seekBarValueDeliveryType = MutableLiveData<Int>()
//    val seekBarValueYearFrom = MutableLiveData<Int>()
//    val seekBarValueYearUntil = MutableLiveData<Int>()
//
//    //Bound to text fields (equals values in user request)
//    val textFieldValueDeliveryType = MutableLiveData<Int>()
//    val textFieldValueYearFrom = MutableLiveData<Int>()
//    val textFieldValueYearUntil = MutableLiveData<Int>()


//
//    fun postUserRequest(){
//        textFieldValueDeliveryType.postValue(userRequest.deliveryType.toInt())
//        textFieldValueYearFrom.postValue(userRequest.fromYear)
//        textFieldValueYearUntil.postValue(userRequest.untilYear)
//
//        seekBarValueDeliveryType.postValue(userRequest.deliveryType.toInt() -1)
//        seekBarValueYearFrom.postValue(userRequest.fromYear - 1850)
//        seekBarValueYearUntil.postValue(userRequest.untilYear - 1850)
//    }

//    fun saveValues(){
//        userRequest.deliveryType = textFieldValueDeliveryType.value?.toDeliveryType() ?: DefaultSettings.deliveryType
//        userRequest.fromYear = textFieldValueYearFrom.value ?: DefaultSettings.minimumYear
//        userRequest.untilYear = textFieldValueYearUntil.value ?: DefaultSettings.maximumYear
//
//    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}
