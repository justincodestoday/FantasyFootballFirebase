package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fantasy.fantasyfootball.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
//    private lateinit var filePickerLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentProfileBinding
//    private var bytes: ByteArray? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        filePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
//            it?.let { uri ->
//                binding.tvImageName.text = requireContext().contentResolver.getFileName(uri)
//
//                val inputStream = requireContext().contentResolver.openInputStream(uri)
//                bytes = inputStream?.readBytes()
//                Log.d("debugging", bytes.toString())
//                inputStream?.close()
//            }
//        }
//
//        binding.tvImageName.setOnClickListener {
//            filePickerLauncher.launch("image/*")
//        }
//    }
//    private fun ContentResolver.getFileName(fileUri: Uri): String {
//        val cursor = this.query(fileUri, null, null, null, null)
//
//        cursor?.let {
//            val name = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//            cursor.moveToFirst()
//            return cursor.getString(name)
//        }
//        cursor?.close()
//
//        return "Unknown"
    }
}