package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.databinding.PlayerCardBinding
import com.fantasy.fantasyfootball.util.Utils.update

class PlayerAdapter(var players: List<Player>, val onClick: (player: Player) -> Unit) :
    RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val binding = PlayerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val player = players[position]
        holder.binding.run {
            tvName.text = player.name
            tvTeam.text = player.team
            tvPosition.text = "(${player.area})"
            tvPrice.text = player.price.toString() + " m"
            cvPlayerCard.setOnClickListener {
                onClick(player)
            }
        }
    }

    override fun getItemCount() = players.size

    fun setPlayer(players: List<Player>) {
        val oldItems = this.players
        this.players = players as MutableList<Player>
        if (oldItems.isEmpty()) {
            update(emptyList(), players) { player1, player2 ->
                player1.playerId == player2.playerId
            }
        } else {
            update(oldItems, players) { player1, player2 ->
                player1.playerId == player2.playerId
            }
        }
    }

    class PlayerHolder(val binding: PlayerCardBinding) : RecyclerView.ViewHolder(binding.root)
}

