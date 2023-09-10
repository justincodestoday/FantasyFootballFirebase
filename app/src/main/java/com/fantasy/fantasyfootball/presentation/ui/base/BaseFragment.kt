package com.fantasy.fantasyfootball.presentation.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.util.AlertUtil.enumToString
import com.fantasy.fantasyfootball.presentation.ui.base.viewModel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    lateinit var navController: NavController
    var binding: T? = null
    abstract val viewModel: BaseViewModel
    abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.onViewCreated()
            }
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
            viewModel.success.collect {
                val msg = enumToString(it, requireContext())
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.error.collect {
                val msg = enumToString(it, requireContext())
                val snackBar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                snackBar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red_500)
                )
                snackBar.setAction("Hide") {
                    snackBar.dismiss()
                }
                snackBar.show()
            }
        }
    }

    open fun onBindData(view: View) {}
}