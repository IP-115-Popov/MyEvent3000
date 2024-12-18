package com.eltex.lab14.presentation.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.eltex.lab14.R
import com.eltex.lab14.presentation.ui.EdgeToEdgeHelper

class MainActivity : AppCompatActivity(R.layout.activity_main) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


//        if (intent.action == Intent.ACTION_SEND) { // Обязательно проверьте соответствует ли action ожидаемому
//            val text = intent.getStringExtra(Intent.EXTRA_TEXT)
//            intent.removeExtra(Intent.EXTRA_TEXT) // Удаляем, чтобы при повороте экрана снова не открывалась активити
//
//            text?.let {
//                val toolbarFragment = supportFragmentManager.findFragmentById(R.id.container) as ToolbarFragment
//
//                val navController = toolbarFragment.findNavController()
//
//                val bundle = Bundle().apply {
//                    putString(Constants.ID_NON_EXISTENT_EVENT, it)
//                }
//                navController!!.navigate(R.id.newPostFragment)
//            }
//        }
        enableEdgeToEdge()
        applyInserts()
    }

    private fun applyInserts() {
        EdgeToEdgeHelper.enableEdgeToEdge(findViewById(R.id.container))
    }
}