package com.example.notes.screens.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.example.notes.R
import com.example.notes.databinding.ActivityIntroBinding
import com.example.notes.screens.home.HomeActivity

/**
 * Created by Ankita
 */
class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            val pair1 = Pair.create(binding.icon as View, getString(R.string.image_logo_transition))
            val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1)
            startActivity(intent, activityOptionsCompat.toBundle())
        }, 3000)
    }
}