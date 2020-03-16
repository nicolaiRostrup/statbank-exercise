package gov.ufst.statbank_exercise.ui.helpers

import android.view.View
import androidx.databinding.BindingAdapter

//import gov.ufst.statbank_exercise.ui.main.SettingsViewModel

@BindingAdapter("visibleIf")
fun changeVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

