package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentSelectPlayerBinding

class SelectPlayerFragment : Fragment() {
    private lateinit var binding: FragmentSelectPlayerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectPlayerBinding.inflate(layoutInflater)
        return binding.root
    }
}