package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun like(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun remove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun edit(post: Post) {
                viewModel.edit(post)
            }

            override fun share(post: Post) {
                viewModel.shareById(post.id)
            }

            override fun cancel(post: Post) {
                viewModel.cancel(post.id)
            }

        }
        )
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
                focusAndShowKeyboard()
            }
            with(binding) {
                group.visibility = View.VISIBLE
            }

            with(binding.editMessage) {
                setText(post.content)

            }

            with(binding) {
                content.apply {
                    requestFocus()
                    setText(post.content)
                    focusAndShowKeyboard()
                }

            }
        }

        binding.cancel.setOnClickListener {
            with(binding) {
                content.setText("")
                cancel.clearFocus()
                content.clearFocus()
                AndroidUtils.hideKeyboard(it)
                group.visibility = View.GONE
                // cancel.visibility = View.GONE
                // edit.visibility = View.GONE
                //   editMessage.visibility = View.GONE
                //  editText.visibility = View.GONE
            }
        }
        binding.save.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_content, Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            viewModel.changeContentAndSave(text)

            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(it)
            with(binding) {
                content.setText("")
                cancel.clearFocus()
                content.clearFocus()
                AndroidUtils.hideKeyboard(it)
                group.visibility = View.GONE
            }


        }

    }
}


