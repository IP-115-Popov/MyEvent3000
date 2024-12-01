package com.eltex.lab14.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.eltex.lab14.Constants
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.ActivityMainBinding
import com.eltex.lab14.db.AppDb
import com.eltex.lab14.presentation.adapters.EventAdapter
import com.eltex.lab14.presentation.adapters.OffsetDecoration
import com.eltex.lab14.presentation.ui.EdgeToEdgeHelper
import com.eltex.lab14.presentation.viewmodel.EventViewModel
import com.eltex.lab14.repository.SqliteEventsRepository
import com.eltex.lab14.utils.share
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        if (intent.action == Intent.ACTION_SEND) { // Обязательно проверьте соответствует ли action ожидаемому
//            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
//            intent.removeExtra(Intent.EXTRA_TEXT) // Удаляем, чтобы при повороте экрана снова не открывалась активити
//
//            text?.let {
//                launchNewEventActivity(Constants.ID_NON_EXISTENT_EVENT, text)
//            }
//        }
        enableEdgeToEdge()
        applyInserts()
    }

    private fun applyInserts() {
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(R.id.container))
    }
}