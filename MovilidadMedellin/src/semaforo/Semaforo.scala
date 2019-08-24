package semaforo

import punto.Interseccion
import main.Simulacion

case class Semaforo (val ubicacion: Interseccion) (val tiempoV: Int, val tiempoA: Int){
  
  Simulacion.arrayDeSemaforos += this
  
  var Estado = "Rojo"
}