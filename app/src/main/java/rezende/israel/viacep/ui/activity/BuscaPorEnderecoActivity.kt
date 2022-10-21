package rezende.israel.viacep.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Response
import rezende.israel.viacep.databinding.ActivityBuscaPorEnderecoBinding
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.model.recyclerview.adapter.ListaCepsAdapter
import rezende.israel.viacep.webclient.CepResposta
import rezende.israel.viacep.webclient.RetrofitInicializador

class BuscaPorEnderecoActivity : AppCompatActivity() {

    private lateinit var ceps: List<Cep>

    private val binding by lazy {
        ActivityBuscaPorEnderecoBinding.inflate(layoutInflater)
    }

    private val adapter by lazy {
        ListaCepsAdapter(this)
    }

    private val cepService = RetrofitInicializador().cepService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val botaoVoltar = binding.fabVoltarEnd
        binding.recyclerview.adapter = adapter

        val logradouro = binding.inputLogradouro
        val cidade = binding.inputCidade
        val uf = binding.inputUf



        botaoVoltar.setOnClickListener {
            finish()
        }

        binding.fabConfirma.setOnClickListener {
            val call =
                cepService.buscaEnderecos(uf.toString(), cidade.toString(), logradouro.toString())
            lifecycleScope.launch(IO) {
                val resposta: Response<List<CepResposta>> = call.execute()
                resposta.body()?.let { cepResposta ->
                    ceps = cepResposta.map {
                        it.cepGet
                    }
                }
                launch(Main) {
                    ceps?.let {
                        adapter.atualiza(ceps = ceps)
                    }
                }
            }

        }
    }
}
