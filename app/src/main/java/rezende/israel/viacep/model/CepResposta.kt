package rezende.israel.viacep.model

class CepResposta(
    val cep: String?,
    val logradouro: String?,
    val complemento: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val ibge: String?,
    val gia: String?,
    val ddd: String?,
    val siafi: String?,
) {

    val cepGet: Cep
        get() = Cep(
            cep ?: "-",
            logradouro ?: "-",
            complemento ?: "-",
            bairro ?: "-",
            localidade ?: "-",
            uf ?: "-",
            ibge ?: "-",
            gia ?: "-",
            ddd ?: "-",
            siafi ?: "-"
        )

}