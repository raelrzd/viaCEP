package rezende.israel.viacep.ui.activity

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import rezende.israel.viacep.databinding.ActivityBuscaPorCepBinding
import rezende.israel.viacep.extension.valida
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.webclient.RetrofitInicializador

class BuscaPorCepActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBuscaPorCepBinding.inflate(layoutInflater)
    }

    private val cepsNull: Cep? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoVoltar()

        val botaoApagar = binding.fabBackspace
        botaoApagar.setOnClickListener {
            preencheCampos(cepsNull)
            binding.inputCep.text = null
        }

        binding.fabConfirma.setOnClickListener {

            lifecycleScope.launch(IO) {
                launch(Main) {
                    binding.loading.visibility = VISIBLE
                    delay(2000)
                    binding.loading.visibility = INVISIBLE
                }
                val resposta: Response<Cep> = buscaDadosNaApi()
                resposta.body()?.let { ceps ->
                    ceps.cep?.let {
                        launch(Main) {
                            preencheCampos(ceps)
                            Toast.makeText(
                                this@BuscaPorCepActivity,
                                "Busca realizada com sucesso! ✅",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } ?: launch(Main) {
                    Toast.makeText(
                        this@BuscaPorCepActivity,
                        "Falha ao buscar CEP. Verifique se o valor inserido é válido! ⚠️",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun buscaDadosNaApi(): Response<Cep> {
        val call: Call<Cep> =
            RetrofitInicializador().cepService.buscaCep(binding.inputCep.text.toString())
        val resposta: Response<Cep> = call.execute()
        return resposta
    }

    private fun preencheCampos(ceps: Cep?) {
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

    private fun configuraBotaoVoltar() {
        val botaoVoltar = binding.fabVoltarCep
        botaoVoltar.setOnClickListener {
            finish()
        }
    }
}