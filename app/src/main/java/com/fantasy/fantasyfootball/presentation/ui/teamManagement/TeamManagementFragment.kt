package com.fantasy.fantasyfootball.presentation.ui.teamManagement

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.*
import androidx.navigation.fragment.NavHostFragment
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentTeamManagementBinding
import com.fantasy.fantasyfootball.presentation.ui.base.BaseFragment
import com.fantasy.fantasyfootball.util.TeamUtil.setImageForPosition
import com.fantasy.fantasyfootball.util.TeamUtil.setPlayerName
import com.fantasy.fantasyfootball.util.TeamUtil.setShirtColor
import com.fantasy.fantasyfootball.presentation.ui.teamManagement.viewModel.TeamManagementViewModel
import com.fantasy.fantasyfootball.util.DialogUtil.showDeleteDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamManagementFragment : BaseFragment<FragmentTeamManagementBinding>() {
    override fun getLayoutResource(): Int = R.layout.fragment_team_management
    override val viewModel: TeamManagementViewModel by viewModels()

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        viewModel.user.observe(viewLifecycleOwner) {
            binding.apply {
                binding?.tvTeamName?.text = it.team.name
                binding?.tvPoints?.text = it.team.points.toString()
//                binding?.tvBudget?.text = "£" + it.team.budget + "m"

//                %1$s represents the currency symbol (string)
//                %2$.1f represents the budget value (floating-point number with one decimal place)
                binding?.tvBudget?.text = getString(
                    R.string.currency_format,
                    getString(R.string.currency_symbol),
                    it.team.budget
                )
            }
        }

        var teamBudget: Float
        viewModel.user.observe(viewLifecycleOwner) {
            teamBudget = it.team.budget
            val listOfPositions = mutableListOf<String>()
            it.team.players.forEach { fanPlayer ->
                val color = fanPlayer.color?.let { shirtColor -> setShirtColor(shirtColor) }
                fanPlayer.position?.let { playerPosition ->
                    listOfPositions.add(playerPosition)
                    if (color != null) binding?.let { imageButton ->
                        setImageForPosition(playerPosition, color, imageButton)
                    }
                    fanPlayer.lastName?.let { lastName ->
                        binding?.let { textView ->
                            setPlayerName(playerPosition, lastName, textView)
                        }
                    }
                }
            }

            binding?.gk?.setOnClickListener { _ ->
                val area = Enums.Area.Goalkeeper
                val position = Enums.Position.GK
                if (listOfPositions.any { pos -> pos == position.name }) {
                    val player =
                        it.team.players.single { fanPlayer -> fanPlayer.position == position.name }
                    context?.let { context ->
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
                        showDeleteDialog(
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
                            viewModel.updateBudget(updatedValue)
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
    }
}