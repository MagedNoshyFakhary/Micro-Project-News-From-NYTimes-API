package com.mego.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mego.myapplication.R
import com.mego.myapplication.adapter.BookmarkAdapter
import com.mego.myapplication.adapter.NewsAdapter
import com.mego.myapplication.ui.MainActivity
import com.mego.myapplication.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_bookmark.*

/*fragment to show articles From Db*/

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: BookmarkAdapter
/*Inflate viewModel and RecyclerView*/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

       newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("news", it)
            }
            findNavController().navigate(
                R.id.action_bookmarkFragment_to_articleFragment,
                bundle
            )
        }
/*handel Ordering & Deleting items*/
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }
/*Getting items FromDb*/
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }
/*Setup RecyclerView*/
    private fun setupRecyclerView() {
        newsAdapter = BookmarkAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = GridLayoutManager(activity,2)
        }
    }
}