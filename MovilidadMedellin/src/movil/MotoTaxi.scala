package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class MotoTaxi(posInicial: Interseccion, posFinal: Interseccion, velocidad: Velocidad) 
extends Vehiculo(posInicial, posFinal, velocidad) with MovimientoUniforme{
  
  this.placa = (List.fill(3)(Random.nextInt(10)) ::: List.fill(3)((65 + Random.nextInt(26)).toChar)).mkString
      
}