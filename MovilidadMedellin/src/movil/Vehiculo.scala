package movil

import punto.Velocidad
import main.Simulacion
import punto.Interseccion
import punto.Angulo
import main.GrafoVia
import scala.collection.mutable.Queue
import scala.util.Random


abstract case class Vehiculo(var velCru: Int, tasaAc:Int) extends Movil {
  
  private var _vel = Velocidad(0.0, new Angulo(0))
  var tasaDes = 0.0
  val placa: String
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
  
  //def aumentarPosc(posc: Any) ={}
}