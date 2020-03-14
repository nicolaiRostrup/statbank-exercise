package gov.ufst.statbank_exercise


import androidx.lifecycle.ViewModel
import gov.ufst.statbank_exercise.ui.helpers.UserRequest

class SharedViewModel: ViewModel(){

    lateinit var currentUserRequest: UserRequest


}