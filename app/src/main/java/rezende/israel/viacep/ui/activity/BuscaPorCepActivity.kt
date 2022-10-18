package rezende.israel.viacep.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import rezende.israel.viacep.databinding.ActivityBuscaPorCepBinding

class BuscaPorCepActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBuscaPorCepBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}