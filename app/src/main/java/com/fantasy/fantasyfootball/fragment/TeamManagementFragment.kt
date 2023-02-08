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
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
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
        var leftStriker: FantasyPlayer? = null
        var rightStriker: FantasyPlayer? = null
        var leftMid: FantasyPlayer? = null
        var leftCM: FantasyPlayer? = null
        var rightCM: FantasyPlayer? = null
        var rightMid: FantasyPlayer? = null
        var leftBack: FantasyPlayer? = null
        var leftCB: FantasyPlayer? = null
        var rightCB: FantasyPlayer? = null
        var rightBack: FantasyPlayer? = null
        var goalKeeper: FantasyPlayer? = null
        val dialogInstance = ConfirmDialogs()
        viewModel.teamPlayer.observe(viewLifecycleOwner) {
            teamId = it.team.teamId!!
            teamBudget = it.team.budget
            val listOfPositions = mutableListOf<String>()
            it.players.forEach { player ->
                listOfPositions.add(player.position)
                val color = setShirtColor(player.color)
                setImageForPosition(player.position, color)
                setPlayerName(player.position, player.lastName)
//                if (player.position == Enums.Position.LS.name) {
//                    leftStriker = player
//                    binding.leftStriker.text = player.lastName
//                } else if (player.position == Enums.Position.RS.name) {
//                    rightStriker = player
//                    binding.rightStriker.text = player.lastName
//                } else if (player.position == Enums.Position.LM.name) {
//                    leftMid = player
//                    binding.leftMidfielder.text = player.lastName
//                } else if (player.position == Enums.Position.LCM.name) {
//                    leftCM = player
//                    binding.leftCenterMid.text = player.lastName
//                } else if (player.position == Enums.Position.RCM.name) {
//                    rightCM = player
//                    binding.rightCenterMid.text = player.lastName
//                } else if (player.position == Enums.Position.RM.name) {
//                    rightMid = player
//                    binding.leftBack.text = player.lastName
//                } else if (player.position == Enums.Position.LB.name) {
//                    leftBack = player
//                    binding.leftCenterBack.text = player.lastName
//                } else if (player.position == Enums.Position.LCB.name) {
//                    leftCB = player
//                    binding.leftCenterBack.text = player.lastName
//                } else if (player.position == Enums.Position.RCB.name) {
//                    leftCB = player
//                    binding.rightCenterBack.text = player.lastName
//                } else if (player.position == Enums.Position.RB.name) {
//                    leftCB = player
//                    binding.rightBack.text = player.lastName
//                } else if (player.position == Enums.Position.GK.name) {
////                    goalKeeper = player
//                    binding.goalKeeper.text = player.lastName
//                }
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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
                            String.format(
                                context.getString(R.string.dialog_remove_message),
                                player.lastName
                            )
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

    private fun setPlayerName(position: String, lastName: String) {
        when (position) {
            Enums.Position.GK.name -> binding.goalKeeper.text = lastName
            Enums.Position.LB.name -> binding.leftBack.text = lastName
            Enums.Position.LCB.name -> binding.leftCenterBack.text = lastName
            Enums.Position.RCB.name -> binding.rightCenterBack.text = lastName
            Enums.Position.RB.name -> binding.rightBack.text = lastName
            Enums.Position.LM.name -> binding.leftMidfielder.text = lastName
            Enums.Position.LCM.name -> binding.leftCenterMid.text = lastName
            Enums.Position.RCM.name -> binding.rightCenterMid.text = lastName
            Enums.Position.RM.name -> binding.rightMidfielder.text = lastName
            Enums.Position.LS.name -> binding.leftStriker.text = lastName
            Enums.Position.RS.name -> binding.rightStriker.text = lastName
        }
    }
}