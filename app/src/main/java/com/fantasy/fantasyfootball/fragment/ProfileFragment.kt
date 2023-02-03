package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.content.ContentResolver
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.data.model.Team
import com.fantasy.fantasyfootball.data.model.User
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.ProfileViewModel
import java.util.Observer

class ProfileFragment : Fragment() {
    private lateinit var filePickerLauncher: ActivityResultLauncher<String>
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
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().let {
            authService = AuthService.getInstance(requireActivity().applicationContext)
            val user = authService.getAuthenticatedUser()

            user?.let {
                binding.tvName.text = it.name
                binding.tvUsername.text = it.username
            }

            if (user != null) {
                viewModel.getTeamName(user.userId!!)
            }

            viewModel.team.observe(viewLifecycleOwner) {
                binding.tvTeamName.text = it.name
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

            binding.edit.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.edit_profile_dialog, null)
                val filterDialog = Dialog(requireContext())
                filterDialog.setContentView(dialogView)
                filterDialog.setCancelable(true)
                val editText1 = filterDialog.findViewById<EditText>(R.id.et_name)
                val editText2 = filterDialog.findViewById<EditText>(R.id.et_username)
                val editText3 = filterDialog.findViewById<EditText>(R.id.et_team_name)
                val user = authService.getAuthenticatedUser()
//                val team = viewModel.getTeamName(user?.userId!!)

                user?.let {
                    editText1.setText(it.name)
                    editText2.setText(it.username)
//                    editText3.setText(it.)
                    filterDialog.show()
                    filterDialog.findViewById<Button>(R.id.btn_edit_query).setOnClickListener {

//                    val etText1 = Editable.Factory.getInstance().newEditable(editText1.text.toString())
//                    val etText2 = Editable.Factory.getInstance().newEditable(editText2.text.toString())
//                    val etText3 = Editable.Factory.getInstance().newEditable(editText3.text.toString())
                        profileRefresh(
                            Editable.Factory.getInstance().newEditable(editText1.text.toString()),
                            Editable.Factory.getInstance().newEditable(editText2.text.toString())
                        )
                        filterDialog.hide()
                    }
                }
            }

            binding.btnSave.setOnClickListener {
                if (user != null) {
                    viewModel.editUser(user.userId!!, User( image = bytes))
                }
            }
        }
    }

    fun profileRefresh(name: Editable, username: Editable) {
        val bundle = Bundle()
        bundle.putBoolean("refresh", true)
        setFragmentResult("edit_profile", bundle)
        val action = ProfileFragmentDirections.actionProfileFragmentSelf()
        NavHostFragment.findNavController(this).navigate(action)
        val user = authService.getAuthenticatedUser()
        user?.let {
            viewModel.editUser(
                it.userId!!,
                User(
                    name = name.toString(),
                    username = username.toString(),
                    password = it.password,
                    image = it.image
                )
            )
//            authService.updateUser(User(
//                name = name.toString(),
//                username = username.toString(),
//                password = it.password,
//                image = it.image
//            ))
        }
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