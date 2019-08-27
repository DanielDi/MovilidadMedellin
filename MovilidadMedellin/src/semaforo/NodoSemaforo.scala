package semaforo

import scala.collection.mutable.ArrayBuffer
import punto.Interseccion
import main.Simulacion

case class NodoSemaforo(val inter: Interseccion) {
  Simulacion.arrayDeNodoSema += this
  
  val arraySemaforo= ArrayBuffer[Semaforo]()

  var auxPos = 0
  var auxT = 0
  
  def cambioSemaforo() {
    if (arraySemaforo(auxPos).estado == "Verde") {
    	if (auxT < arraySemaforo(auxPos).tiempoV) {
    	  auxT += 1
    	}
    	else {
    	  auxT = 0
    	  arraySemaforo(auxPos).estado = "Amarillo"
    	}    	      
    }
    
    if (arraySemaforo(auxPos).estado == "Amarillo") {
      if (auxT < arraySemaforo(auxPos).tiempoA) {
        auxT += 1
      }
      else {
        auxT = 0
        if (auxPos == arraySemaforo.size -1) {
          arraySemaforo(auxPos).estado = "Rojo"
          auxPos = 0
          arraySemaforo(auxPos).estado = "Verde"
        }
        else {
          arraySemaforo(auxPos).estado = "Rojo"
          auxPos += 1
          arraySemaforo(auxPos).estado = "Verde"
        }
      }
    }
      
  }
  
  
}