package com.och.musicplayer.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.och.musicplayer.App
import com.och.musicplayer.R
import com.och.musicplayer.databinding.ActivityMainBinding
import com.och.musicplayer.di.DaggerAppComponent
import com.och.musicplayer.ui.home.HomeContract
import com.och.musicplayer.ui.error.ErrorFragment
import com.och.musicplayer.ui.search.SearchContract

class MainActivity : AppCompatActivity(), HomeContract.Host, SearchContract.Host {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component = DaggerAppComponent.create()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)
    }

   override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_toolbar_menu, menu)
        return true
   }

    override fun showErrorDialog(error: Throwable) {
        error.printStackTrace()
        ErrorFragment.newInstance(
            R.string.error_dialog_title, R.string.error_dialog_message, android.R.string.ok
        ).apply {
            setError(error)
            show(supportFragmentManager, ErrorFragment.TAG)
        }
    }
}
