package com.eltex.lab14.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.lab14.Constants
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

        val id = intent.getLongExtra(Intent.EXTRA_INDEX, -1)
        val oldContent: String = intent.getStringExtra(Intent.EXTRA_TEXT) ?: ""

        binding.etvContent.setText(Editable.Factory.getInstance().newEditable(oldContent))

        binding.toolbar.menu.findItem(R.id.save_post).setOnMenuItemClickListener {
            if (id == Constants.ID_NON_EXISTENT_EVENT) addEvent()
            else updatePost(id)

            true
        }

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        applyInserts()
    }

    private fun updatePost(id: Long) {
        val content = binding.etvContent.text?.toString().orEmpty()

        if (content.isNotBlank()) {
            val intent =
                Intent().putExtra(Intent.EXTRA_INDEX, id).putExtra(Intent.EXTRA_TEXT, content)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            toast(R.string.content_is_epmty, false)
        }
    }

    private fun addEvent() {
        val content = binding.etvContent.text?.toString().orEmpty()

        if (content.isNotBlank()) {
            val intent = Intent().putExtra(Intent.EXTRA_INDEX, Constants.ID_NON_EXISTENT_EVENT)
                .putExtra(Intent.EXTRA_TEXT, content)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            toast(R.string.content_is_epmty, false)
        }
    }

    private fun applyInserts() {
        enableEdgeToEdge()
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(android.R.id.content))
    }
}