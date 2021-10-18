package es.appadel.herramientas

import android.widget.TextView
import androidx.databinding.BindingAdapter
import es.appadel.clases.Partido

@BindingAdapter("setNumeroJugadores")
fun TextView.setNumeroJugadores(partido: Partido){
    if(partido.users!=null){
        text = "${partido.users!!.size}/${partido.numero_jugadores}"
    }else{
        text =  "0/${partido.numero_jugadores}"
    }
}


@BindingAdapter("setFechaDia")
fun TextView.setFechaDia(partido: Partido){
    partido.fecha?.let {
        val mesNombre = MetodosFecha.getNombreMes(partido.fecha!!.toDate())
        val dia = MetodosFecha.getNumero(partido.fecha!!.toDate())
        text = "$dia de $mesNombre"
    }
}
@BindingAdapter("setHora")
fun TextView.setHora(partido: Partido){
    partido.fecha?.let {
        val horas = MetodosFecha.getHora(partido.fecha!!.toDate())
        val minutos = MetodosFecha.getMinutos(partido.fecha!!.toDate())
        text = "$horas:$minutos"
    }
}