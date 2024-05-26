package org.moneymanager.com.view.about

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.moneymanager.com.utils.Constants.MM_GITHUB_LINK
import javax.inject.Inject

@HiltViewModel
class AboutViewModel @Inject constructor() : ViewModel() {
    private val _url = MutableStateFlow(MM_GITHUB_LINK)
    val url: StateFlow<String> = _url

    fun launchLicense() {
        _url.value = MM_GITHUB_LINK
    }

    fun launchRepository() {
        _url.value = MM_GITHUB_LINK
    }
}
