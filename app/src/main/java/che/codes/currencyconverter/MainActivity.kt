package che.codes.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import che.codes.currencyconverter.features.currencylist.CurrencyListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, CurrencyListFragment())
            commit()
        }
    }
}
