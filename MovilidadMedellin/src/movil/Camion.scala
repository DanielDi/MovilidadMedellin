package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Camion(velocidad: Int, ac:Int) 
extends Vehiculo(velocidad, ac) with MovimientoUniforme{

  val placa = "R" + (List.fill(5)(Random.nextInt(10))).mkString
  
}