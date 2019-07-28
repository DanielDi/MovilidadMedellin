package movil

import punto.Velocidad
import punto.Interseccion
import main.Simulacion
import scala.collection.mutable.Queue

abstract class Movil  {
   
  var posInicial: Interseccion
  var posFinal: Interseccion
  var vel: Velocidad
  var path: Queue[Interseccion]
  def aumentarPosc(dt: Int)  
  
  def direccionAngulo() = {
    println(path.front)
    var a = Simulacion.arrayDeVias.filter(via =>
      ((via.interO == posInicial) && (via.interF == path.front)) || ((via.interF == posInicial) && (via.interO == path.front)))(0).anguloVia
      println("Angulo: " + math.toDegrees(a))
      a
  }
    
}