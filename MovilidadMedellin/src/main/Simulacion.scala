package main

import movil.Vehiculo
import punto.Via
import movil.Vehiculo
import scala.collection.mutable.ArrayBuffer

object Simulacion extends Runnable {
  var t = 0
  var dt = 0
  var tRefresh = 0
  var vehiculosMin = 0
  var vehiculosMax = 10
  var velMin = 0
  var velMax = 0
  var propCarros = 0.2
  var propMotos = 0.3
  var propBuses = 0.2
  var propCamiones = 0.2
  var propMotoTaxis = 0.1
  
  var arrayDeVehiculos = ArrayBuffer[Vehiculo]()
  var arrayDeVias = ArrayBuffer[Via]()
  
  var objVehiculo = Vehiculo
  
  def run() {
    while(true) {
//      arrayDeVehiculos.foreach(_.mover(dt))
//      t += dt
//      Grafico.graficarVehiculos(listaDeVehiculos)
//      
//      Thread.sleep(tRefresh)
    }
  }
  
}