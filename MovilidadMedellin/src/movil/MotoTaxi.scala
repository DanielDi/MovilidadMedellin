package movil

import punto.Velocidad
import scala.util.Random

class MotoTaxi(posc: Any, velocidad: Velocidad) extends Vehiculo(posc, velocidad) with MovimientoUniforme{
  
    for (i <- 0 to 5) {
    if (i < 3) {
    	this.placa += Random.nextInt(10)
    }
    
    else {
    	this.placa += (65 + Random.nextInt(26)).toChar
    }
  }
  
}