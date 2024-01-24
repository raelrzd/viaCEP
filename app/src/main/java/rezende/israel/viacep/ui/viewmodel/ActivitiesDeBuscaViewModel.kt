package rezende.israel.viacep.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import rezende.israel.viacep.model.Cep
import rezende.israel.viacep.model.CepResposta
import rezende.israel.viacep.model.Resource
import rezende.israel.viacep.repository.CepRepository

class ActivitiesDeBuscaViewModel(private val repository: CepRepository) : ViewModel() {

    val listCepResposta = MutableLiveData<List<Cep>>()

    fun buscaEndereco(
        uf: String,
        cidade: String,
        logradouro: String,
        completed: (liveData: MutableLiveData<Resource<List<CepResposta>>>) -> Unit,
    ) = repository.buscaEnderecos(uf, cidade, logradouro, completed)

    fun buscaCep(
        cep: String,
        completed: (liveData: MutableLiveData<Resource<Cep>>) -> Unit,
    ) = repository.buscaCep(cep, completed)


}

