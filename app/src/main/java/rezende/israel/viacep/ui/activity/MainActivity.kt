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

        val intent = Intent(this, BuscaPorCepActivity::class.java)

        val botaoBuscaPorCep = binding.button

        botaoBuscaPorCep.setOnClickListener {
            startActivity(intent)
        }


    }
}