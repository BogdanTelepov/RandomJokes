package ru.btelepov.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.joke_item.view.*
import ru.btelepov.app.R
import ru.btelepov.app.models.JokeItem

class JokeListAdapter : RecyclerView.Adapter<JokeListAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<JokeItem>() {
        override fun areItemsTheSame(
            oldItem: JokeItem,
            newItem: JokeItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: JokeItem,
            newItem: JokeItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.joke_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentJoke = differ.currentList[position]
        holder.itemView.apply {
            item_setupTv.text = currentJoke.setup.trim()
            item_punchlineTv.text = currentJoke.punchline.trim()
            cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_anim))
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}