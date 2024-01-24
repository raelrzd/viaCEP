package rezende.israel.viacep.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rezende.israel.viacep.databinding.ItemListaBinding
import rezende.israel.viacep.extension.cepsNull
import rezende.israel.viacep.extension.valida
import rezende.israel.viacep.model.Cep

class ListaCepsAdapter(private val context: Context) :
    RecyclerView.Adapter<ListaCepsAdapter.ViewHolder>() {

    private val cepsList = cepsNull()

    class ViewHolder(
        private val binding: ItemListaBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun vincula(cepItem: Cep) {
            binding.textCep.text = valida(cepItem.cep)
            binding.textLogradouro.text = valida(cepItem.logradouro)
            binding.textComplemento.text = valida(cepItem.complemento)
            binding.textBairro.text = valida(cepItem.bairro)
            binding.textLocalidade.text = valida(cepItem.localidade)
            binding.textUf.text = valida(cepItem.uf)
            binding.textIbge.text = valida(cepItem.ibge)
            binding.textGia.text = valida(cepItem.gia)
            binding.textDdd.text = valida(cepItem.ddd)
            binding.textSiafi.text = valida(cepItem.siafi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemListaBinding.inflate(LayoutInflater.from(context))
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(cepsList[position])
    }

    override fun getItemCount(): Int {
        return cepsList.size
    }

    fun atualiza(ceps: List<Cep>?){
        if (ceps!!.isEmpty()){
            cepsList.addAll(cepsNull())
        } else{
            this.cepsList.clear()
            this.cepsList.addAll(ceps)
        }
        notifyDataSetChanged()
    }

}