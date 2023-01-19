package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.databinding.FragmentHomeBinding
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.databinding.FragmentPickTeamBinding

class PickTeamFragment : Fragment() {
    private lateinit var binding: FragmentPickTeamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPickTeamBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}