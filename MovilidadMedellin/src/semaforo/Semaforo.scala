package semaforo

import punto.Via
import main.Simulacion
import punto.Interseccion

case class Semaforo (val via: Via, val ubicacion: Interseccion) (val tiempoV: Int, val tiempoA: Int){
  
  
  Simulacion.arrayDeSemaforos += this
  
  var Estado = "Rojo"
}