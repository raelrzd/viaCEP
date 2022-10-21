package rezende.israel.viacep.extension

fun valida(valor: String?) : String {
    if (valor != "") {
        return valor.toString()
    } else {
        return "-"
    }
}