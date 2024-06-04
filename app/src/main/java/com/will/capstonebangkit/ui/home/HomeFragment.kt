package com.will.capstonebangkit.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.will.capstonebangkit.data.ResultState
import com.will.capstonebangkit.databinding.FragmentHomeBinding
import com.will.capstonebangkit.ui.ViewModelFactory
import com.will.capstonebangkit.ui.news.NewsActivity
import com.will.capstonebangkit.ui.news.NewsWebViewActivity


class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupNewsCard()
        seeAllOnClickHandler(NewsActivity::class.java)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupNewsCard(){
        viewModel.getNewsFirst().observe(viewLifecycleOwner) { result ->
            if (result != null){
                when (result) {
                    is ResultState.Loading -> {
//                        lottieLoadingAnimation(true)
                    }
                    is ResultState.Success -> {
//                        lottieLoadingAnimation(false)
                        binding.tvNewsAuthor.text = result.data.author
                        binding.tvNewsTitle.text = result.data.title
                        context?.let {
                            Glide.with(it)
                                .load(result.data.urlToImage)
                                .into(binding.ivNewsImage)
                        }
                        newsCardOnClickHandler(result.data.url.toString())
                    }
                    is ResultState.Error -> {
//                        lottieLoadingAnimation(false)
                        showAlert(result.error)
                    }
                }
            }
        }
    }

    private fun seeAllOnClickHandler(targetActivity: Class<out Activity>){
        val seeAllNewsTv = binding.tvNewsSeeAll

        seeAllNewsTv.setOnClickListener(View.OnClickListener {
            val intent = Intent( activity, targetActivity)
            startActivity(intent)
        })
    }

    private fun newsCardOnClickHandler(newsUrl: String) {
        val newsCard = binding.newsCard
        newsCard.setOnClickListener {
            val intent = Intent(activity, NewsWebViewActivity::class.java)
            intent.putExtra("NEWS_URL", newsUrl)
            startActivity(intent)
        }
    }

    private fun showAlert(message: String){
        AlertDialog.Builder(requireContext()).apply {
            setTitle(message)
            setPositiveButton("OK") { _, _ -> }
            create()
            show()
        }
    }
}