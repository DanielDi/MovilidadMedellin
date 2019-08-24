package semaforo

import scala.collection.mutable.ArrayBuffer
import punto.Interseccion
import main.Simulacion

case class NodoSemaforo(val inter: Interseccion) {
  Simulacion.arrayDeNodoSema += this
  
  val arraySemaforo= ArrayBuffer[Semaforo]()
}