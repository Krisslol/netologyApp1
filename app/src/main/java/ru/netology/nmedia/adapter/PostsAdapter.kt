package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.roundingNumbers

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
typealias OnRemoveListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener, onRemoveListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener,
    private val onRemoveListener: OnRemoveListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            numberLikes.text = roundingNumbers(post.likes.toLong())
            numberViews.text = roundingNumbers(post.views.toLong())
            numberShare.text = roundingNumbers(post.shares.toLong())
            like.setImageResource(
                if (post.likedByMe) R.drawable.filled_favorite else R.drawable.ic_baseline_favorite_border_24
            )
            //if (post.likedByMe) post.likes + 1 else post.likes - 1
            like.setOnClickListener{
                onLikeListener(post)
            }
            share.setOnClickListener{
                onShareListener(post)
            }
            menu.setOnClickListener{
               PopupMenu(it.context, it).apply {
                   inflate(R.menu.menu_options)
                   setOnMenuItemClickListener {
                       when(itemId){
                           R.id.remove.toLong() -> {
                               onRemoveListener(post)
                               true
                           }
                           else -> false
                       }

                   }
               }.show()
            }
        }
    }
}


class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}