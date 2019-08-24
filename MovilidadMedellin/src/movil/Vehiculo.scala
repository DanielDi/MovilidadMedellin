package movil

import punto.Velocidad
import main.Simulacion
import punto.Interseccion
import punto.Angulo
import main.GrafoVia
import scala.collection.mutable.Queue


abstract case class Vehiculo(private var _vel: Velocidad) extends Movil {
  
  val placa: String
  
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
  
  //def aumentarPosc(posc: Any) ={}
}