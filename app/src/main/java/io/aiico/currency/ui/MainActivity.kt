package io.aiico.currency.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import io.aiico.currency.ui.list.CurrenciesFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    supportFragmentManager.findFragmentById(android.R.id.content) ?: supportFragmentManager.commit {
      add(android.R.id.content, CurrenciesFragment())
    }
  }
}
