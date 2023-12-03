package com.example.homework14

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homework14.databinding.BmwLayoutBinding
import com.example.homework14.databinding.MercedesLayoutBinding

class FragmentAdapter(private val onDeleteClickListener: (Data) -> Unit) :
    ListAdapter<Data, RecyclerView.ViewHolder>(DataDiffCallback()) {

    private class DataDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    inner class BmwViewHolder(private val binding: BmwLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var data: Data

        init {
            binding.button.setOnClickListener {
                onDeleteClickListener.invoke(data)
            }
        }

        fun bind() {
            data = currentList[adapterPosition]
            with(binding) {
                image.setImageResource(data.image)
                text.text = data.text
            }
        }
    }

    inner class MercedesViewHolder(private val binding: MercedesLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var data: Data

        init {
            binding.button.setOnClickListener {
                onDeleteClickListener.invoke(data)
            }
        }

        fun bind() {
            data = currentList[adapterPosition]
            with(binding) {
                image.setImageResource(data.image)
                text.text = data.text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_BMW -> {
                val binding = BmwLayoutBinding.inflate(inflater, parent, false)
                BmwViewHolder(binding)
            }

            VIEW_TYPE_MERCEDES -> {
                val binding = MercedesLayoutBinding.inflate(inflater, parent, false)
                MercedesViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BmwViewHolder -> holder.bind()
            is MercedesViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position].image) {
            R.drawable.bmw -> VIEW_TYPE_BMW
            R.drawable.mercedes -> VIEW_TYPE_MERCEDES
            else -> throw IllegalArgumentException("Invalid image resource")
        }
    }

    companion object {
        private const val VIEW_TYPE_BMW = 1
        private const val VIEW_TYPE_MERCEDES = 2
    }
}
