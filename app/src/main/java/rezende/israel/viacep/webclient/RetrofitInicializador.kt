package rezende.israel.viacep.webclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rezende.israel.viacep.webclient.service.CepService

class RetrofitInicializador {

    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(logging).build()
    }

    private val retrofit = Retrofit.Builder().baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(MoshiConverterFactory.create()).client(client).build()

    val cepService = retrofit.create(CepService::class.java)

}