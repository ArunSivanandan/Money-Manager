package org.moneymanager.com.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import dagger.hilt.android.AndroidEntryPoint
import org.moneymanager.com.databinding.ActivitySplashLayoutBinding
import org.moneymanager.com.view.lock.LockActivity
import org.moneymanager.com.view.main.MainActivity
import java.util.*

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = ActivitySplashLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, LockActivity::class.java))
                finish()
            }
        }, 3000)
    }
}