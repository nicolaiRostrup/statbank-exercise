package gov.ufst.statbank_exercise

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import gov.ufst.statbank_exercise.ui.helpers.Event
import gov.ufst.statbank_exercise.ui.main.NavigationFragment
import gov.ufst.statbank_exercise.ui.main.SettingsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SettingsFragment.newInstance())
                .replace(R.id.navigation, NavigationFragment.newInstance())
                .commitNow()
        }
    }
}
