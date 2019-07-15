package movil

import punto.Velocidad
import punto.Interseccion

abstract class Movil  {
   
  var posInicial: Interseccion
  var posFinal: Interseccion
  var vel: Velocidad
  
  def aumentarPosc(dt: Int)  
  
  def direccionAngulo() = vel.direccion
    
}