package com.fantasy.fantasyfootball.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentOptionalBinding
import com.fantasy.fantasyfootball.util.Utils
import com.fantasy.fantasyfootball.util.Utils.getFileName
import com.fantasy.fantasyfootball.viewModel.OptionalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OptionalFragment : BaseFragment<FragmentOptionalBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_optional
    override val viewModel: OptionalViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    var imageUri: Uri? = null

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        binding?.tvChooseImage?.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            it?.let { uri ->
                binding?.apply {
                    ivImage.setImageURI(uri)
                    tvImageName.text = requireContext().contentResolver.getFileName(uri)
                    tvError.text =
                        context?.getString(R.string.missing_image)
                    tvError.visibility = View.GONE
                }
            }
        }

        val args: OptionalFragmentArgs by navArgs()

        binding?.btnSave?.setOnClickListener {
            val imageName = binding?.tvImageName?.text.toString()
            if (Utils.validate(imageName)) {
                lifecycleScope.launch {
                    viewModel.addInfo(args.email, imageUri)
                }
            } else {
                binding?.tvError?.text =
                    context?.getString(R.string.missing_image)
                binding?.tvError?.visibility = View.VISIBLE
            }
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.navigate.collect {
                binding?.run {
                    etTeamName.text?.clear()
                    tvError.text =
                        context?.getString(R.string.missing_image)
                    tvError.visibility = View.GONE
                }
                navController.popBackStack(R.id.main_nav_graph, true)
                navController.navigate(R.id.credentialsFragment)

                // if closed app without logging in, the app launched will log in automatically,
                // ask khayrul
                // viewModel.login()
//                val action = OptionalFragmentDirections.toHomeFragment()
//                navController.navigate(action)
            }
        }
    }
}