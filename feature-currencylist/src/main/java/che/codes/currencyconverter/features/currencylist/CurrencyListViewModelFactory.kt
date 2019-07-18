package che.codes.currencyconverter.features.currencylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import che.codes.currencyconverter.data.CurrencyRepository

class CurrencyListViewModelFactory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CurrencyListViewModel::class.java) -> {
                CurrencyListViewModel(repository) as T
            }
            else -> throw IllegalArgumentException(
                "${modelClass.simpleName} is an unknown view model type"
            )
        }
    }
}