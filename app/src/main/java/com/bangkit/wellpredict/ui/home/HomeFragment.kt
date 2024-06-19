package com.bangkit.wellpredict.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.wellpredict.R
import com.bumptech.glide.Glide
import com.facebook.shimmer.ShimmerFrameLayout
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.databinding.FragmentHomeBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.ui.auth.LoginActivity
import com.bangkit.wellpredict.ui.news.NewsActivity
import com.bangkit.wellpredict.ui.news.NewsWebViewActivity
import com.bangkit.wellpredict.utils.DateHelper


class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var shimmerFrameLayout : ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            if (!user.isLogin) {
                startActivity(Intent(requireContext(), LoginActivity::class.java))
            }
        }

        shimmerFrameLayout = binding.cardViewShimmer

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
                        showLoading(true, binding.newsCardView)
                    }
                    is ResultState.Success -> {
                        showLoading(false, binding.newsCardView)

                        binding.tvNewsAuthor.text = result.data.source?.name
                        binding.tvNewsTitle.text = result.data.title
                        binding.tvNewsUploadTime.text = DateHelper().convertTime(result.data.publishedAt.toString())
                        context?.let {
                            Glide.with(it)
                                .load(result.data.urlToImage)
                                .into(binding.ivNewsImage)
                        }
                        newsCardOnClickHandler(result.data.url.toString())
                    }
                    is ResultState.Error -> {
                        showLoading(false, binding.newsCardView)
                        showAlert(result.error)
                    }
                }
            }
        }
    }

    private fun seeAllOnClickHandler(targetActivity: Class<out Activity>){
        val seeAllNewsTv = binding.tvNewsSeeAll

        seeAllNewsTv.setOnClickListener {
            val intent = Intent(activity, targetActivity)
            startActivity(intent)
        }
    }

    private fun newsCardOnClickHandler(newsUrl: String) {
        val newsCard = binding.newsCard
        newsCard.setOnClickListener {
            val intent = Intent(activity, NewsWebViewActivity::class.java)
            intent.putExtra("NEWS_URL", newsUrl)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean, targetLayout: View) {
        if (isLoading) {
            targetLayout.visibility = View.INVISIBLE
            shimmerFrameLayout.visibility = View.VISIBLE
            shimmerFrameLayout.startShimmer()
        } else {
            shimmerFrameLayout.stopShimmer()
            shimmerFrameLayout.visibility = View.GONE
            targetLayout.visibility = View.VISIBLE
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