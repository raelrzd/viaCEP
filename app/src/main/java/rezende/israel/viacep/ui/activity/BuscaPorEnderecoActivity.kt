package rezende.israel.viacep.ui.activity

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import rezende.israel.viacep.databinding.ActivityBuscaPorEnderecoBinding
import rezende.israel.viacep.extension.cepsNull
import rezende.israel.viacep.model.CepResposta
import rezende.israel.viacep.ui.recyclerview.adapter.ListaCepsAdapter
import rezende.israel.viacep.ui.viewmodel.ActivitiesDeBuscaViewModel

class BuscaPorEnderecoActivity : AppCompatActivity() {

    private val viewModel: ActivitiesDeBuscaViewModel by inject()
    private val binding by lazy {
        ActivityBuscaPorEnderecoBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaCepsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter
        val logradouro = binding.inputLogradouro
        val cidade = binding.inputCidade
        val uf = binding.inputUf
        configuraBotaoVoltar()
        configuraBotaoVoltar(logradouro, cidade, uf)
        configuraBotaoConfirma(uf, cidade, logradouro)
    }

    private fun configuraBotaoConfirma(
        uf: TextInputEditText,
        cidade: TextInputEditText,
        logradouro: TextInputEditText,
    ) {
        binding.fabConfirma.setOnClickListener {
            lifecycleScope.launch {
                viewModel.buscaEndereco(
                    uf.text.toString(),
                    cidade.text.toString(),
                    logradouro.text.toString(),
                    completed = {
                        val enderecoEncontrado = it.value
                        if (uf.length() == 2 && cidade.length() >= 3 && logradouro.length() >= 3) {
                            showLoading(show = true)
                            if (enderecoEncontrado?.dado != null) {
                                atualizaAdapter(enderecoEncontrado.dado)
                            } else {
                                showMessage(enderecoEncontrado?.erro)
                            }
                        } else {
                            showMessage(enderecoEncontrado?.erro)
                        }
                    })

            }
        }
    }

    private fun configuraBotaoVoltar(
        logradouro: TextInputEditText,
        cidade: TextInputEditText,
        uf: TextInputEditText,
    ) {
        binding.fabBackspace.setOnClickListener {
            logradouro.text = null
            cidade.text = null
            uf.text = null
            uf.clearFocus()
            viewModel.listCepResposta.value = cepsNull()
            lifecycleScope.launch(Main) {
                viewModel.listCepResposta.value.let {
                    adapter.atualiza(ceps = it)
                }
            }
        }
    }

    private fun configuraBotaoVoltar() {
        binding.fabVoltarEnd.setOnClickListener {
            finish()
        }
    }

    private fun showMessage(message: String?) {
        lifecycleScope.launch(Main) {
            Toast.makeText(
                this@BuscaPorEnderecoActivity,
                message,
                Toast.LENGTH_LONG
            ).show()
            showLoading(show = false)
        }
    }

    private fun atualizaAdapter(cepRespostaList: List<CepResposta>) {
        lifecycleScope.launch(Main) {
            viewModel.listCepResposta.value = cepRespostaList.map {
                it.cepGet
            }
            viewModel.listCepResposta.value?.let {
                adapter.atualiza(ceps = it)
            }
            showLoading(show = false)
        }
    }

    private fun showLoading(show: Boolean) {
        lifecycleScope.launch(Main) {
            binding.loading.visibility = if (show) VISIBLE else INVISIBLE
        }
    }

}
