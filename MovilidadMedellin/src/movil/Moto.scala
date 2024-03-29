package movil

import punto.Velocidad
import punto.Interseccion
import scala.util.Random

class Moto(velocidad: Int, ac:Int) 
extends Vehiculo(velocidad, ac) with MovimientoUniforme{
  
  var placa = (List.fill(3)((65 + Random.nextInt(26)).toChar) 
      ::: List.fill(2)(Random.nextInt(10)) 
      ::: List((65 + Random.nextInt(26)).toChar)).mkString
  
}