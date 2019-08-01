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
//  println("ya me cree")
    this.path.dequeue()
//  println(this.path)
//  println(vel.direccion.grado)
  var radioLimite = vel.magnitud * Simulacion.dt

  def aumentarPosc(dt: Int) = {
    if(!this.path.isEmpty){
    	var d= distEntreIntersec(posInicial, path.front)
//			println(d)
//			println("Coordenadas: "+posInicial.xI+" - "+ posInicial.yI)
			if(d>radioLimite){
				var tup = formaAumentoPosicion(this.vel, dt)
						this.posInicial.xI += tup._1
						this.posInicial.yI += tup._2
			}else{
			  println("Camino cabeza: "+this.path.front )
				this.posInicial = this.path.front.copy()
				this.path.dequeue()
				//println("posInicial: "+ this.posInicial)
				if(!this.path.isEmpty) this.vel.direccion.grado = this.direccionAngulo(this.posInicial ,this.path) 
			}
    }
  }
  
  def distEntreIntersec(actual: Interseccion, fin: Interseccion)= {
    math.pow((math.pow((fin.xI - actual.xI),2) + math.pow((fin.yI - actual.yI),2)),0.5)
  }
  
}

object Vehiculo {
  
  def genPosiciones(): (Interseccion, Interseccion) = {
    var posIni = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
    
//    var posFin = Random.nextInt(Simulacion.arrayDeIntersecciones.filter(x => 
//      x != Simulacion.arrayDeIntersecciones(posIni)).length)
      
    var posFin = {
      var r = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
      while(r == posIni){
        r = Random.nextInt(Simulacion.arrayDeIntersecciones.length)
      }
      r
    }
    
//    println("Tamaño ArrayIntersecciones en Posiciones: "+Simulacion.arrayDeIntersecciones.length)
    
    (Simulacion.arrayDeIntersecciones(posIni).copy(), Simulacion.arrayDeIntersecciones(posFin).copy())    
  }
  
  var totalVehiculos = Simulacion.vehiculosMin + Random.nextInt(Simulacion.vehiculosMax - Simulacion.vehiculosMin + 1)
  
  val buses = totalVehiculos * Simulacion.propBuses
  val camiones = totalVehiculos * Simulacion.propCamiones
  val carros = totalVehiculos * Simulacion.propCarros
  val motos = totalVehiculos * Simulacion.propMotos
  val motoTaxis = totalVehiculos * Simulacion.propMotoTaxis
  
  
  def genVelocidad(): Int = Simulacion.velMin + Random.nextInt(Simulacion.velMax - Simulacion.velMin + 1)
 
  def crearVehiculos(){
    var cBuses = 0
	  var cCamiones = 0
	  var cCarros = 0
	  var cMotos = 0
	  var cMotoTaxis = 0

//    var a = Simulacion.arrayDeIntersecciones.indexOf(Simulacion.arrayDeIntersecciones.filter(p => p.nombre=="Monterrey")(0))
//    var b = Simulacion.arrayDeIntersecciones.indexOf(Simulacion.arrayDeIntersecciones.filter(p => p.nombre=="Col Reg")(0))
//    Simulacion.arrayDeVehiculos +=(new Carro(Simulacion.arrayDeIntersecciones(a).copy(),Simulacion.arrayDeIntersecciones(b).copy(),new Velocidad(30,new Angulo(0))))
    while (Simulacion.arrayDeVehiculos.length < totalVehiculos) {
//      println(s"Inicio-${Simulacion.arrayDeVehiculos.length} total: $totalVehiculos")
  	  var r = Random.nextInt(5)
  		var posiciones = genPosiciones()
  	  
  	  r match {
    		case 0 => {		  
    		  if (cBuses < buses) {
    		    println("bus")
    			  Simulacion.arrayDeVehiculos += new Bus(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    		    println("Bus"+" "+posiciones._1+" "+posiciones._2)
    			  cBuses += 1	    
    		  }
    		}		
    		case 1 => {
    		  if (cCamiones < camiones) {
    		    println("camion")
    			  Simulacion.arrayDeVehiculos += new Camion(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0))) 
    			  println("Camion"+" "+posiciones._1+" "+posiciones._2)
    			  cCamiones += 1
    		  }
    		}		
    		case 2 => {
    		  if (cCarros < carros) {
    		    println("carro")
    			  Simulacion.arrayDeVehiculos += new Carro(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
					  println("Carro"+" "+posiciones._1+" "+posiciones._2)
    		    cCarros += 1
    		  }
    		}
    		case 3 => {
    		  if (cMotos < motos) {
    		    println("Moto")
    			  Simulacion.arrayDeVehiculos += new Moto(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
					  println("Moto"+" "+posiciones._1+" "+posiciones._2)
    		    cMotos += 1
    		  }
    		}
    		case 4 => {
    		  if (cMotoTaxis < motoTaxis) {
    		    println("motoT")
    			  Simulacion.arrayDeVehiculos += new MotoTaxi(posiciones._1, posiciones._2, new Velocidad(genVelocidad(), new Angulo(0)))
    		    println("MotoTaxi"+" "+posiciones._1+" "+posiciones._2)
    			  cMotoTaxis += 1
    		  }
    		}
  	  }
      println("Tamaño ArrayIntersecciones en Vehiculos: "+Simulacion.arrayDeIntersecciones.length)
    }
    println("ya sali")
    
    Simulacion.arrayDeVehiculos.foreach(vehi => vehi.vel.direccion.grado = vehi.direccionAngulo(vehi.posInicial,vehi.path))
  }
}