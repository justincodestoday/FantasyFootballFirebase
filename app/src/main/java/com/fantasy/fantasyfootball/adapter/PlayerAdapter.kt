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
//                setImageForPosition(player.position.toString())
            }
        }
    }

    override fun getItemCount() = players.size

    fun setPlayer(players: List<Player>) {
        this.players = players
        notifyDataSetChanged()
    }

//    fun setImageForPosition(position: String): View? {
//        when (position) {
//            "GK" -> setImageResource(R.drawable.ic_delete)
//            "LB" -> binding.lb.setImageResource(R.drawable.ic_delete)
//            "LCB" -> binding.lcb.setImageResource(R.drawable.ic_delete)
//            "RCB" -> binding.rcb.setImageResource(R.drawable.ic_delete)
//            "RB" -> binding.rb.setImageResource(R.drawable.ic_delete)
//            "LM" -> binding.lm.setImageResource(R.drawable.ic_delete)
//            "LCM" -> binding.lcm.setImageResource(R.drawable.ic_delete)
//            "RCM" -> binding.rcm.setImageResource(R.drawable.ic_delete)
//            "RM" -> binding.rm.setImageResource(R.drawable.ic_delete)
//            "LS" -> binding.ls.setImageResource(R.drawable.ic_delete)
//            "RS" -> binding.rs.setImageResource(R.drawable.ic_delete)
//        }
//    }


    class PlayerHolder(val binding: PlayerCardBinding) : RecyclerView.ViewHolder(binding.root)
}

