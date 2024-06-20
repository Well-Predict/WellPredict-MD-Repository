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

        binding.btnLogout.setOnClickListener { logoutDialog() }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun logoutDialog() {
        // Tampilkan dialog konfirmasi logout
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
                                errorDialog()
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
    private fun errorDialog() {
        PopupDialog.getInstance(context)
            .statusDialogBuilder()
            .createErrorDialog()
            .setHeading("Uh-Oh")
            .setActionButtonText("Okay")
            .setDescription("Unexpected error occurred." +
                    " Try again later.")
            .build(Dialog::dismiss)
            .show()
    }
}