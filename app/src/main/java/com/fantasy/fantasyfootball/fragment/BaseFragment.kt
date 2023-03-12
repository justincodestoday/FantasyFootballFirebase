package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.viewModel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment<T: ViewDataBinding>: Fragment() {
    lateinit var navController: NavController
    abstract val viewModel: BaseViewModel
    var binding: T? = null

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
        onBindView(view, savedInstanceState)
        onBindData(view)
    }

    open fun onBindView(view: View, savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(view)
        lifecycleScope.launch {
            viewModel.error.collect {
                var snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
                snackbar.show()
            }
        }
    }
    open fun onBindData(view: View) {}

    open fun enumToString(type: String?): String? {
        return when (type) {
            Enums.FormError.EMPTY_FIELD.name -> context?.getString(R.string.empty_field)
            Enums.FormError.INVALID_USERNAME.name -> context?.getString(R.string.no_user)
            Enums.FormError.INVALID_PASSWORD.name -> context?.getString(R.string.incorrect_password)
            Enums.FormError.WRONG_CREDENTIALS.name -> context?.getString(R.string.wrong_credentials)
            Enums.FormSuccess.LOGIN_SUCCESSFUL.name -> context?.getString(R.string.login_successful)
            else -> context?.getString(R.string.nothing)
        }
    }
}