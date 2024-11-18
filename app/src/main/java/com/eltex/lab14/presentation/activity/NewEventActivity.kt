package com.eltex.lab14.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.lab14.R
import com.eltex.lab14.databinding.ActivityNewEventBinding
import com.eltex.lab14.presentation.ui.EdgeToEdgeHelper
import com.eltex.lab14.utils.toast

class NewEventActivity : AppCompatActivity() {
    private var _binding: ActivityNewEventBinding? = null
    private val binding: ActivityNewEventBinding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityNewEvent must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.menu.findItem(R.id.save_post).setOnMenuItemClickListener {
            val content = binding.etvContent.text?.toString().orEmpty()

            if (content.isNotBlank()) {
                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content))
                finish()
            } else {
                toast(R.string.content_is_epmty, false)
            }

            true
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        applyInserts()
    }

    private fun applyInserts() {
        enableEdgeToEdge()
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))
    }
}