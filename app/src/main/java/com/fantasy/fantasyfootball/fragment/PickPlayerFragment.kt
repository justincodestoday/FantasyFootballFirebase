package com.fantasy.fantasyfootball.fragment


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fantasy.fantasyfootball.MainApplication
import com.fantasy.fantasyfootball.R
import com.fantasy.fantasyfootball.adapter.PlayerAdapter
import com.fantasy.fantasyfootball.constant.Enums
import com.fantasy.fantasyfootball.databinding.FragmentPickPlayerBinding
import com.fantasy.fantasyfootball.viewModel.PickPlayerViewModel


class PickPlayerFragment : Fragment() {
    private lateinit var adapter: PlayerAdapter
    private lateinit var binding: FragmentPickPlayerBinding
    var currentFilter = ""
    private val viewModel: PickPlayerViewModel by viewModels {
        PickPlayerViewModel.Provider((requireContext().applicationContext as MainApplication).playerRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPickPlayerBinding.inflate(layoutInflater)
        // val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
//        bottomNav.visibility = View.VISIBLE
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
//        toolbar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        val args: PickPlayerFragmentArgs by navArgs()
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

        binding.ivFilter.setOnClickListener {
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
                sortRefresh(radioG1ButtonText.toString(), radioG2ButtonText.toString())
                filterDialog.hide()
            }
        }
    }

    fun setupAdapter() {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = PlayerAdapter(emptyList()) {
            val action = PickPlayerFragmentDirections.actionPickPlayerFragmentToPickTeamFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvPlayers.adapter = adapter
        binding.rvPlayers.layoutManager = layoutManager
    }

    fun refresh(area: String, playername: String) {
        if(playername.isNotEmpty()) {
            viewModel.getPlayersBySearch(area, playername)
        } else {
            viewModel.getPlayersByArea(area)
        }
    }

    fun sortRefresh(order: String, by: String) {
        viewModel.sortPlayers(order, by)
    }

    companion object {
        private var pickPlayerFragmentInstance: PickPlayerFragment? = null
        fun getInstance(): PickPlayerFragment {
            if (pickPlayerFragmentInstance == null) {
                pickPlayerFragmentInstance = PickPlayerFragment()
            }
            return pickPlayerFragmentInstance!!
        }
    }
}