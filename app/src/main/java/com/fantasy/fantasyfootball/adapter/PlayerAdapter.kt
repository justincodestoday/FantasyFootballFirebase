package com.fantasy.fantasyfootball.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fantasy.fantasyfootball.data.model.Player
import com.fantasy.fantasyfootball.databinding.PlayerCardBinding

class PlayerAdapter(var players: List<Player>, val onClick:(player: Player) -> Unit):
    RecyclerView.Adapter<PlayerAdapter.PlayerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val binding = PlayerCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        val player = players[position]
        holder.binding.run {
            tvName.text = player.name
            tvTeam.text = player.area.toString()
            tvPrice.text = player.price.toString() + " m"
            cvPlayerCard.setOnClickListener {
                onClick(player)
            }
        }
    }

    override fun getItemCount() = players.size

    fun setPlayer(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

    class PlayerHolder(val binding: PlayerCardBinding) : RecyclerView.ViewHolder(binding.root)
}

