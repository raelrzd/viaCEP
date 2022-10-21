package rezende.israel.viacep.webclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rezende.israel.viacep.model.Cep

interface CepService {

    @GET("{cep}/json/")
    fun buscaCep(@Path("cep") cep: String) : Call<Cep>

    @GET("{uf}/{cidade}/{logradouro}/json/")
    fun buscaEnderecos(@Path("uf") uf: String, @Path("cidade") cidade: String, @Path("logradouro") logradouro: String) : Call<List<CepResposta>>

}
