package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.viewModel.BaseViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
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
                val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(requireContext(), R.color.red_500)
                )
                snackbar.show()
            }
        }
    }

    open fun onBindData(view: View) {}
}