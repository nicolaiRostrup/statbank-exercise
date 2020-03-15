package gov.ufst.statbank_exercise

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gov.ufst.statbank_exercise.data.model.Repository
import gov.ufst.statbank_exercise.ui.helpers.UserRequest
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
