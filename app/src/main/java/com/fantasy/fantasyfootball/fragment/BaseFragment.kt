package com.fantasy.fantasyfootball.fragment

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.viewModel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var navController: NavController
    lateinit var imageGallery: ActivityResultLauncher<String>
    abstract val viewModel: BaseViewModel
    var binding: T? = null
    var fileUri: Uri? = null

    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            viewModel.onViewCreated()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        imageGallery = registerForActivityResult(ActivityResultContracts.GetContent()) {
            fileUri = it
        }
        onBindView(view, savedInstanceState)
        onBindData(view)
    }

    open fun onBindView(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)
        lifecycleScope.launch {
            viewModel.error.collect {
                val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red_500)
                )
                snackbar.show()
            }
        }
    }

    open fun onBindData(view: View) {}

    fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormError.EMPTY_FIELD.name -> context?.getString(R.string.empty_field)
            Enums.FormError.MISSING_NAME.name -> context?.getString(R.string.missing_name)
            Enums.FormError.MISSING_TEAM_NAME.name -> context?.getString(R.string.missing_team_name)
            Enums.FormError.INVALID_USERNAME.name -> context?.getString(R.string.invalid_username)
            Enums.FormError.INVALID_PASSWORD.name -> context?.getString(R.string.invalid_password)
            Enums.FormError.PASSWORDS_NOT_MATCHING.name -> context?.getString(R.string.passwords_not_matching)
            Enums.FormError.USER_EXISTS.name -> context?.getString(R.string.user_already_exists)
            Enums.FormError.WRONG_CREDENTIALS.name -> context?.getString(R.string.wrong_credentials)
            Enums.FormSuccess.REGISTER_SUCCESSFUL.name -> context?.getString(R.string.register_successful)
            Enums.FormSuccess.LOGIN_SUCCESSFUL.name -> context?.getString(R.string.login_successful)
            Enums.FormSuccess.LOGOUT_SUCCESSFUL.name -> context?.getString(R.string.logout_successful)
            else -> context?.getString(R.string.unrecognised_error)
        }
    }
}