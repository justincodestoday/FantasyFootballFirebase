package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.data.model.FantasyPlayer
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PickPlayerFragment : BaseFragment<FragmentPickPlayerBinding>() {
    private lateinit var adapter: PlayerAdapter
    var currentFilter = ""
    override val viewModel: PickPlayerViewModel by viewModels()

    override fun getLayoutResource(): Int = R.layout.fragment_pick_player

    override fun onBindView(view: View, savedInstanceState: Bundle?) {
        super.onBindView(view, savedInstanceState)

        viewModel.fetchCurrentUser()

        val args: PickPlayerFragmentArgs by navArgs()
        val selectedArea = args.area
        val selectedPosition = args.position

        var teamBudget = 0.0f
        viewModel.user.observe(viewLifecycleOwner) { it ->
            teamBudget = it.team.budget
            val listOfLastNames = mutableListOf<String>()
            it.team.players.forEach { player ->
                player.lastName?.let { it1 -> listOfLastNames.add(it1) }
            }

            viewModel.getPlayersByArea(args.area, listOfLastNames)

            viewModel.players.observe(viewLifecycleOwner) { players ->
                adapter.setPlayer(players)
                binding?.progress?.visibility = View.GONE
            }

            binding?.search?.svSearch?.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    p0?.let {
                        currentFilter = it
                        refresh(args.area, it, listOfLastNames)
                    }
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let {
                        currentFilter = it
                        refresh(args.area, it, listOfLastNames)
                    }
                    return false
                }
            })

            binding?.search?.ivFilter?.setOnClickListener {
                val dialogView = layoutInflater.inflate(R.layout.filter_dialog, null)
                val filterDialog = Dialog(requireContext(), R.style.Filter_AlertDialog)
                filterDialog.setContentView(dialogView)
                filterDialog.setCancelable(true)
                filterDialog.show()
                filterDialog.findViewById<Button>(R.id.btn_filter_done).setOnClickListener {
                    val radioG1 = filterDialog.findViewById<RadioGroup>(R.id.radioGroup_order)
                    val radioG2 = filterDialog.findViewById<RadioGroup>(R.id.radioGroup_by)
                    val radioG1Checked = radioG1.checkedRadioButtonId
                    val radioG2Checked = radioG2.checkedRadioButtonId
                    val radioG1Button = filterDialog.findViewById<RadioButton>(radioG1Checked)
                    val radioG2Button = filterDialog.findViewById<RadioButton>(radioG2Checked)
                    val radioG1ButtonText = radioG1Button.text
                    val radioG2ButtonText = radioG2Button.text
                    sortRefresh(
                        radioG1ButtonText.toString(),
                        radioG2ButtonText.toString(),
                        selectedArea,
                        listOfLastNames
                    )
                    filterDialog.hide()
                }
            }
        }


        val layoutManager = LinearLayoutManager(requireContext())
        adapter = PlayerAdapter(emptyList()) {
            if (it.playerId != null) {
                if (teamBudget.compareTo(it.price) == 0) {
                    val updatedValue = teamBudget - it.price
                    viewModel.updateBudget(updatedValue)
                    viewModel.addPlayerToTeam(
                        FantasyPlayer(
                            firstName = it.firstName,
                            lastName = it.lastName,
                            team = it.team,
                            teamConst = it.teamConst,
                            price = it.price,
                            color = it.color,
                            position = selectedPosition,
                            isSet = true
                        )
                    )
                    val bundle = Bundle()
                    bundle.putBoolean(Enums.Result.REFRESH.name, true)
                    setFragmentResult(Enums.Result.ADD_PLAYER_RESULT.name, bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.added_player_successful)
                            ?.let { context -> String.format(context, it.lastName) },
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (teamBudget.compareTo(it.price) < 0) {
                    val snackBar = Snackbar.make(
                        binding!!.root,
                        "${context?.getString(R.string.insufficient_funds)}",
                        Snackbar.LENGTH_LONG
                    )
                    snackBar.setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.red_500)
                    )
                    snackBar.setAction("Hide") {
                        snackBar.dismiss()
                    }
                    snackBar.show()
                } else {
                    val updatedValue = teamBudget - it.price
                    viewModel.updateBudget(updatedValue)
                    viewModel.addPlayerToTeam(
                        FantasyPlayer(
                            firstName = it.firstName,
                            lastName = it.lastName,
                            team = it.team,
                            teamConst = it.teamConst,
                            price = it.price,
                            color = it.color,
                            position = selectedPosition,
                            isSet = true
                        )
                    )
                    val bundle = Bundle()
                    bundle.putBoolean(Enums.Result.REFRESH.name, true)
                    setFragmentResult(Enums.Result.ADD_PLAYER_RESULT.name, bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                    Toast.makeText(
                        requireContext(),
                        context?.getString(R.string.added_player_successful)
                            ?.let { context -> String.format(context, it.lastName) },
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding?.rvPlayers?.adapter = adapter
        binding?.rvPlayers?.layoutManager = layoutManager
    }

    private fun refresh(area: String, playerName: String, existingPlayer: List<String>) {
        if (playerName.isNotEmpty()) {
            viewModel.getPlayersBySearch(area, playerName, existingPlayer)
        } else {
            viewModel.getPlayersByArea(area, existingPlayer)
        }
    }

    private fun sortRefresh(order: String, by: String, area: String, existingPlayer: List<String>) {
        viewModel.sortPlayers(order, by, area, existingPlayer)
    }
}