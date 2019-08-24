package movil

import main.Simulacion
import punto.Interseccion
import main.GrafoVia

import scala.collection.mutable.Queue

case class Viaje(var vehiculo: Vehiculo)(var posInicial: Interseccion, var posFinal: Interseccion) extends MovimientoUniforme {
  
  var path = Queue(GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  this.path.dequeue()
  
  def mover(dt: Int) {
    var a = vehiculo.aumentarPosc(dt, posInicial.copy(), path)
    this.posInicial = a._1.copy()
    this.path = a._2
  }
  

}