package movil

import punto.Velocidad
import main.Simulacion
import punto.Velocidad
import punto.Interseccion
import punto.Angulo
import scala.util.Random
import main.GrafoVia
import scala.collection.mutable.Queue


abstract class Vehiculo(var posInicial: Interseccion, var posFinal: Interseccion, var vel: Velocidad) extends Movil with MovimientoUniforme {
  
  var placa: String = ""

  var path = Queue( GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  path.dequeue()
  var radioLimite = vel.magnitud * Simulacion.dt

  def aumentarPosc(dt: Int) = {
    var d= distEntreIntersec(posInicial, path.front) //
    if(d>radioLimite){
      var tup = formaAumentoPosicion(this.vel, dt)
      this.posInicial.xI = this.posInicial.xI + tup._1
      this.posInicial.yI = this.posInicial.yI + tup._2
    }else{
      this.posInicial.xI = path.front.xI
      this.posFinal.yI = path.front.yI
      this.vel.direccion.grado = Simulacion.arrayDeVias.filter(via => 
        (via.interO == posInicial) && (via.interF == path.front)).head.angulovia 
      path.dequeue()
    }
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}

object Vehiculo {
  
  def genPosiciones(): (Interseccion, Interseccion) = {
    var posIni = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
    
    var posFin = Random.nextInt(Simulacion.arrayDeIntersecciones.filter(x => 
      x != Simulacion.arrayDeIntersecciones(posIni)).length)
      
      
    (Simulacion.arrayDeIntersecciones(posIni), Simulacion.arrayDeIntersecciones(posFin))    
  }
  
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
 
  def crearVehiculos(){
    Simulacion.arrayDeVehiculos +=(new Carro(Simulacion.arrayDeIntersecciones(1),Simulacion.arrayDeIntersecciones(2),new Velocidad(30,new Angulo(0))))
    while (Simulacion.arrayDeVehiculos.length != totalVehiculos) {
      
  	  var r = Random.nextInt(5)
  		var posiciones = genPosiciones()
  	  
  	  r match {
    		case 0 => {		  
    		  if (cBuses < buses) {
    		    
    			  Simulacion.arrayDeVehiculos += new Bus(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    			  cBuses += 1	    
    		  }
    		}		
    		case 1 => {
    		  if (cCamiones < camiones) {
    			  Simulacion.arrayDeVehiculos += new Camion(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0))) 
    			  cCamiones += 1
    		  }
    		}		
    		case 2 => {
    		  if (cCarros < carros) {
    			  Simulacion.arrayDeVehiculos += new Carro(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    		    cCarros += 1
    		  }
    		}
    		case 3 => {
    		  if (cMotos < motos) {
    			  Simulacion.arrayDeVehiculos += new Moto(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    		    cMotos += 1
    		  }
    		}
    		case 4 => {
    		  if (cMotoTaxis < motoTaxis) {
    			  Simulacion.arrayDeVehiculos += new MotoTaxi(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    			  cMotoTaxis += 1
    		  }
    		}
  	  } 
    }
    Simulacion.arrayDeVehiculos.foreach(vehi => vehi.vel.direccion.grado = Simulacion.arrayDeVias.filter(via => 
        (via.interO == vehi.posInicial) && (via.interF == vehi.path.front)).head.angulovia)
  }
}