package rezende.israel.viacep.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import rezende.israel.viacep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intentBuscaPorCep = Intent(this, BuscaPorCepActivity::class.java)
        val intentBuscaPorEndereco = Intent(this, BuscaPorEnderecoActivity::class.java)

        val botaoBuscaPorCep = binding.fabBuscaPorCep
        val botaoBuscaPorEndereco = binding.fabBuscaPorEndereco

        botaoBuscaPorCep.setOnClickListener {
            startActivity(intentBuscaPorCep)
        }

        botaoBuscaPorEndereco.setOnClickListener{
            startActivity(intentBuscaPorEndereco)
        }


    }
}