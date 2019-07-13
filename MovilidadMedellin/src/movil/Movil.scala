package movil

import punto.Velocidad

abstract class Movil(posc: Any,vel: Velocidad)  {
    
  def aumentarPosc(posc: Any)  
  
  def direccionAngulo() = vel.direccion
    
}