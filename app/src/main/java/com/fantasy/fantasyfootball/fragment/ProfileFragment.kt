package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.databinding.EditImageDialogBinding
import com.fantasy.fantasyfootball.databinding.EditPasswordDialogBinding
import com.fantasy.fantasyfootball.databinding.EditProfileDialogBinding
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding
import com.fantasy.fantasyfootball.service.ImageStorageService
import com.fantasy.fantasyfootball.util.Utils.getFileName
import com.fantasy.fantasyfootball.util.Utils.validate
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    private lateinit var imageDialogBinding: EditImageDialogBinding
    private lateinit var accountDialogBinding: EditProfileDialogBinding
    private lateinit var passwordDialogBinding: EditPasswordDialogBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            it?.let { uri ->
                binding?.profilePicture?.setImageURI(uri)
                imageDialogBinding.ivImage.setImageURI(uri)
                imageDialogBinding.tvImageName.text =
                    requireContext().contentResolver.getFileName(uri)
            }
        }
    }

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        imageDialogBinding = EditImageDialogBinding.inflate(layoutInflater)
        val imageDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        accountDialogBinding = EditProfileDialogBinding.inflate(layoutInflater)
        val accountDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        passwordDialogBinding = EditPasswordDialogBinding.inflate(layoutInflater)
        val passwordDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        viewModel.user.observe(viewLifecycleOwner) {
            binding?.run {
                tvName.text = it.name
                tvUsername.text = it.email
                it.image?.let { imageName ->
                    ImageStorageService.getImageUri(imageName) { uri ->
                        Glide.with(view)
                            .load(uri)
                            .into(profilePicture)
                    }
                }
            }
        }

        binding?.run {
            btnUpdateInfo.setOnClickListener {
                viewModel.user.observe(viewLifecycleOwner) { user ->
                    accountDialog.setContentView(accountDialogBinding.root)
                    accountDialogBinding.etName.setText(user.name)
                    accountDialogBinding.etEmail.setText(user.email)
                    accountDialogBinding.etTeamName.setText(user.team.name)

                    accountDialogBinding.btnSaveAccount.setOnClickListener {
                        val name = accountDialogBinding.etName.text.toString().trim()
                        val email = accountDialogBinding.etEmail.text.toString().trim()
                        val teamName = accountDialogBinding.etTeamName.text.toString().trim()

                        if (validate(name, email, teamName)) {
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                            viewModel.editUser(
                                user.copy(name = name, email = email, team = Team(name = teamName))
                            )
                            Toast.makeText(
                                requireContext(),
                                context?.getString(R.string.update_successful),
                                Toast.LENGTH_SHORT
                            ).show()
                            accountDialog.dismiss()
                        } else {
                            accountDialogBinding.tvError.text =
                                context?.getString(R.string.empty_field)
                            accountDialogBinding.tvError.visibility = View.VISIBLE
                        }
                    }

                }
                accountDialog.setCancelable(true)
                accountDialog.setOnCancelListener {
                    accountDialogBinding.tvError.text = context?.getString(R.string.empty_field)
                    accountDialogBinding.tvError.visibility = View.GONE
                }
                accountDialog.setOnDismissListener {
                    viewModel.fetchCurrentUser()
                }
                accountDialog.show()
            }

            btnChangePassword.setOnClickListener {
                viewModel.user.observe(viewLifecycleOwner) { user ->
                    passwordDialog.setContentView(passwordDialogBinding.root)
                    passwordDialogBinding.btnSavePassword.setOnClickListener {
                        val currentPassword = passwordDialogBinding.etPassword.text.toString().trim()
                        val newPassword = passwordDialogBinding.etNewPassword.text.toString().trim()

                        if (validate(currentPassword) && validate(newPassword)) {
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                            viewModel.updatePassword(currentPassword, newPassword)
                            viewModel.editUser(user.copy(password = newPassword))
                            Toast.makeText(
                                requireContext(),
                                context?.getString(R.string.update_successful),
                                Toast.LENGTH_SHORT
                            ).show()
                            passwordDialog.dismiss()
                        } else {
                            passwordDialogBinding.tvError.text =
                                context?.getString(R.string.empty_single_field)
                            passwordDialogBinding.tvError.visibility = View.VISIBLE
                        }
                    }
                    passwordDialog.setCancelable(true)
                    passwordDialog.setOnCancelListener {
                        passwordDialogBinding.tvError.text =
                            context?.getString(R.string.empty_single_field)
                        passwordDialogBinding.tvError.visibility = View.GONE
                    }
                    passwordDialog.setOnDismissListener {
                        viewModel.fetchCurrentUser()
                    }
                }
                passwordDialog.show()
            }

            imageDialogBinding.tvChooseImage.setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }

            viewModel.user.observe(viewLifecycleOwner) { user->
                imageDialogBinding.tvImageName.setText(user.image)
                user.image?.let { imageName ->
                    ImageStorageService.getImageUri(imageName) { uri ->
                        Glide.with(view)
                            .load(uri)
                            .into(imageDialogBinding.ivImage)
                    }
                }

                imageDialogBinding.btnSaveImage.setOnClickListener {
                    val imageName = imageDialogBinding.tvImageName.text.toString()
                    if (validate(imageName)) {
                        val bundle = Bundle()
                        bundle.putBoolean(Enums.Result.REFRESH.name, true)
                        setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                        viewModel.updateProfile(imageUri)
                        imageDialog.dismiss()
                    } else {
                        imageDialogBinding.tvError.text =
                            context?.getString(R.string.empty_single_field)
                        imageDialogBinding.tvError.visibility = View.VISIBLE
                    }
                }
            }

            profilePicture.setOnClickListener {
                imageDialog.setContentView(imageDialogBinding.root)
                imageDialog.setCancelable(true)
                imageDialog.setOnCancelListener {
                    imageDialogBinding.tvError.text =
                        context?.getString(R.string.empty_single_field)
                    imageDialogBinding.tvError.visibility = View.GONE
                }
                imageDialog.setOnDismissListener {
                    viewModel.fetchCurrentUser()
                }
                imageDialog.show()
            }

            btnSignOut.setOnClickListener {
                viewModel.logout()
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.logout_successful),
                    Toast.LENGTH_SHORT
                ).show()

                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.credentialsFragment)
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.update.collect {
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_profile", bundle)
                viewModel.fetchCurrentUser()
            }
        }
    }
}
