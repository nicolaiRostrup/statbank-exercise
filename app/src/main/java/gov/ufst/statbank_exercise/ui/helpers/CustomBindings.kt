package gov.ufst.statbank_exercise.ui.helpers

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun changeVisibility(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}