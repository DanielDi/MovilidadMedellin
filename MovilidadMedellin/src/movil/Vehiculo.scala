package movil

import punto.Velocidad
import main.Simulacion
import punto.Interseccion
import punto.Angulo
import main.GrafoVia
import scala.collection.mutable.Queue
import scala.util.Random


abstract case class Vehiculo(var velCru: Int, tasaAc:Int) extends Movil {
  
  private var _vel = new Velocidad(0.0, new Angulo(0))
  var tasaDes = 0.0
  var placa: String
  
  override def toString = s"velCru: $velCru tasaAc: $tasaAc velocidad: ${vel.magnitud}"
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
 
}