package com.fantasy.fantasyfootball.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentTeamManagementBinding
import com.fantasy.fantasyfootball.dialog.ConfirmDialogs
import com.fantasy.fantasyfootball.service.AuthService
import com.fantasy.fantasyfootball.viewModel.TeamManagementViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamManagementFragment : BaseFragment<FragmentTeamManagementBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_team_management
    override val viewModel: TeamManagementViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view,savedInstanceState)

        val user = viewModel.fetchCurrentUser()

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                binding?.tvTeamName?.text = it.team.name
                binding?.tvPoints?.text = it.team.points.toString()
                binding?.tvBudget?.text = "£" + it.team.budget + "m"
            }
        }

        var teamId: Int = 0
        var teamBudget: Float = 0.0f
        val dialogInstance = ConfirmDialogs()
        viewModel.user.observe(viewLifecycleOwner) {
//            teamId = it.teamId!!
            teamBudget = it.team.budget
            val listOfPositions = mutableListOf<String>()
            it.team.players.forEach { player ->
                listOfPositions.add(player.position)
                val color = setShirtColor(player.color)
                setImageForPosition(player.position, color)
                setPlayerName(player.position, player.lastName)
            }

            binding?.gk?.setOnClickListener { _ ->
                val area = Enums.Area.Goalkeeper
                val position = Enums.Position.GK
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.lb?.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.LB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.lcb?.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.LCB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.rcb?.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.RCB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.rb?.setOnClickListener { _ ->
                val area = Enums.Area.Defender
                val position = Enums.Position.RB
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.lm?.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.LM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.lcm?.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.LCM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.rcm?.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.RCM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.rm?.setOnClickListener { _ ->
                val area = Enums.Area.Midfielder
                val position = Enums.Position.RM
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.ls?.setOnClickListener { _ ->
                val area = Enums.Area.Striker
                val position = Enums.Position.LS
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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

            binding?.rs?.setOnClickListener { _ ->
                val area = Enums.Area.Striker
                val position = Enums.Position.RS
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        dialogInstance.showDeleteDialog(
                            requireContext(),
                            context.getString(R.string.dialog_remove_title),
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
                        ) { _, _ ->
                            val bundle = Bundle()
                            bundle.putBoolean(Enums.Result.REFRESH.name, true)
                            setFragmentResult(Enums.Result.REMOVE_PLAYER_RESULT.name, bundle)
                            val updatedValue = teamBudget + player.price
                            viewModel.updateBudget(teamId, updatedValue)
                            player.fanPlayerId?.let { fanPlayerId ->
                                viewModel.removePlayer(
                                    fanPlayerId
                                )
                            }
                            NavHostFragment.findNavController(this).popBackStack()
                            NavHostFragment.findNavController(this)
                                .navigate(R.id.teamManagementFragment)
                            Toast.makeText(
                                requireContext(),
                                String.format(
                                    context.getString(R.string.removed_player_successful),
                                    player.lastName
                                ),
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
//                viewModel.getUserWithTeam(user.userId!!)
//                viewModel.getTeamWithPlayers(user.userId)
            }
        }
    }

    private fun setImageForPosition(position: String, color: Int) {
        when (position) {
            Enums.Position.GK.name -> binding?.gk?.setImageResource(color)
            Enums.Position.LB.name -> binding?.lb?.setImageResource(color)
            Enums.Position.LCB.name -> binding?.lcb?.setImageResource(color)
            Enums.Position.RCB.name -> binding?.rcb?.setImageResource(color)
            Enums.Position.RB.name -> binding?.rb?.setImageResource(color)
            Enums.Position.LM.name -> binding?.lm?.setImageResource(color)
            Enums.Position.LCM.name -> binding?.lcm?.setImageResource(color)
            Enums.Position.RCM.name -> binding?.rcm?.setImageResource(color)
            Enums.Position.RM.name -> binding?.rm?.setImageResource(color)
            Enums.Position.LS.name -> binding?.ls?.setImageResource(color)
            Enums.Position.RS.name -> binding?.rs?.setImageResource(color)
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
            Enums.ShirtColor.PURPLE -> R.drawable.shirt_pink
            else -> R.drawable.shirt2
        }
    }

    private fun setPlayerName(position: String, lastName: String) {
        when (position) {
            Enums.Position.GK.name -> binding?.goalKeeper?.text = lastName
            Enums.Position.LB.name -> binding?.leftBack?.text = lastName
            Enums.Position.LCB.name -> binding?.leftCenterBack?.text = lastName
            Enums.Position.RCB.name -> binding?.rightCenterBack?.text = lastName
            Enums.Position.RB.name -> binding?.rightBack?.text = lastName
            Enums.Position.LM.name -> binding?.leftMidfielder?.text = lastName
            Enums.Position.LCM.name -> binding?.leftCenterMid?.text = lastName
            Enums.Position.RCM.name -> binding?.rightCenterMid?.text = lastName
            Enums.Position.RM.name -> binding?.rightMidfielder?.text = lastName
            Enums.Position.LS.name -> binding?.leftStriker?.text = lastName
            Enums.Position.RS.name -> binding?.rightStriker?.text = lastName
        }
    }
}