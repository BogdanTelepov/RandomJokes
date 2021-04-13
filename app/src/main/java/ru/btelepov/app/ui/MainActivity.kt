package ru.btelepov.app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.btelepov.app.adapters.JokeListAdapter
import ru.btelepov.app.databinding.ActivityMainBinding
import ru.btelepov.app.repository.MainRepository
import ru.btelepov.app.viewmodels.MainActivityViewModel
import ru.btelepov.app.viewmodels.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var mainRepository: MainRepository
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var jokeListAdapter: JokeListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initialize()
        initAdapter()
        fetchJokes()

        binding.refreshButton.setOnClickListener {
            mainActivityViewModel.getRandomJoke()

        }

        binding.swipeLayout.setOnRefreshListener {
            mainActivityViewModel.getRandomJoke()
        }

    }


    private fun initialize() {
        mainRepository = MainRepository()
        viewModelFactory = ViewModelFactory(mainRepository)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)


    }


    private fun fetchJokes() {
        mainActivityViewModel.getRandomJoke()
        binding.swipeLayout.isRefreshing = true
        mainActivityViewModel.responseGetRandomJoke.observe(this, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                jokeListAdapter.differ.submitList(response.body()?.toList())

            } else {
                binding.swipeLayout.isRefreshing = false
            }
        })

    }

    private fun initAdapter() {
        jokeListAdapter = JokeListAdapter()
        binding.rvJokeList.apply {
            adapter = jokeListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
    }
}