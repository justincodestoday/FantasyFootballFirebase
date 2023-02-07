package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.databinding.FragmentTeamManagementBinding
import com.fantasy.fantasyfootball.viewModel.TeamManagementViewModel
import android.widget.ImageView
import androidx.fragment.app.setFragmentResultListener
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.util.AuthService

class TeamManagementFragment : Fragment() {
    private lateinit var binding: FragmentTeamManagementBinding
    private val viewModel: TeamManagementViewModel by viewModels {
        TeamManagementViewModel.Provider(
            (requireContext().applicationContext as MainApplication).playerRepo,
            (requireContext().applicationContext as MainApplication).teamRepo,
            (requireContext().applicationContext as MainApplication).userRepo,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val position = PickTeamFragmentArgs.fromBundle(requireArguments()).position
//        setImageForPosition(position, binding)
        binding = FragmentTeamManagementBinding.inflate(layoutInflater)
        return binding.root
    }

    private var currentPosition: String? = null
    private var currentImageView: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        var _position = ""
//        NavHostFragment.findNavController(this).currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
//            "key"
//        )?.observe(viewLifecycleOwner) {
//            _position = it
//        }

        val authService = AuthService.getInstance(requireContext())
        val user = authService.getAuthenticatedUser()

        if (user != null) {
            viewModel.getUserWithTeam(user.userId!!)
            viewModel.getTeamWithPlayers(user.userId)
        }
        viewModel.userTeam.observe(viewLifecycleOwner) {
            binding.apply {
                binding.tvTeamName.text = it.team.name
                binding.tvPoints.text = it.team.points.toString()
                binding.tvBudget.text = "£" + it.team.budget.toString() + "m"
            }
        }

        viewModel.teamPlayer.observe(viewLifecycleOwner) {
            it.players.forEach { player ->
                val color = setShirtColor(player.color)
                setImageForPosition(player.position, color)
            }
        }

//        viewModel.teamWithPlayers.observe(viewLifecycleOwner) {
//            it.players.forEach { player ->
////                binding.tvPoints.text = player.name
//
//                if (player.area == Enums.Area.Goalkeeper) {
//                    val color = setShirtColor(Enums.ShirtColor.DARKRED)
//                    setImageForPosition("GK", color)
//                }
//            }
//        }

        binding.gk.setOnClickListener {
            val area = Enums.Area.Goalkeeper
            val position = Enums.Position.GK
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lb.setOnClickListener {
            val area = Enums.Area.Defender
            val position = Enums.Position.LB
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcb.setOnClickListener {
            val area = Enums.Area.Defender
            val position = Enums.Position.LCB
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcb.setOnClickListener {
            val area = Enums.Area.Defender
            val position = Enums.Position.RCB
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rb.setOnClickListener {
            val area = Enums.Area.Defender
            val position = Enums.Position.RB
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lm.setOnClickListener {
            val area = Enums.Area.Midfielder
            val position = Enums.Position.LM
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.lcm.setOnClickListener {
            val area = Enums.Area.Midfielder
            val position = Enums.Position.LCM
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rcm.setOnClickListener {
            val area = Enums.Area.Midfielder
            val position = Enums.Position.RCM
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rm.setOnClickListener {
            val area = Enums.Area.Midfielder
            val position = Enums.Position.RM
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.ls.setOnClickListener {
            val area = Enums.Area.Striker
            val position = Enums.Position.LS
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.rs.setOnClickListener {
            val area = Enums.Area.Striker
            val position = Enums.Position.RS
            val action =
                TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                    area.toString(), position.name
                )
            NavHostFragment.findNavController(this).navigate(action)
        }

        setFragmentResultListener(Enums.Result.ADD_PLAYER_RESULT.name) { _, result ->
            val refresh = result.getBoolean(Enums.Result.REFRESH.name)
            if (refresh && user != null) {
                viewModel.getUserWithTeam(user.userId!!)
            }
        }
    }

    private fun setImageForPosition(position: String, color: Int) {
        when (position) {
            "GK" -> binding.gk.setImageResource(color)
            "LB" -> binding.lb.setImageResource(color)
            "LCB" -> binding.lcb.setImageResource(color)
            "RCB" -> binding.rcb.setImageResource(color)
            "RB" -> binding.rb.setImageResource(color)
            "LM" -> binding.lm.setImageResource(color)
            "LCM" -> binding.lcm.setImageResource(color)
            "RCM" -> binding.rcm.setImageResource(color)
            "RM" -> binding.rm.setImageResource(color)
            "LS" -> binding.ls.setImageResource(color)
            "RS" -> binding.rs.setImageResource(color)
        }
    }

    private fun setShirtColor(color: Enums.ShirtColor): Int {
        return when (color) {
            Enums.ShirtColor.LIGHTRED -> R.drawable.shirt_light_red
            Enums.ShirtColor.YELLOW -> R.drawable.shirt_yellow
            else -> R.drawable.shirt2
        }
    }
}