package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.EditImageDialogBinding
import com.fantasy.fantasyfootball.databinding.EditPasswordDialogBinding
import com.fantasy.fantasyfootball.databinding.EditProfileDialogBinding
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : Fragment() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentProfileBinding
    private lateinit var imageDialogBinding: EditImageDialogBinding
    private lateinit var accountDialogBinding: EditProfileDialogBinding
    private lateinit var passwordDialogBinding: EditPasswordDialogBinding
    private lateinit var authService: AuthService

    //    private var bytes: ByteArray? = null
    private val viewModel: ProfileViewModel by viewModels {
        ProfileViewModel.Provider(
            (requireContext().applicationContext as MainApplication).userRepo,
            (requireContext().applicationContext as MainApplication).teamRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authService = AuthService.getInstance(requireActivity().applicationContext)
        val user = authService.getAuthenticatedUser()
        if (user != null) {
            viewModel.getUserWithTeam(user.userId!!)
        }

        imageDialogBinding = EditImageDialogBinding.inflate(layoutInflater)
        val imageDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        accountDialogBinding = EditProfileDialogBinding.inflate(layoutInflater)
        val accountDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        passwordDialogBinding = EditPasswordDialogBinding.inflate(layoutInflater)
        val passwordDialog = Dialog(requireContext(), R.style.Custom_AlertDialog)

        var name = ""
        var username = ""
        var password = ""
        var image: ByteArray? = null
        var teamId = 0
        var teamName = ""
        var teamPoints = 0
        var teamBudget = 0f

        val timeZone = TimeZone.getTimeZone("GMT+8")
        val calendar = Calendar.getInstance()
        val dateFormat: DateFormat =
            SimpleDateFormat("LLLL d yyyy, HH:mm a (z)", Locale.ENGLISH)
        dateFormat.timeZone = timeZone
        val date = dateFormat.format(calendar.time)

        viewModel.userTeam.observe(viewLifecycleOwner) {
            binding.apply {
                tvName.text = it.user.name
                tvUsername.text = it.user.username
                if (it.user.image != null) {
                    val bitmap =
                        BitmapFactory.decodeByteArray(it.user.image, 0, it.user.image!!.size)
                    profilePicture.setImageBitmap(bitmap)
                    imageDialogBinding.ivImage.setImageBitmap(bitmap)
                }

                name = it.user.name.toString()
                username = it.user.username.toString()
                password = it.user.password.toString()
                image = it.user.image
                teamId = it.team.teamId!!
                teamName = it.team.name.toString()
                teamPoints = it.team.points
                teamBudget = it.team.budget
            }
        }

//        filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
//            it?.let { uri ->
////                binding.tvImageName.text = requireContext().contentResolver.getFileName(uri)
//
//                val inputStream = requireContext().contentResolver.openInputStream(uri)
////                val bytes: ByteArray = inputStream?.readBytes()!!
//                bytes = inputStream?.readBytes()
//
////                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
////                binding.profilePicture.setImageBitmap(bitmap)
//                image = bytes
//                Log.d("debugging", bytes.toString())
//                inputStream?.close()
//            }
//        }

        binding.apply {
            var bytes: ByteArray? = null
            filePickerLauncher =
                registerForActivityResult(ActivityResultContracts.GetContent()) {
                    it?.let { uri ->
                        imageDialogBinding.tvImageName.text =
                            requireContext().contentResolver.getFileName(uri)
                        imageDialogBinding.tvImageName.movementMethod = ScrollingMovementMethod()
                        val inputStream = requireContext().contentResolver.openInputStream(uri)
                        bytes = inputStream?.readBytes()!!
                        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
                        imageDialogBinding.ivImage.setImageBitmap(bitmap)
                        inputStream.close()
                    }
                }
            imageDialogBinding.tvChooseImage.setOnClickListener {
                filePickerLauncher.launch("image/*")
            }
            ivEditImage.setOnClickListener {
                imageDialog.setContentView(imageDialogBinding.root)
                imageDialogBinding.btnSaveImage.setOnClickListener {
                    val imageName = imageDialogBinding.tvImageName.text.toString()
                    if (validate(imageName)) {
                        val bundle = Bundle()
                        bundle.putBoolean(Enums.Result.REFRESH.name, true)
                        setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                        viewModel.editUser(
                            user?.userId!!,
                            User(user.userId, name, username, password, bytes)
                        )
                        val team = Team(
                            name = teamName,
                            points = teamPoints,
                            budget = teamBudget,
                            lastUpdated = date,
                            ownerId = user.userId
                        )
                        viewModel.editTeam(teamId, team)
                        Toast.makeText(
                            requireContext(),
                            context?.getString(R.string.update_successful),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        imageDialog.dismiss()
                    } else {
                        imageDialogBinding.tvError.text =
                            context?.getString(R.string.empty_single_field)
                        imageDialogBinding.tvError.visibility = View.VISIBLE
                    }
                }
                imageDialog.setCancelable(true)
                imageDialog.setOnCancelListener {
                    imageDialogBinding.tvError.text =
                        context?.getString(R.string.empty_single_field)
                    imageDialogBinding.tvError.visibility = View.GONE
                }
                imageDialog.setOnDismissListener {
                    viewModel.getUserWithTeam(user?.userId!!)
                }
                imageDialog.show()
            }

            btnUpdateInfo.setOnClickListener {
                accountDialog.setContentView(accountDialogBinding.root)
                accountDialogBinding.etName.setText(name)
                accountDialogBinding.etUsername.setText(username)
                accountDialogBinding.etTeamName.setText(teamName)
                accountDialogBinding.btnSaveAccount.setOnClickListener {
                    val _name = accountDialogBinding.etName.text.toString().trim()
                    val _username = accountDialogBinding.etUsername.text.toString().trim()
                    val _teamName = accountDialogBinding.etTeamName.text.toString().trim()

                    if (validate(_name, _username, _teamName)) {
                        val bundle = Bundle()
                        bundle.putBoolean(Enums.Result.REFRESH.name, true)
                        setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                        viewModel.editUser(
                            user?.userId!!,
                            User(user.userId, _name, _username, password, image)
                        )
                        val team = Team(
                            name = _teamName,
                            points = teamPoints,
                            budget = teamBudget,
                            lastUpdated = date,
                            ownerId = user.userId
                        )
                        viewModel.editTeam(teamId, team)
                        Toast.makeText(
                            requireContext(),
                            context?.getString(R.string.update_successful),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        accountDialog.dismiss()
                    } else {
                        accountDialogBinding.tvError.text = context?.getString(R.string.empty_field)
                        accountDialogBinding.tvError.visibility = View.VISIBLE
                    }
                }
                accountDialog.setCancelable(true)
                accountDialog.setOnCancelListener {
                    accountDialogBinding.tvError.text = context?.getString(R.string.empty_field)
                    accountDialogBinding.tvError.visibility = View.GONE
                }
                accountDialog.setOnDismissListener {
                    viewModel.getUserWithTeam(user?.userId!!)
                }
                accountDialog.show()
            }

            btnChangePassword.setOnClickListener {
                passwordDialog.setContentView(passwordDialogBinding.root)
                passwordDialogBinding.etPassword.setText(password)
                passwordDialogBinding.btnSavePassword.setOnClickListener {
                    val _password = passwordDialogBinding.etPassword.text.toString().trim()

                    if (validate(_password)) {
                        val bundle = Bundle()
                        bundle.putBoolean(Enums.Result.REFRESH.name, true)
                        setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                        viewModel.editUser(
                            user?.userId!!,
                            User(user.userId, name, username, _password, image)
                        )
                        val team = Team(
                            name = teamName,
                            points = teamPoints,
                            budget = teamBudget,
                            lastUpdated = date,
                            ownerId = user.userId
                        )
                        viewModel.editTeam(teamId, team)
                        Toast.makeText(
                            requireContext(),
                            context?.getString(R.string.update_successful),
                            Toast.LENGTH_SHORT
                        )
                            .show()
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
                    viewModel.getUserWithTeam(user?.userId!!)
                }
                passwordDialog.show()
            }
        }
    }

    private fun validate(vararg list: String): Boolean {
        for (field in list) {
            if (field.isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        val cursor = this.query(fileUri, null, null, null, null)

        cursor?.let {
            val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            return cursor.getString(name)
        }
        cursor?.close()

        return "Unknown"
    }
}