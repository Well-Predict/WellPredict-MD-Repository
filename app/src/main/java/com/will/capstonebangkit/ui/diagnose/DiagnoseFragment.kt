package com.will.capstonebangkit.ui.diagnose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.will.capstonebangkit.databinding.FragmentDiagnoseBinding

class DiagnoseFragment : Fragment() {

    private var _binding: FragmentDiagnoseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val diagnoseViewModel =
            ViewModelProvider(this).get(DiagnoseViewModel::class.java)

        _binding = FragmentDiagnoseBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        setupSearchBar()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


//    private fun setupSearchBar() {
//        with(binding) {
//            searchView.setupWithSearchBar(searchBar)
//            searchView.editText.setOnEditorActionListener { _, _, _ ->
//                searchBar.setText(searchView.text)
//                searchView.hide()
////                mainViewModel.searchQuery.value = searchView.text.toString()
//                false
//            }
//        }
//    }
}