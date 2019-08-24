package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Camion(velocidad: Velocidad) 
extends Vehiculo(velocidad) with MovimientoUniforme{

  val placa = "R" + (List.fill(5)(Random.nextInt(10))).mkString
  
}