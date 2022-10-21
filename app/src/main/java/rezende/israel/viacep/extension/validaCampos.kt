package rezende.israel.viacep.extension

fun valida(valor: String?) : String {
    if (valor != "" && valor!= null) {
        return "$valor"
    } else {
        return "-"
    }
}