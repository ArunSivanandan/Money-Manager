package org.moneymanager.com.view.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.postDelayed
import dagger.hilt.android.AndroidEntryPoint
import org.moneymanager.com.databinding.ActivitySplashLayoutBinding
import org.moneymanager.com.view.main.MainActivity
import java.util.*

@AndroidEntryPoint
class SplashActivity: AppCompatActivity() {

    private lateinit var mBinding: ActivitySplashLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashLayoutBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }, 3000)
    }
}