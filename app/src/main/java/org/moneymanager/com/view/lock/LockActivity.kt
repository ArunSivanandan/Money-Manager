package org.moneymanager.com.view.lock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import org.moneymanager.com.R
import org.moneymanager.com.databinding.ActivityLockBinding
import org.moneymanager.com.local.datastore.DataStoreImpl
import org.moneymanager.com.utils.Constants.EMPTY_STRING
import org.moneymanager.com.view.main.MainActivity
import org.moneymanager.com.view.main.viewmodel.TransactionViewModel
import javax.inject.Inject

@AndroidEntryPoint
class LockActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLockBinding

    @Inject
    lateinit var dataStoreImpl: DataStoreImpl

    private val viewModel: TransactionViewModel by viewModels()
    private var existingUser = true
    private var newPwd = EMPTY_STRING
    private var confirmPwd = EMPTY_STRING
    private var savedPwd = EMPTY_STRING

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        initViews()
        initListener()
    }

    private fun initViews() {
        lifecycleScope.launchWhenStarted {
            savedPwd = viewModel.getUIMode.first()
            if (savedPwd.isNotEmpty()) {
                mBinding.tilNewPassword.visibility = View.GONE
                mBinding.tilConfirmPassword.hint = getString(R.string.lock_existing_user_pwd)
                mBinding.tvNote.text = getString(R.string.lock_note_existing)
            } else {
                existingUser = false
            }
        }
    }

    private fun initListener() {
        mBinding.apply {
            btnConfirm.setOnClickListener {
                tvError.visibility = View.GONE
                newPwd = etNewPwd.text.toString()
                confirmPwd = etConfirmPwd.text.toString()
                if (checkFields()) {
                    if (validatePassword()) {
                        tvError.visibility = View.GONE
                        viewModel.saveLockPwd(etConfirmPwd.text.toString())
                        startActivity(Intent(this@LockActivity, MainActivity::class.java))
                        finish()
                    } else {
                        tvError.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun validatePassword() = run {
        if (existingUser) {
            savedPwd == confirmPwd
        } else {
            newPwd == confirmPwd
        }
    }

    private fun checkFields(): Boolean {
        if (!existingUser && newPwd.isEmpty()) {
            mBinding.tilNewPassword.error = getString(R.string.lock_pwd_empty)
            return false
        } else {
            mBinding.tilNewPassword.error = null
        }

        if (confirmPwd.isEmpty()) {
            mBinding.tilConfirmPassword.error = getString(R.string.lock_pwd_empty)
            return false
        } else {
            mBinding.tilConfirmPassword.error = null
        }

        return true
    }
}