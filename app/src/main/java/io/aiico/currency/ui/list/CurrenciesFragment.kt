package io.aiico.currency.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import io.aiico.currency.App
import io.aiico.currency.R
import io.aiico.currency.databinding.FragmentCurrenciesBinding
import io.aiico.currency.di.ConverterComponent
import io.aiico.currency.di.DaggerConverterComponent
import io.aiico.currency.ui.ConverterViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class CurrenciesFragment : Fragment(R.layout.fragment_currencies) {

  private var viewBinding: FragmentCurrenciesBinding? = null

  private val component: ConverterComponent by lazy {
    DaggerConverterComponent.factory()
      .create((requireActivity().application as App).appComponent)
  }
  private val viewModel by viewModels<ConverterViewModel> {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel> create(modelClass: Class<T>): T =
        component.factory.create() as T
    }
  }

  private val adapter = CurrencyAdapter(
    { code, amount -> },
    { code, amount -> }
  )

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewBinding = FragmentCurrenciesBinding.bind(view)
    viewBinding?.run {
      currencyRecyclerView.adapter = adapter
      lifecycleScope.launch {
        viewModel.state
          .map { state -> state.currencies }
          .onEach(adapter::submitList)
          .flowWithLifecycle(viewLifecycleOwner.lifecycle)
          .collect()
      }
    }
  }

  override fun onStop() {
    super.onStop()
    viewBinding = null
  }
}
