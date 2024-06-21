package com.bangkit.wellpredict.ui.profile

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.wellpredict.R
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.databinding.FragmentProfileBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.ui.auth.LoginActivity
import com.bangkit.wellpredict.utils.DialogHelper
import com.saadahmedev.popupdialog.PopupDialog
import com.saadahmedev.popupdialog.listener.StandardDialogActionListener


class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupProfile()

        binding.btnLogout.setOnClickListener { logoutDialog() }

        return root
    }

    private fun setupProfile() {
        viewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.tvUserName.text = user.name
            binding.tvUserEmail.text = user.email
        }
    }

    private fun logoutDialog() {
        PopupDialog.getInstance(context)
            .standardDialogBuilder()
            .createStandardDialog()
            .setHeading("Logout")
            .setPositiveButtonTextColor(R.color.white)
            .setPositiveButtonBackgroundColor(R.color.red)
            .setPositiveButtonText("Yes")
            .setDescription(
                "Are you sure you want to logout?" +
                        " This action cannot be undone"
            )
            .setIcon(R.drawable.ic_logout_24)
            .setIconColor(R.color.red)
            .build(object : StandardDialogActionListener {
                override fun onPositiveButtonClicked(dialog: Dialog) {
                    viewModel.logout().observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is ResultState.Loading -> {
                            }

                            is ResultState.Success -> {
                                dialog.dismiss()
                                startActivity(Intent(requireContext(), LoginActivity::class.java))
                                requireActivity().finish()
                            }

                            is ResultState.Error -> {
                                DialogHelper.errorDialog(requireContext(), result.error)
                                dialog.dismiss()
                            }
                        }
                    }
                }

                override fun onNegativeButtonClicked(dialog: Dialog) {
                    dialog.dismiss()
                }
            })
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}