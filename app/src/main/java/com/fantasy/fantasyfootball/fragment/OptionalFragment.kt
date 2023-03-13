package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentOptionalBinding
import com.fantasy.fantasyfootball.viewModel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionalFragment : BaseFragment<FragmentOptionalBinding>() {
    override val viewModel: BaseViewModel
        get() = TODO("Not yet implemented")
    override fun getLayoutResource(): Int = R.layout.fragment_optional

    override fun onBindData(view: View) {
        super.onBindData(view)
    }
}