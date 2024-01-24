package rezende.israel.viacep.repository

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.model.CepResposta
import rezende.israel.viacep.model.Resource
import rezende.israel.viacep.webclient.service.CepService

private const val ERRO_BUSCA_CEP = "Falha ao buscar CEP. Verifique se o valor inserido é válido! ⚠️"
private const val ERRO_BUSCA_ENDERECO = "Falha ao buscar CEP. Verifique se os valores inseridos são válidos! ⚠️"

class CepRepository(private val cepService: CepService) {

    fun buscaCep(cep: String, completed: (liveData: MutableLiveData<Resource<Cep>>) -> Unit) {
        val mutableLiveData = MutableLiveData<Resource<Cep>>()
        val call = cepService.buscaCep(cep)
        call.enqueue(object : Callback<Cep> {

            override fun onResponse(call: Call<Cep>, response: Response<Cep>) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource(dado = response.body())
                    completed(mutableLiveData)
                } else {
                    mutableLiveData.value = Resource(dado = null, erro = ERRO_BUSCA_CEP)
                    completed(mutableLiveData)
                }
            }

            override fun onFailure(call: Call<Cep>, t: Throwable) {
                mutableLiveData.value = Resource(dado = null, erro = ERRO_BUSCA_CEP)
                completed(mutableLiveData)
            }

        })
    }

    fun buscaEnderecos(
        uf: String,
        cidade: String,
        logradouro: String,
        completed: (liveData: MutableLiveData<Resource<List<CepResposta>>>) -> Unit,
    ) {
        val mutableLiveData = MutableLiveData<Resource<List<CepResposta>>>()
        val call = cepService.buscaEnderecos(uf, cidade, logradouro)
        call.enqueue(object : Callback<List<CepResposta>> {

            override fun onResponse(
                call: Call<List<CepResposta>>,
                response: Response<List<CepResposta>>,
            ) {
                if (response.isSuccessful) {
                    mutableLiveData.value = Resource(dado = response.body())
                    completed(mutableLiveData)
                } else {
                    mutableLiveData.value = Resource(dado = null, erro = ERRO_BUSCA_ENDERECO)
                    completed(mutableLiveData)
                }
            }

            override fun onFailure(call: Call<List<CepResposta>>, t: Throwable) {
                mutableLiveData.value = Resource(dado = null, erro = ERRO_BUSCA_ENDERECO)
                completed(mutableLiveData)
            }
        })
    }

}