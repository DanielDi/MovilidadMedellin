package movil

import punto.Velocidad
import main.Simulacion
import punto.Velocidad
import punto.Interseccion
import punto.Angulo
import scala.util.Random
import main.GrafoVia
import scala.collection.mutable.Queue


abstract class Vehiculo(var posInicial: Interseccion, var posFinal: Interseccion, private var _vel: Velocidad) extends Movil with MovimientoUniforme {
  
  val placa: String

  var path = Queue( GrafoVia.menorCamino(posInicial, posFinal).map(_.toOuter).toSeq : _*) //Convierte nodos a intersecciones
  var origen = posInicial.copy()
  this.path.dequeue()
  var radioLimite = vel.magnitud * Simulacion.dt

  def aumentarPosc(dt: Int) = {
    if(!this.path.isEmpty){
    	var d= distEntreIntersec(posInicial, path.front)
			if(d>radioLimite){
				var tup = formaAumentoPosicion(this.vel, dt)
						this.posInicial.xI += tup._1
						this.posInicial.yI += tup._2
			}else{
				this.posInicial = this.path.front.copy()
				this.path.dequeue()
				if(!this.path.isEmpty) this.vel.direccion.grado = this.direccionAngulo(this.posInicial ,this.path) 
			}
    }
  }
  
  //Accesor
  def vel = _vel
  
  //Mutator
  def vel_= (vel: Velocidad): Unit = _vel = vel
  
  def aumentarPosc(posc: Any) ={}

  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
}

object Vehiculo {
  
  def genPosiciones(): (Interseccion, Interseccion) = {
    var posIni = Random.nextInt(Simulacion.arrayDeIntersecciones.length)      
    var posFin = {
      var r = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
      while(r == posIni){
        r = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
      }
      r
    }
    
    (Simulacion.arrayDeIntersecciones(posIni).copy(), Simulacion.arrayDeIntersecciones(posFin).copy())    
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
      
    cBuses = 0
    cCamiones = 0
    cCarros = 0
    cMotos = 0
    cMotoTaxis = 0

    while (Simulacion.arrayDeVehiculos.length < totalVehiculos) {
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
    
    Simulacion.arrayDeVehiculos.foreach(vehi => vehi.vel.direccion.grado = vehi.direccionAngulo(vehi.posInicial,vehi.path))
  }
}