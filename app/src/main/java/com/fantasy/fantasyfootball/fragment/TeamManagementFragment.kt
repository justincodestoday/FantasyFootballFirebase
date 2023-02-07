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
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.dialog.ConfirmDialogs
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
        binding = FragmentTeamManagementBinding.inflate(layoutInflater)
        return binding.root
    }

    private var currentPosition: String? = null
    private var currentImageView: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        var teamId: Int
        var teamBudget: Float
        val dialogInstance = ConfirmDialogs()
        viewModel.teamPlayer.observe(viewLifecycleOwner) {
            teamId = it.team.teamId!!
            teamBudget = it.team.budget
            val listOfPositions = mutableListOf<String>()
            it.players.forEach { player ->
                listOfPositions.add(player.position)
                val color = setShirtColor(player.color)
                setImageForPosition(player.position, color)
            }

            binding.gk.setOnClickListener { _ ->
                val area = Enums.Area.Goalkeeper
                val position = Enums.Position.GK
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.lb.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.LB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.lcb.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.LCB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.rcb.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.RCB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.rb.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.RB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.lm.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.LM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.lcm.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.LCM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.rcm.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.RCM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.rm.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.RM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.ls.setOnClickListener { _ ->
                val area = Enums.Area.Striker
                val position = Enums.Position.LS
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }

            binding.rs.setOnClickListener { _ ->
                val area = Enums.Area.Striker
                val position = Enums.Position.RS
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            context.getString(R.string.dialog_remove_message)
                        ) { _, _ ->
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            viewModel.getUserWithTeam(user?.userId!!)
                            viewModel.getTeamWithPlayers(user.userId)
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                context.getString(R.string.removed_player_successful),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                } else {
                    val action =
                        TeamManagementFragmentDirections.actionTeamManagementFragmentToPickPlayerFragment(
                            area.toString(), position.name
                        )
                    NavHostFragment.findNavController(this).navigate(action)
                }
            }
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
            Enums.Position.GK.name -> binding.gk.setImageResource(color)
            Enums.Position.LB.name -> binding.lb.setImageResource(color)
            Enums.Position.LCB.name -> binding.lcb.setImageResource(color)
            Enums.Position.RCB.name -> binding.rcb.setImageResource(color)
            Enums.Position.RB.name -> binding.rb.setImageResource(color)
            Enums.Position.LM.name -> binding.lm.setImageResource(color)
            Enums.Position.LCM.name -> binding.lcm.setImageResource(color)
            Enums.Position.RCM.name -> binding.rcm.setImageResource(color)
            Enums.Position.RM.name -> binding.rm.setImageResource(color)
            Enums.Position.LS.name -> binding.ls.setImageResource(color)
            Enums.Position.RS.name -> binding.rs.setImageResource(color)
        }
    }

    private fun setShirtColor(color: Enums.ShirtColor): Int {
        return when (color) {
            Enums.ShirtColor.LIGHTRED -> R.drawable.shirt_light_red
            Enums.ShirtColor.DARKRED -> R.drawable.shirt_dark_red
            Enums.ShirtColor.LIGHTBLUE -> R.drawable.shirt_light_blue
            Enums.ShirtColor.DARKBLUE -> R.drawable.shirt_dark_blue
            Enums.ShirtColor.YELLOW -> R.drawable.shirt_yellow
            Enums.ShirtColor.WHITE -> R.drawable.shirt_white
            Enums.ShirtColor.BLACK -> R.drawable.shirt_black
            else -> R.drawable.shirt2
        }
    }
}