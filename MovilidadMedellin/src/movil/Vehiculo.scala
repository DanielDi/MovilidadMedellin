package movil

import punto.Velocidad
import main.Simulacion
import punto.Velocidad
import punto.Angulo
import scala.util.Random

class Vehiculo(pos: Any, private var _vel: Velocidad) extends Movil(pos,_vel) with MovimientoUniforme {
  
  var placa: String = ""
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
  
  def aumentarPosc(posc: Any) ={}
  
}

object Vehiculo {
  
  
  var totalVehiculos = Simulacion.vehiculosMin + Random.nextInt(Simulacion.vehiculosMax - Simulacion.vehiculosMin + 1)
  
  
  
  val buses = totalVehiculos * Simulacion.propBuses
  val camiones = totalVehiculos * Simulacion.propCamiones
  val carros = totalVehiculos * Simulacion.propCarros
  val motos = totalVehiculos * Simulacion.propMotos
  val motoTaxis = totalVehiculos * Simulacion.propMotoTaxis
  
  var cBuses = 0
  var cCamiones = 0
  var cCarros = 0
  var cMotos = 0
  var cMotoTaxis = 0
  
  def genVelocidad(): Int = Simulacion.velMin + Random.nextInt(Simulacion.velMax - Simulacion.velMin + 1)
  
  while (Simulacion.arrayDeVehiculos.length != totalVehiculos) {
    
	  var r = Random.nextInt(5)
			  
	  r match {
  		case 0 => {		  
  		  if (cBuses < buses) {
  			  Simulacion.arrayDeVehiculos :+= new Bus(0 , new Velocidad(0, new Angulo(0)))
  			  cBuses += 1	    
  		  }
  		}		
  		case 1 => {
  		  if (cCamiones < camiones) {
  			  Simulacion.arrayDeVehiculos :+= new Camion(0 , new Velocidad(genVelocidad(), new Angulo(0))) 
  			  cCamiones += 1
  		  }
  		}		
  		case 2 => {
  		  if (cCarros < carros) {
  			  Simulacion.arrayDeVehiculos :+= new Carro(0 , new Velocidad(genVelocidad(), new Angulo(0)))
  		    cCarros += 1
  		  }
  		}
  		case 3 => {
  		  if (cMotos < motos) {
  			  Simulacion.arrayDeVehiculos :+= new Moto(0 , new Velocidad(genVelocidad(), new Angulo(0)))
  		    cMotos += 1
  		  }
  		}
  		case 4 => {
  		  if (cMotoTaxis < motoTaxis) {
  			  Simulacion.arrayDeVehiculos :+= new MotoTaxi(0 , new Velocidad(genVelocidad(), new Angulo(0)))
  			  cMotoTaxis += 1
  		  }
  		}
	  }
  }  
}