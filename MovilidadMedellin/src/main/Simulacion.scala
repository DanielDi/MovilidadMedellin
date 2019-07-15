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
    
  val niquia = new Interseccion(300, 12000, "Niquia")     
  val lauraAuto = new Interseccion(2400, 11400, "M. Laura Auto")     
  val lauraReg = new Interseccion(2400, 12600, "M. Laura Reg")     
  val ptoCero = new Interseccion(5400, 12000, "Pto 0")    
  val mino = new Interseccion(6300, 15000, "Minorista")    
  val villa = new Interseccion(6300, 19500, "Villanueva") 
  val ig65 = new Interseccion(5400, 10500, "65 Igu")    
  val robledo = new Interseccion(5400, 1500, "Exito Rob")    
  val colReg = new Interseccion(8250, 12000, "Col Reg")    
  val colFerr = new Interseccion(8250, 15000, "Col Ferr")     
  val col65 = new Interseccion(8250, 10500, "Col 65")     
  val col80 = new Interseccion(8250, 1500, "Col 80")    
  val juanOr = new Interseccion(10500, 19500, "Sn Juan Ori")    
  val maca = new Interseccion(10500, 12000, "Macarena")    
  val expo = new Interseccion(12000, 13500, "Exposiciones")   
  val reg30 = new Interseccion(13500, 15000, "Reg 30")     
  val monte = new Interseccion(16500, 15000, "Monterrey")     
  val agua = new Interseccion(19500, 15000, "Aguacatala")     
  val viva = new Interseccion(21000, 15000, "Viva Env")   
  val mayor = new Interseccion(23400, 15000, "Mayorca")   
  val ferrCol = new Interseccion(8250, 15000, "Ferr Col")   
  val ferrJuan = new Interseccion(10500, 15000, "Alpujarra")   
  val sanDiego = new Interseccion(12000, 19500, "San Diego")   
  val premium = new Interseccion(13500, 19500, "Premium")    
  val pp = new Interseccion(16500, 19500, "Parque Pob")    
  val santafe = new Interseccion(19500, 18750, "Santa Fe")    
  val pqEnv = new Interseccion(21000, 18000, "Envigado")     
  val juan65 = new Interseccion(10500, 10500, "Juan 65")   
  val juan80 = new Interseccion(10500, 1500, "Juan 80")    
  val _33_65 = new Interseccion(12000, 10500, "33 con 65")  
  val bule = new Interseccion(12000, 7500, "Bulerias")   
  val gema = new Interseccion(12000, 1500, "St Gema")    
  val _30_65 = new Interseccion(13500, 10500, "30 con 65")   
  val _30_70 = new Interseccion(13500, 4500, "30 con 70")    
  val _30_80 = new Interseccion(13500, 1500, "30 con 80")    
  val bol65 = new Interseccion(11100, 10500, "Boliv con 65")   
  val gu10 = new Interseccion(16500, 12000, "Guay con 10")    
  val terminal = new Interseccion(16500, 10500, "Term Sur")   
  val gu30 = new Interseccion(13500, 12000, "Guay 30")   
  val gu80 = new Interseccion(19500, 12000, "Guay 80")  
  val _65_80 = new Interseccion(19500, 10500, "65 con 30")  
  val gu_37S = new Interseccion(21000, 12000, "Guay con 37S") 
  }
  
}