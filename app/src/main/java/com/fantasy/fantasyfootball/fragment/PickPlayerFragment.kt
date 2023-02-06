package com.fantasy.fantasyfootball.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.util.AuthService
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel

class PickPlayerFragment : Fragment() {
    private lateinit var adapter: PlayerAdapter
    private lateinit var binding: FragmentPickPlayerBinding
    private lateinit var authService: AuthService
    var currentFilter = ""
    private val viewModel: PickPlayerViewModel by viewModels {
        PickPlayerViewModel.Provider(
            (requireContext().applicationContext as MainApplication).playerRepo,
            (requireContext().applicationContext as MainApplication).teamRepo,
            (requireContext().applicationContext as MainApplication).userRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authService = AuthService.getInstance(requireActivity().applicationContext)
        val user = authService.getAuthenticatedUser()

        val args: PickPlayerFragmentArgs by navArgs()
        val selectedArea = args.area
        val selectedPosition = args.position

        setupAdapter(selectedPosition)

        if (user != null) {
            viewModel.getUserWithTeam(user.userId!!)
            viewModel.userTeam.observe(viewLifecycleOwner) {
            }
        }

        viewModel.getPlayersByArea(args.area)

        viewModel.players.observe(viewLifecycleOwner) { players ->
            adapter.setPlayer(players)
        }

        binding.search.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    currentFilter = it
                    refresh(args.area, it)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    currentFilter = it
                    refresh(args.area, it)
                }
                return false
            }
        })

        binding.search.ivFilter.setOnClickListener {
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
                    selectedArea
                )
                filterDialog.hide()
            }
        }
    }

    private fun setupAdapter(selectedPosition: String) {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = PlayerAdapter(emptyList()) {
            NavHostFragment.findNavController(this).previousBackStackEntry?.savedStateHandle?.set("key", selectedPosition)
            NavHostFragment.findNavController(this).popBackStack()
            val bundle = Bundle()
            if (it.playerId != null) {
                bundle.putFloat(Enums.Result.PLAYER_PRICE.name, it.price)
                bundle.putInt(Enums.Result.PLAYER_ID.name, it.playerId)
                bundle.putBoolean(Enums.Result.REFRESH.name, true)
                bundle.putString(Enums.Result.POSITION_BUTTON.name, selectedPosition)
                setFragmentResult(Enums.Result.ADD_PLAYER_RESULT.name, bundle)
//                viewModel.addPlayer(TeamsPlayersCrossRef(teamId, it.playerId))
                Toast.makeText(
                    requireContext(),
                    context?.getString(R.string.added_player_successful),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
//            (requireContext() as PickTeamFragment).setImageForPosition(position = "GK")
        }
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = layoutManager
    }

    private fun refresh(area: String, playerName: String) {
        if (playerName.isNotEmpty()) {
            viewModel.getPlayersBySearch(area, playerName)
        } else {
            viewModel.getPlayersByArea(area)
        }
    }

    private fun sortRefresh(order: String, by: String, area: String) {
        viewModel.sortPlayers(order, by, area)
    }
}