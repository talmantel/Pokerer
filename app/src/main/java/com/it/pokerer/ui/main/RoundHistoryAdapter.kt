package com.it.pokerer.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.it.pokerer.R
import com.it.pokerer.data.Round
import com.it.pokerer.utils.bindScore
import kotlinx.android.synthetic.main.round_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class RoundHistoryAdapter(private val context: Context) : RecyclerView.Adapter<RoundHistoryAdapter.RoundViewHolder>() {

    private var rounds: List<Round> = mutableListOf()

    var onLongClickListener: ((Round) -> Unit)? = null

    class RoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(ts: Date, gil: Int, tal: Int, shay: Int){
            itemView.time_text_view.text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault()).format(ts)
            itemView.gil_score_text_view.bindScore(gil)
            itemView.tal_score_text_view.bindScore(tal)
            itemView.shay_score_text_view.bindScore(shay)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoundViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.round_item, parent, false)
        return RoundViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RoundViewHolder, position: Int) {
        val round = rounds[position]
        holder.setData( SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).parse(round.ts)!!, round.gilWon, round.talWon, round.shayWon)
        holder.itemView.setOnLongClickListener { onLongClickListener?.invoke(round); true }
    }

    override fun getItemCount() = rounds.size

    fun setRounds(rounds: List<Round>){
        this.rounds = rounds
        notifyDataSetChanged()
    }

}
