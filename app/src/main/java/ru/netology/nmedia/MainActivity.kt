package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.roundingNumbers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            numberLikes.text = roundingNumbers(post.likes.toLong())
            numberShare.text = roundingNumbers(post.shares.toLong())
            numberViews.text = roundingNumbers(post.views.toLong())

                if (post.likedByMe) {
                    like.setImageResource(R.drawable.filled_favorite)
                } else {
                    like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                numberLikes.text = roundingNumbers(post.views.toLong())

                binding.like.setOnClickListener {
                    post.likedByMe = !post.likedByMe
                    if (post.likedByMe) {
                        like.setImageResource(R.drawable.filled_favorite)
                        post.likes++
                    } else {
                        like.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        post.likes--
                    }
                    numberLikes.text = roundingNumbers(post.likes.toLong())
                }

                binding.share.setOnClickListener {
                    post.shares++
                    numberShare.text = roundingNumbers(post.shares.toLong())
                }
                binding.views.setOnClickListener {
                    post.views++
                    numberViews.text = roundingNumbers(post.views.toLong())
                }
            binding.root.setOnClickListener { println("root") }
            }
        }

    }

