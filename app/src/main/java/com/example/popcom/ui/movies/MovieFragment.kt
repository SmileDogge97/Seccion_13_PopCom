package com.example.popcom.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.popcom.R
import com.example.popcom.retrofit.models.Movie

class MovieFragment : Fragment() {
    private lateinit var moviesViewModel: MovieViewModel
    private lateinit var moviesAdapter: MovieRecyclerViewAdapter
    private var popularMovies: List<Movie> = ArrayList()

    private var columnCount = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        //Obtenemos el viewModel
        moviesViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        moviesAdapter = MovieRecyclerViewAdapter(popularMovies)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter =moviesAdapter
            }
        }
        //Observer de las películas
        moviesViewModel.getPopularMovies().observe(viewLifecycleOwner, Observer{
            popularMovies=it
            moviesAdapter.setData(popularMovies)
        })
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MovieFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}