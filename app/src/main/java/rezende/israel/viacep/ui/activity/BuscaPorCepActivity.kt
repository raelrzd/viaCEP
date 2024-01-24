package rezende.israel.viacep.ui.activity

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import rezende.israel.viacep.databinding.ActivityBuscaPorCepBinding
import rezende.israel.viacep.extension.valida
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.ui.viewmodel.ActivitiesDeBuscaViewModel

class BuscaPorCepActivity : AppCompatActivity() {

    private val viewModel: ActivitiesDeBuscaViewModel by inject()
    private val binding by lazy {
        ActivityBuscaPorCepBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoVoltar()
        configuraBotaoApagar()
        configuraBotaoConfirma()
    }

    private fun configuraBotaoConfirma() {
        binding.fabConfirma.setOnClickListener {
            lifecycleScope.launch {
                showLoading(show = true)
                viewModel.buscaCep(binding.inputCep.text.toString(), completed = {
                    val cepEncontrado = it.value
                    if (cepEncontrado?.dado != null) {
                        preencheCampos(cepEncontrado.dado)
                        showMessage("Busca realizada com sucesso! ✅")
                    } else showMessage(cepEncontrado?.erro)
                })
            }
        }
    }

    private fun configuraBotaoApagar() {
        binding.fabBackspace.setOnClickListener {
            preencheCampos(null)
            binding.inputCep.text = null
        }
    }

    private fun configuraBotaoVoltar() {
        val botaoVoltar = binding.fabVoltarCep
        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    private fun showMessage(message: String?) {
        lifecycleScope.launch(Main) {
            message?.let {
                Toast.makeText(
                    this@BuscaPorCepActivity,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }
            showLoading(show = false)
        }
    }

    private fun showLoading(show: Boolean) {
        lifecycleScope.launch(Main) {
            binding.loading.visibility = if (show) VISIBLE else INVISIBLE
        }
    }

    private fun preencheCampos(ceps: Cep?) {
        lifecycleScope.launch(Main) {
            binding.textCep.text = valida(ceps?.cep)
            binding.textLogradouro.text = valida(ceps?.logradouro)
            binding.textComplemento.text = valida(ceps?.complemento)
            binding.textBairro.text = valida(ceps?.bairro)
            binding.textLocalidade.text = valida(ceps?.localidade)
            binding.textUf.text = valida(ceps?.uf)
            binding.textIbge.text = valida(ceps?.ibge)
            binding.textGia.text = valida(ceps?.gia)
            binding.textDdd.text = valida(ceps?.ddd)
            binding.textSiafi.text = valida(ceps?.siafi)
        }
    }
}