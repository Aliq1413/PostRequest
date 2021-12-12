package com.example.postrequest
import UserItem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postrequest.databinding.ItemRowBinding
class UsersAdapter(private val usersList: ArrayList<UserItem>):
    RecyclerView.Adapter<UsersAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = usersList[position]
        holder.binding.apply {
            userTv.text = item.name
            locationTv.text = item.location
        }
    }

    override fun getItemCount() = usersList.size
}
