package com.eltex.lab14.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eltex.lab14.R
import com.eltex.lab14.data.Post
import com.eltex.lab14.databinding.ActivityMainBinding
import com.eltex.lab14.utils.toast

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMain must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var post = Post(
            author = "Sergey",
            content = "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
            published = "11.05.22 11:21",
        )
        bindPost(binding, post)

        binding.bthLike.setOnClickListener {
            post = post.copy(likedByMe = !post.likedByMe)
            bindPost(binding, post)
        }


        binding.bthShare.setOnClickListener {
            toast(R.string.toastNotImplemented, false)
        }
        binding.bthPeopleOutline.setOnClickListener {
            toast(R.string.toastNotImplemented, true)
        }
        binding.imvMenu.setOnClickListener {
            toast(R.string.toastNotImplemented, true)
        }
    }
}

private fun bindPost(binding: ActivityMainBinding, post: Post) {
    binding.tvAuthor.text = post.author
    binding.tvContent.text = post.content
    binding.tvData.text = post.published

    binding.bthLike.setIconResource(
        if (post.likedByMe) {
            R.drawable.ic_baseline_favorite_24
        } else {
            R.drawable.ic_favorite_border_24
        }
    )
    binding.tvInitial.text = post.author.take(1)
    binding.bthLike.text = if (post.likedByMe) "1" else "0"
}