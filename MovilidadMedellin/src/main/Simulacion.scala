package main

import scala.collection.mutable.ArrayBuffer

import movil._
import scala.collection.mutable.ArrayBuffer
import main.Main.parametrosSimulacion
import scala.util.Random
import semaforo.Semaforo
import semaforo.NodoSemaforo
import punto._

object Simulacion extends Runnable {
  
  var t = 0
  var dt = parametrosSimulacion.dt
  var tRefresh = 50//parametrosSimulacion.tRefresh
  val vehiculosMin = parametrosSimulacion.vehiculos.minimo
  val vehiculosMax = parametrosSimulacion.vehiculos.maximo
  val velMin = parametrosSimulacion.velocidad.minimo
  val velMax = parametrosSimulacion.velocidad.maximo
  val acMin = 20
  val acMax = 50
  val minTVerde = 50
  val maxTVerde=  100
  val tAmarillo = 3
  val XSemaforoF = 400
  val XSemaAmaC = 200

  var propCarros = parametrosSimulacion.proporciones.carros
  var propMotos = parametrosSimulacion.proporciones.motos
  var propBuses = parametrosSimulacion.proporciones.buses
  var propCamiones = parametrosSimulacion.proporciones.camiones
  var propMotoTaxis = parametrosSimulacion.proporciones.motoTaxis
  
  var hilo: Thread = _ 
  
  def iniciar = hilo.start

  var arrayDeVehiculos = ArrayBuffer[Vehiculo]()  
  var arrayDeVias = ArrayBuffer[Via]()
  var arrayDeIntersecciones = ArrayBuffer[Interseccion]()
  var arrayDeViajes = ArrayBuffer[Viaje]()
  var arrayDeSemaforos = ArrayBuffer[Semaforo]()
  var arrayDeNodoSema = ArrayBuffer[NodoSemaforo]()
  var comparendos = ArrayBuffer[Comparendo]()
  var camaras = ArrayBuffer[CamaraFotoDeteccion]()
  
  def run() {
    
    while(!(Simulacion.arrayDeViajes.map(_.path)).filter(!_.isEmpty).isEmpty) {
      
      arrayDeNodoSema.foreach(_.cambioSemaforo())
      arrayDeViajes.foreach(_.aumentarPosc(dt))
      this.t += dt
      Grafico.graficarVehiculos(arrayDeVehiculos)
      Thread.sleep(tRefresh)
    }
    Main.llamar
    
  }
  
  def crearGrafo(){
    GrafoVia.construir(arrayDeVias)
  }
  
  def crearGrafico() = {
     iniciarVias
     Grafico.graficarVias(arrayDeVias,arrayDeIntersecciones, camaras)
     arrayDeIntersecciones.clear()
     arrayDeVias.clear()
   }
  
  def crearSemaforo(){
    arrayDeVias.foreach(via => if(via.sentido.doblevia){
                                 Semaforo(via,via.interO)(genTVerde(),tAmarillo)
                                 Semaforo(via,via.interF)(genTVerde(),tAmarillo)
                                }else Semaforo(via,via.interF)(genTVerde(),tAmarillo))
                                 
   arrayDeSemaforos.foreach(sema => if(arrayDeNodoSema.filter(_.inter == sema.ubicacion).size == 0){
                                    var nodo = NodoSemaforo(sema.ubicacion)
                                    nodo.arraySemaforo += sema
                                    }else arrayDeNodoSema.find(_.inter == sema.ubicacion).get.arraySemaforo += sema)
   
   arrayDeNodoSema.foreach(_.arraySemaforo(0).estado = "Verde")                                 
  }
  
  
  def genPosiciones(): (Interseccion, Interseccion) = {
    var posIni = Random.nextInt(arrayDeIntersecciones.length)      
    var posFin = {
      var r = Random.nextInt(arrayDeIntersecciones.length)
      while(r == posIni){
        r = Random.nextInt(arrayDeIntersecciones.length)
      }
      r
    }
    (Simulacion.arrayDeIntersecciones(posIni).copy(), Simulacion.arrayDeIntersecciones(posFin).copy())    
  }
  
  var totalVehiculos = vehiculosMin + Random.nextInt(vehiculosMax - vehiculosMin + 1)
  
  val buses = totalVehiculos * propBuses
  val camiones = totalVehiculos * propCamiones
  val carros = totalVehiculos * propCarros
  val motos = totalVehiculos * propMotos
  val motoTaxis = totalVehiculos * propMotoTaxis
  
  var cBuses = 0
  var cCamiones = 0
  var cCarros = 0
  var cMotos = 0
  var cMotoTaxis = 0
  
  def genVelocidad(): Int = velMin + Random.nextInt(velMax - velMin + 1)
  def genTasaAc(): Int = acMin + Random.nextInt(acMax - acMin + 1)
  def genTVerde(): Int = minTVerde + Random.nextInt(maxTVerde - minTVerde + 1)
   
  def crearVehiculos(){
      
    cBuses = 0
    cCamiones = 0
    cCarros = 0
    cMotos = 0
    cMotoTaxis = 0
    
    
    while (arrayDeVehiculos.length < 3) {
  	  var r = Random.nextInt(5)
  		var posiciones = genPosiciones()
  	  
  	  r match {
    		case 0 => {
    		  
    		  if (cBuses < buses) {
    		    var bus = new Bus( genVelocidad() ,genTasaAc())  
    			  arrayDeVehiculos += bus
    			  arrayDeViajes += Viaje(bus)(posiciones._1, posiciones._2) 
    			  cBuses += 1	    
    		  }
    		}		
    		case 1 => {
    		  if (cCamiones < camiones) {
    		    var camion = new Camion(genVelocidad() ,genTasaAc()) 
    			  arrayDeVehiculos += camion
    		    arrayDeViajes += Viaje(camion)(posiciones._1, posiciones._2)  
    			  cCamiones += 1
    		  }
    		}		
    		case 2 => {
    		  if (cCarros < carros) {
    			  var carro = new Carro(genVelocidad() ,genTasaAc()) 
    			  arrayDeVehiculos += carro
    		    arrayDeViajes += Viaje(carro)(posiciones._1, posiciones._2)  
    		    cCarros += 1
    		  }
    		}
    		case 3 => {
    		  if (cMotos < motos) {
    			  var moto = new Moto(genVelocidad() ,genTasaAc()) 
    			  arrayDeVehiculos += moto 
    		    arrayDeViajes += Viaje(moto)(posiciones._1, posiciones._2)  
    		    cMotos += 1
    		  }
    		}
    		case 4 => {
    		  if (cMotoTaxis < motoTaxis) {
    			  var motoTaxi = new MotoTaxi(genVelocidad(),genTasaAc()) 
    			  arrayDeVehiculos += motoTaxi
    		    arrayDeViajes += Viaje(motoTaxi)(posiciones._1, posiciones._2)  
    			  cMotoTaxis += 1
    		  }
    		}
  	  }
    }
    
    arrayDeVehiculos.foreach(vehi => vehi.vel.direccion.grado = {
      var viaje = arrayDeViajes.filter(_.vehiculo.placa == vehi.placa)(0)
      var d = vehi.direccionAngulo(viaje.posInicial.copy(), viaje.path)
      d
    })
  }
    
  def iniciarVias{
    Connection.saveInterseccion()
  }
  
}