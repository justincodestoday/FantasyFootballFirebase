package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.EditImageDialogBinding
import com.fantasy.fantasyfootball.databinding.EditPasswordDialogBinding
import com.fantasy.fantasyfootball.databinding.EditProfileDialogBinding
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding
import com.fantasy.fantasyfootball.service.ImageStorageService
import com.fantasy.fantasyfootball.util.Utils.getFileName
import com.fantasy.fantasyfootball.util.Utils.validate
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModels()

    private lateinit var imageDialogBinding: EditImageDialogBinding
    private lateinit var accountDialogBinding: EditProfileDialogBinding
    private lateinit var passwordDialogBinding: EditPasswordDialogBinding
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    var imageUri: Uri? = null

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            it?.let { uri ->
                imageDialogBinding.ivImage.setImageURI(uri)
                imageDialogBinding.tvImageName.text =
                    requireContext().contentResolver.getFileName(uri)
            }
        }

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
//                    accountDialogBinding.etEmail.setText(user.email)
                    accountDialogBinding.etTeamName.setText(user.team.name)

                    accountDialogBinding.btnSaveAccount.setOnClickListener {
                        val name = accountDialogBinding.etName.text.toString().trim()
//                        val email = accountDialogBinding.etEmail.text.toString().trim()
                        val teamName = accountDialogBinding.etTeamName.text.toString().trim()

                        if (validate(name, teamName)) {
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                            viewModel.editUser(
                                user.copy(name = name, team = user.team.copy(name = teamName))
                            )
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
                    passwordDialogBinding.etPassword.setText(user.password)
                    passwordDialogBinding.btnSavePassword.setOnClickListener {
                        val _password = passwordDialogBinding.etPassword.text.toString().trim()

                        if (validate(_password)) {
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                            viewModel.editUser(user.copy(password = _password))
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

            viewModel.user.observe(viewLifecycleOwner) { user ->
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
                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.credentialsFragment)
            }
        }
    }
}
