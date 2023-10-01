package ru.netology.nmedia.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.launch
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.databinding.CardPostBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        val bindingCard = CardPostBinding.inflate(layoutInflater)
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
                val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
                    result ?: return@registerForActivityResult
                    viewModel.changeContent(result)
                    viewModel.save()
                }
                editPostLauncher.launch(post.content)
            }


            override fun share(post: Post) {
                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size >adapter.currentList.size
            adapter.submitList(posts){
                if(newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.edited
            viewModel.changeContent(result)
            viewModel.save()
        }
      //  editPostLauncher.launch(null)



        binding.fab.setOnClickListener {
            newPostLauncher.launch()
        }
      // edit.setOnClickListener{
     //              editPostLauncher.launch()
    //   }
    }
}

