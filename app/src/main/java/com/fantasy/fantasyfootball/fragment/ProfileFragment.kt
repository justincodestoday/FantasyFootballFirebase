package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.EditProfileDialogBinding
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<String>
    private lateinit var dialogBinding: EditProfileDialogBinding
    private lateinit var binding: FragmentProfileBinding
    private lateinit var authService: AuthService
    private var bytes: ByteArray? = null
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

        dialogBinding = EditProfileDialogBinding.inflate(layoutInflater)
        val dialog = Dialog(requireContext(), R.style.Custom_AlertDialog)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        authService = AuthService.getInstance(requireActivity().applicationContext)
        val user = authService.getAuthenticatedUser()
        if (user != null) {
            viewModel.getUserWithTeam(user.userId!!)
        }
        var name = ""
        var username = ""
        var password = ""
//        var image = ByteArray(0)
        var teamId = 0
        var teamName = ""
        var teamPoints = 0
        var teamBudget = 0f
        var lastUpdated = ""
        viewModel.userTeam.observe(viewLifecycleOwner) {
            binding.apply {
                tvName.text = it.user.name
                tvUsername.text = it.user.username
                tvTeamName.text = it.team.name

                name = it.user.name.toString()
                username = it.user.username.toString()
                password = it.user.password.toString()
//                image = it.user.image!!
                teamId = it.team.teamId!!
                teamName = it.team.name.toString()
                teamPoints = it.team.points
                teamBudget = it.team.remainingBudget
                lastUpdated = it.team.lastUpdated.toString()
            }
        }

        binding.apply {
            btnEdit.setOnClickListener {
                dialog.setContentView(dialogBinding.root)
                dialogBinding.etName.setText(name)
                dialogBinding.etUsername.setText(username)
                dialogBinding.etTeamName.setText(teamName)
                dialogBinding.btnEdit.setOnClickListener {
                    val _name = dialogBinding.etName.text.toString().trim()
                    val _username = dialogBinding.etUsername.text.toString().trim()
                    val _teamName = dialogBinding.etTeamName.text.toString().trim()

                    val timeZone = TimeZone.getTimeZone("GMT+8")
                    val calendar = Calendar.getInstance()
                    val dateFormat: DateFormat =
                        SimpleDateFormat("LLLL d yyyy, HH:mm a (z)", Locale.ENGLISH)
                    dateFormat.timeZone = timeZone
                    val date = dateFormat.format(calendar.time)

                    if (validate(_name, _username, _teamName)) {
                        val bundle = Bundle()
                        bundle.putBoolean(Enums.Result.REFRESH.name, true)
                        setFragmentResult(Enums.Result.EDIT_PROFILE_RESULT.name, bundle)
                        viewModel.editUser(
                            user?.userId!!,
                            User(user.userId, _name, _username, password)
                        )
                        val team = Team(
                            name = _teamName,
                            points = teamPoints,
                            remainingBudget = teamBudget,
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
                        dialog.dismiss()
                    } else {
                        dialogBinding.tvError.text = context?.getString(R.string.empty_field)
                        dialogBinding.tvError.visibility = View.VISIBLE
                    }
                }
                dialog.setCancelable(true)
                dialog.setOnCancelListener {
                    dialogBinding.tvError.text = context?.getString(R.string.empty_field)
                    dialogBinding.tvError.visibility = View.GONE
                }
                dialog.setOnDismissListener {
                    viewModel.getUserWithTeam(user?.userId!!)
                }
                dialog.show()
            }
        }

        filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            it?.let { uri ->
                binding.tvImageName.text = requireContext().contentResolver.getFileName(uri)

                val inputStream = requireContext().contentResolver.openInputStream(uri)
                bytes = inputStream?.readBytes()
                Log.d("debugging", bytes.toString())
                inputStream?.close()
            }
        }

        binding.tvChooseImage.setOnClickListener {
            filePickerLauncher.launch("image/*")
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size)
            binding.image.setImageBitmap(bitmap)
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

//    fun profileRefresh(name: Editable, username: Editable) {
//        val bundle = Bundle()
//        bundle.putBoolean("refresh", true)
//        setFragmentResult("edit_profile", bundle)
//        val action = ProfileFragmentDirections.actionProfileFragmentSelf()
//        NavHostFragment.findNavController(this).navigate(action)
//        val user = authService.getAuthenticatedUser()
//        user?.let {
//            viewModel.editUser(
//                it.userId!!,
//                User(
//                    name = name.toString(),
//                    username = username.toString(),
//                    password = it.password,
//                    image = it.image
//                )
//            )
//        }
//    }

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