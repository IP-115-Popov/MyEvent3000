package com.eltex.lab14.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.lab14.R
import com.eltex.lab14.data.Post
import com.eltex.lab14.databinding.ActivityMainBinding
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.InMemoryPostRepository
import com.eltex.lab14.utils.toast
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMain must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel by viewModels<EventViewModel> {
            viewModelFactory {
                addInitializer(EventViewModel::class) {
                    EventViewModel(InMemoryPostRepository())
                }
            }
        }


        viewModel.state
            .onEach {
                bindPost(binding, it.post)
            }
            .launchIn(lifecycleScope)



        binding.bthLike.setOnClickListener {
            viewModel.like()
        }


        binding.bthShare.setOnClickListener {
            toast(R.string.toastNotImplemented, false)
        }
        binding.bthPeopleOutline.setOnClickListener {
            viewModel.participate()
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
    binding.bthPeopleOutline.text = if (post.visitByMe) "1" else "0"
}