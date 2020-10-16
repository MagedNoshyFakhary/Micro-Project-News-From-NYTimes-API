package com.mego.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mego.myapplication.R
import com.mego.myapplication.db.NewsDB
import com.mego.myapplication.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*newsRepository*/

        val newsRepository = NewsRepository(NewsDB.invoke(this))
        /*viewModel inflater*/
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
       /* bottom navigation Controller*/
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }
}