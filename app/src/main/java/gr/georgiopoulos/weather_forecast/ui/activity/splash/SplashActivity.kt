package gr.georgiopoulos.weather_forecast.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import gr.georgiopoulos.weather_forecast.R
import gr.georgiopoulos.weather_forecast.ui.activity.home.HomeActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        const val SPLASH_DELAY: Long = 2000
    }

    private var delayHandler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_loading)
        initLayout()
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    public override fun onDestroy() {
        delayHandler?.removeCallbacks(mRunnable)
        super.onDestroy()
    }

    private fun initLayout() {
        delayHandler = Looper.myLooper()?.let { Handler(it) }
        delayHandler?.postDelayed(mRunnable, SPLASH_DELAY)
    }
}