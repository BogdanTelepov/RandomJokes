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
        mainActivityViewModel.getRandomJoke()
        fetchJokes()

        binding.refreshButton.setOnClickListener {
            fetchJokes()
        }

        binding.swipeLayout.setOnRefreshListener {
            fetchJokes()
        }

    }


    private fun initialize() {
        mainRepository = MainRepository()
        viewModelFactory = ViewModelFactory(mainRepository)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFactory).get(MainActivityViewModel::class.java)

        jokeListAdapter = JokeListAdapter()
    }


    private fun fetchJokes() {
        binding.swipeLayout.isRefreshing = true
        mainActivityViewModel.responseGetRandomJoke.observe(this, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                jokeListAdapter.differ.submitList(response.body()?.toList())
                binding.rvJokeList.apply {
                    adapter = jokeListAdapter
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    startLayoutAnimation()
                }
            } else {
                binding.swipeLayout.isRefreshing = false
            }
        })

    }
}