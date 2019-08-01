package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Camion(posInicial: Interseccion, posFinal: Interseccion, velocidad: Velocidad) 
extends Vehiculo(posInicial, posFinal, velocidad) with MovimientoUniforme{

  this.placa = "R" + (List.fill(5)(Random.nextInt(10))).mkString
  
}