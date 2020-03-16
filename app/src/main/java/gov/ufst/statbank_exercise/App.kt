package gov.ufst.statbank_exercise

import android.app.Application
import gov.ufst.statbank_exercise.data.model.Repository
import gov.ufst.statbank_exercise.ui.helpers.UserRequest
import gov.ufst.statbank_exercise.ui.main.ChartViewModel
import gov.ufst.statbank_exercise.ui.main.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class StatBank : Application() {

    override fun onCreate() {
        super.onCreate()

        val userRequest = UserRequest()
        val repository = Repository()

        val mainModule = module {
            viewModel { SettingsViewModel(userRequest) }
            viewModel { ChartViewModel(userRequest = userRequest, repository = repository) }
            single { Repository() }
            single { UserRequest() }

        }
        // Initialize Dependency Injection.
        startKoin {
            androidContext(applicationContext)
            modules(mainModule)

        }
    }

}