package rezende.israel.viacep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rezende.israel.viacep.databinding.ActivityBuscaPorEnderecoBinding

class BuscaPorEnderecoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBuscaPorEnderecoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val botaoVoltar = binding.fabVoltarEnd

        botaoVoltar.setOnClickListener{
            finish()
        }

    }
}