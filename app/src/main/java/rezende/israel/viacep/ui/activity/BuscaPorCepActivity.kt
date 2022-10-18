package rezende.israel.viacep.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import rezende.israel.viacep.databinding.ActivityBuscaPorCepBinding
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.webclient.RetrofitInicializador

class BuscaPorCepActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBuscaPorCepBinding.inflate(layoutInflater)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoVoltar()

        val cep = binding.textCep
        val logradouro = binding.textLogradouro
        val complemento = binding.textComplemento
        val bairro = binding.textBairro
        val localidade = binding.textLocalidade
        val uf = binding.textUf
        val ibge = binding.textIbge
        val gia = binding.textGia
        val ddd = binding.textDdd
        val siafi = binding.textSiafi



        val cepInserido = binding.inputCep
        val botaoConfirma = binding.fabConfirma


        botaoConfirma.setOnClickListener{
            lifecycleScope.launch(IO) {
                val call: Call<Cep> = RetrofitInicializador().cepService.buscaCep(cepInserido.text.toString())
                val resposta: Response<Cep> = call.execute()
                resposta.body()?.let { ceps ->
                    launch(Main) {
                        cep.text = valida(ceps.cep)
                        logradouro.text = valida(ceps.logradouro)
                        complemento.text = valida(ceps.complemento)
                        bairro.text = valida(ceps.bairro)
                        localidade.text = valida(ceps.localidade)
                        uf.text = valida(ceps.uf)
                        ibge.text = valida(ceps.ibge)
                        gia.text = valida(ceps.gia)
                        ddd.text = valida(ceps.ddd)
                        siafi.text = valida(ceps.siafi)
                        Toast.makeText(this@BuscaPorCepActivity, "Busca realizada com sucesso! ✅", Toast.LENGTH_SHORT).show()
                    }
                } ?: launch(Main) {
                    Toast.makeText(this@BuscaPorCepActivity, "Falha ao buscar CEP. Verifique se o valor inserido é válido! ⚠️", Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    fun valida(valor: String?) : String {
        if (valor != "") {
            return valor.toString()
        } else {
            return "-"
        }
    }

    private fun configuraBotaoVoltar() {
        val botaoVoltar = binding.fabVoltarCep
        botaoVoltar.setOnClickListener {
            finish()
        }
    }
}