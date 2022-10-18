package rezende.israel.viacep.webclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import rezende.israel.viacep.model.Cep

interface CepService {

    @GET("{cep}/json/")
    fun buscaCep(@Path("cep") cep: String) : Call<Cep>

}
