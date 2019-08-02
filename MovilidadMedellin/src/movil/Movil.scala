package movil

import punto.Velocidad
import punto.Interseccion
import main.Simulacion
import scala.collection.mutable.Queue

abstract class Movil {
   
  var posInicial: Interseccion
  var posFinal: Interseccion
  var vel: Velocidad
  var path: Queue[Interseccion]
  def aumentarPosc(dt: Int)  
  
  def direccionAngulo(posO: Interseccion, camino: Queue[Interseccion]):Double = {
    var angulo: Double = 0
    if(!camino.isEmpty){
      var a = Simulacion.arrayDeVias.filter(via =>
        ((via.interO == posO) && (via.interF == camino.front)))
      if (a.size == 0){
        a = Simulacion.arrayDeVias.filter(via =>
        ((via.interF == posO) && (via.interO == camino.front)))
      }
      if(posO.xI > camino.front.xI) angulo = a(0).anguloVia + math.Pi
      else if(posO.xI == camino.front.xI && posO.yI > camino.front.yI) angulo = a(0).anguloVia + math.Pi
      else angulo = a(0).anguloVia
    }
    angulo
  } 
}