package org.moneymanager.com.view.about

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.moneymanager.com.BuildConfig
import org.moneymanager.com.R
import org.moneymanager.com.databinding.FragmentAboutBinding
import org.moneymanager.com.view.base.BaseFragment

@AndroidEntryPoint
class AboutFragment : BaseFragment<FragmentAboutBinding, AboutViewModel>() {
    override val viewModel: AboutViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        appVersion.text = getString(
            R.string.text_app_version,
            BuildConfig.VERSION_NAME,
            BuildConfig.VERSION_CODE
        )

        license.setOnClickListener {
            viewModel.launchLicense().run {
                launchBrowser(viewModel.url.value)
            }
        }

        ivGithub.setOnClickListener {
            viewModel.launchRepository().run {
                launchBrowser(viewModel.url.value)
            }
        }
    }

    private fun launchBrowser(url: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).also {
            startActivity(it)
        }

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentAboutBinding.inflate(inflater, container, false)
}
