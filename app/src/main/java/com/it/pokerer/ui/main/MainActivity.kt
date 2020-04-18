package com.it.pokerer.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.it.pokerer.R
import com.it.pokerer.utils.InjectorUtils


class MainActivity : AppCompatActivity() {

    companion object {
        private const val HISTORY_FRAGMENT_TAG = "history"
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val factory = InjectorUtils.provideMainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, RoundFragment())
            .commitNow()

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.currentFragment.observe(this, Observer {currentFragment ->
            var myFrag = supportFragmentManager.findFragmentById(R.id.container)

            if (myFrag is RoundFragment && currentFragment == MainViewModel.Fragment.HISTORY) {
                myFrag = supportFragmentManager.findFragmentByTag(HISTORY_FRAGMENT_TAG)

                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_top, R.anim.slide_out_bottom)
                    .replace(R.id.container, myFrag ?: HistoryFragment() , HISTORY_FRAGMENT_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(HISTORY_FRAGMENT_TAG)
                    .commit()
            } else if (myFrag is HistoryFragment && currentFragment == MainViewModel.Fragment.ROUND)
                supportFragmentManager.popBackStack()

            invalidateOptionsMenu()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onBackPressed() {
        if(viewModel.currentFragment.value == MainViewModel.Fragment.HISTORY)
            viewModel.currentFragment.value = MainViewModel.Fragment.ROUND
        else
            super.onBackPressed()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        when(viewModel.currentFragment.value){
            MainViewModel.Fragment.HISTORY -> {
                menu?.findItem(R.id.action_bar_item_round)?.isVisible = true
                menu?.findItem(R.id.action_bar_item_history)?.isVisible = false
            }
            MainViewModel.Fragment.ROUND -> {
                menu?.findItem(R.id.action_bar_item_round)?.isVisible = false
                menu?.findItem(R.id.action_bar_item_history)?.isVisible = true
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar_item_history -> viewModel.currentFragment.value = MainViewModel.Fragment.HISTORY
            R.id.action_bar_item_round -> viewModel.currentFragment.value = MainViewModel.Fragment.ROUND
        }
        return true
    }


}
