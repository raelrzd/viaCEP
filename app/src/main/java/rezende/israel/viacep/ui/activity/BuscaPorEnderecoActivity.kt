package rezende.israel.viacep.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import rezende.israel.viacep.databinding.ActivityBuscaPorEnderecoBinding
import rezende.israel.viacep.extension.cepsNull
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.model.recyclerview.adapter.ListaCepsAdapter
import rezende.israel.viacep.webclient.CepResposta
import rezende.israel.viacep.webclient.RetrofitInicializador

class BuscaPorEnderecoActivity : AppCompatActivity() {

    private var ceps: List<Cep>? = null

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

        binding.fabBackspace.setOnClickListener{
            logradouro.text = null
            cidade.text = null
            uf.text = null
            uf.clearFocus()
            ceps = cepsNull()
            lifecycleScope.launch(Main) {
                ceps.let {
                    adapter.atualiza(ceps = it)
                }
            }
        }

        binding.fabConfirma.setOnClickListener {
            if (uf.length() == 2 && cidade.length() >= 3 && logradouro.length() >= 3){
                val call =
                    cepService.buscaEnderecos(uf.text.toString(), cidade.text.toString(), logradouro.text.toString())
                lifecycleScope.launch(IO) {
                    launch(Main) {
                        binding.loading.visibility = View.VISIBLE
                        delay(2000)
                        binding.loading.visibility = View.INVISIBLE
                    }
                    val resposta: Response<List<CepResposta>> = call.execute()
                    resposta.body()?.let { cepResposta ->
                        ceps = cepResposta.map {
                            it.cepGet
                        }
                    }
                    launch(Main) {
                        ceps?.let {
                            adapter.atualiza(ceps = ceps!!)
                        }
                    }
                }
            } else {
                Toast.makeText(
                    this@BuscaPorEnderecoActivity,
                    "Falha ao buscar CEP. Verifique se os valores inseridos são válidos! ⚠️",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
