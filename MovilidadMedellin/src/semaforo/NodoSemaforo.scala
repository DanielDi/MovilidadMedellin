package semaforo

import scala.collection.mutable.ArrayBuffer
import punto.Interseccion
import main.Simulacion

case class NodoSemaforo(val inter: Interseccion) {
  Simulacion.arrayDeNodoSema += this
 
  val arraySemaforo= ArrayBuffer[Semaforo]()
  var auxtiempo = arraySemaforo(0).tiempoV
  
  def cambioSem(tiempo: Int) {
    var sema = arraySemaforo.filter(_.Estado =="Verde")(0)
    if(tiempo < auxtiempo){
    }else{
      auxtiempo=tiempo +
    }
  }
}