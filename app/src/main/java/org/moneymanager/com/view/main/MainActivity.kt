package org.moneymanager.com.view.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController

import kotlinx.coroutines.flow.collect

import dagger.hilt.android.AndroidEntryPoint
import org.moneymanager.com.R
import org.moneymanager.com.databinding.ActivityMainBinding
import org.moneymanager.com.local.datastore.DataStoreImpl
import org.moneymanager.com.repo.TransactionRepo
import org.moneymanager.com.exportcsv.ExportCsvService
import org.moneymanager.com.view.main.viewmodel.TransactionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var repo: TransactionRepo
    @Inject
    lateinit var exportCsvService: ExportCsvService
    @Inject
    lateinit var themeManager: DataStoreImpl
    private val viewModel: TransactionViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Just so the viewModel doesn't get removed by the compiler, as it isn't used
         * anywhere here for now
         */
        viewModel

        initViews(binding)
//        observeThemeMode()
        observeNavElements(binding, navHostFragment.navController)
    }

//    private fun observeThemeMode() {
//        lifecycleScope.launchWhenStarted {
//            viewModel.getUIMode.collect {
//                val mode = when (it) {
//                    true -> AppCompatDelegate.MODE_NIGHT_YES
//                    false -> AppCompatDelegate.MODE_NIGHT_NO
//                }
//                AppCompatDelegate.setDefaultNightMode(mode)
//            }
//        }
//    }

    private fun observeNavElements(
        binding: ActivityMainBinding,
        navController: NavController
    ) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.dashboardFragment) {
                supportActionBar!!.setIcon(R.drawable.transparent_icon)
            } else {
                supportActionBar!!.setIcon(null)
            }
        }
    }

    private fun initViews(binding: ActivityMainBinding) {
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.titlebar_gradient_bg))

        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        with(navHostFragment.navController) {
            appBarConfiguration = AppBarConfiguration(graph)
            setupActionBarWithNavController(this, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navHostFragment.navController.navigateUp()
        return super.onSupportNavigateUp()
    }
}
