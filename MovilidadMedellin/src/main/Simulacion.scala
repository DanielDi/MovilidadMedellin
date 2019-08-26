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
  var tRefresh = parametrosSimulacion.tRefresh
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
    arrayDeNodoSema.foreach(_.arraySemaforo(0).estado = "Verde")
    Connection.saveVehiculo()
    Connection.saveViaje()
    Connection.saveSemaforos()
    
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
    
    var bus = new Bus(genVelocidad() ,genTasaAc())
    arrayDeVehiculos += bus
    arrayDeViajes += Viaje(bus)(arrayDeIntersecciones(1).copy(), arrayDeIntersecciones(33).copy())
    
    
    while (arrayDeVehiculos.length < totalVehiculos) {
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
//    val niquia = new Interseccion(300, 12000, Some("Niquia"))     
//    val lauraAuto = new Interseccion(2400, 11400, Some("M. Laura Auto"))     
//    val lauraReg = new Interseccion(2400, 12600, Some("M. Laura Reg"))     
//    val ptoCero = new Interseccion(5400, 12000, Some("Pto 0"))    
//    val mino = new Interseccion(6300, 15000, Some("Minorista"))    
//    val villa = new Interseccion(6300, 19500, Some("Villanueva")) 
//    val ig65 = new Interseccion(5400, 10500, Some("65 Igu"))    
//    val robledo = new Interseccion(5400, 1500, Some("Exito Rob"))    
//    val colReg = new Interseccion(8250, 12000, Some("Col Reg"))    
////    val colFerr = new Interseccion(8250, 15000, "Col Ferr")     
//    val col65 = new Interseccion(8250, 10500, Some("Col 65"))     
//    val col80 = new Interseccion(8250, 1500, Some("Col 80"))    
//    val juanOr = new Interseccion(10500, 19500, Some("Sn Juan Ori"))    
//    val maca = new Interseccion(10500, 12000, Some("Macarena"))    
//    val expo = new Interseccion(12000, 13500, Some("Exposiciones"))   
//    val reg30 = new Interseccion(13500, 15000, Some("Reg 30"))     
//    val monte = new Interseccion(16500, 15000, Some("Monterrey"))     
//    val agua = new Interseccion(19500, 15000, Some("Aguacatala"))     
//    val viva = new Interseccion(21000, 15000, Some("Viva Env"))   
//    val mayor = new Interseccion(23400, 15000, Some("Mayorca"))   
//    val ferrCol = new Interseccion(8250, 15000, Some("Ferr Col"))   
//    val ferrJuan = new Interseccion(10500, 15000, Some("Alpujarra"))   
//    val sanDiego = new Interseccion(12000, 19500, Some("San Diego"))   
//    val premium = new Interseccion(13500, 19500, Some("Premium"))    
//    val pp = new Interseccion(16500, 19500, Some("Parque Pob"))    
//    val santafe = new Interseccion(19500, 18750, Some("Santa Fe"))    
//    val pqEnv = new Interseccion(21000, 18000, Some("Envigado"))     
//    val juan65 = new Interseccion(10500, 10500, Some("Juan 65"))   
//    val juan80 = new Interseccion(10500, 1500, Some("Juan 80"))    
//    val _33_65 = new Interseccion(12000, 10500, Some("33 con 65"))  
//    val bule = new Interseccion(12000, 7500, Some("Bulerias"))   
//    val gema = new Interseccion(12000, 1500, Some("St Gema"))    
//    val _30_65 = new Interseccion(13500, 10500, Some("30 con 65"))   
//    val _30_70 = new Interseccion(13500, 4500, Some("30 con 70"))    
//    val _30_80 = new Interseccion(13500, 1500, Some("30 con 80"))    
//    val bol65 = new Interseccion(11100, 10500, Some("Boliv con 65"))   
//    val gu10 = new Interseccion(16500, 12000, Some("Guay con 10"))    
//    val terminal = new Interseccion(16500, 10500, Some("Term Sur"))   
//    val gu30 = new Interseccion(13500, 12000, Some("Guay 30"))   
//    val gu80 = new Interseccion(19500, 12000, Some("Guay 80"))  
//    val _65_80 = new Interseccion(19500, 10500, Some("65 con 30"))  
//    val gu_37S = new Interseccion(21000, 12000, Some("Guay con 37S"))
//
//    arrayDeVias.append(new Via(niquia, lauraAuto, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", Some("Auto Norte"),None),      
//      new Via(niquia, lauraReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),       
//      new Via(lauraAuto, lauraReg, 60, TipoVia("Calle"), Sentido.dobleVia, "94", Some("Pte Madre Laura"),None),    
//      new Via(lauraAuto, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "64C", Some("Auto Norte"),None),      
//      new Via(lauraReg, ptoCero, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),     
//      new Via(ptoCero, mino, 60, TipoVia("Calle"), Sentido.dobleVia, "58", Some("Oriental"),None),     
//      new Via(mino, villa, 60, TipoVia("Calle"), Sentido.dobleVia, "58", Some("Oriental"),None),    
//      new Via(ptoCero, ig65, 60, TipoVia("Calle"), Sentido.dobleVia, "55", Some("Iguaná"),None),       
//      new Via(ig65, robledo, 60, TipoVia("Calle"), Sentido.dobleVia, "55", Some("Iguaná"),None),      
//      new Via(ptoCero, colReg, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None), 
//      new Via(colReg, maca, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),     
//      new Via(maca, expo, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),     
//      new Via(expo, reg30, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),   
//      new Via(reg30, monte, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),    
//      new Via(monte, agua, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),   
//      new Via(agua, viva, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None), 
//      new Via(viva, mayor, 80, TipoVia("Carrera"), Sentido.dobleVia, "62", Some("Regional"),None),   
//      new Via(mino, ferrCol, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"),None),  
//      new Via(ferrCol, ferrJuan, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"),None),   
//      new Via(ferrJuan, expo, 60, TipoVia("Carrera"), Sentido.dobleVia, "55", Some("Ferrocarril"),None),      
//      new Via(villa, juanOr, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", Some("Oriental"),None),    
//      new Via(juanOr, sanDiego, 60, TipoVia("Carrera"), Sentido.dobleVia, "46", Some("Oriental"),None),    
//      new Via(sanDiego, premium, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"),None),     
//      new Via(premium, pp, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"),None),    
//      new Via(pp, santafe, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"),None),    
//      new Via(santafe, pqEnv, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"),None),  
//      new Via(pqEnv, mayor, 60, TipoVia("Carrera"), Sentido.dobleVia, "43A", Some("Av Pob"),None),     
//      new Via(ferrCol, colReg, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"),None), 
//      new Via(colReg, col65, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"),None),   
//      new Via(col65, col80, 60, TipoVia("Calle"), Sentido.dobleVia, "450", Some("Colombia"),None),    
//      new Via(juanOr, ferrJuan, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"),None),   
//      new Via(ferrJuan, maca, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"),None),    
//      new Via(maca, juan65, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"),None),     
//      new Via(juan65, juan80, 60, TipoVia("Calle"), Sentido.dobleVia, "44", Some("Sn Juan"),None),  
//      new Via(sanDiego, expo, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"),None),   
//      new Via(expo, _33_65, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"),None),     
//      new Via(_33_65, bule, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"),None),     
//      new Via(bule, gema, 60, TipoVia("Calle"), Sentido.dobleVia, "33", Some("33"),None),      
//      new Via(premium, reg30, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"),None),  
//      new Via(reg30, _30_65, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"),None),     
//      new Via(_30_65, _30_70, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"),None),  
//      new Via(_30_70, _30_80, 60, TipoVia("Calle"), Sentido.dobleVia, "30", Some("30"),None),    
//      new Via(maca, bol65, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"),None),   
//      new Via(bol65, bule, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"),None),   
//      new Via(bule, _30_70, 60, TipoVia("Diagonal"), Sentido.dobleVia, "74B", Some("Boliv"),None),   
//      new Via(juan80, bule, 60, TipoVia("Transversal"), Sentido.dobleVia, "39B", Some("Nutibara"),None),    
//      new Via(pp, monte, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"),None),   
//      new Via(monte, gu10, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"),None),   
//      new Via(gu10, terminal, 60, TipoVia("Calle"), Sentido.dobleVia, "10", Some("10"),None),   
//      new Via(expo, gu30, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"),None),   
//      new Via(gu30, gu10, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"),None),    
//      new Via(gu10, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"),None),    
//      new Via(gu80, gu_37S, 60, TipoVia("Carrera"), Sentido.dobleVia, "52", Some("Av Guay"),None),    
//      new Via(lauraAuto, ig65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"),None),      
//      new Via(ig65, col65, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"),None),      
//      new Via(juan65, col65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"),None),     
//      new Via(bol65, juan65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"),None),     
//      new Via(_33_65, bol65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"),None),    
//      new Via(_30_65, _33_65, 60, TipoVia("Carrera"), Sentido.unaVia, "65", Some("65"),None),     
//      new Via(_30_65, terminal, 60, TipoVia("Carrera"), Sentido.dobleVia, "65", Some("65"),None),  
//      new Via(terminal, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("65"),None),    
//      new Via(robledo, col80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),       
//      new Via(col80, juan80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),    
//      new Via(juan80, gema, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),     
//      new Via(gema, _30_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),     
//      new Via(_30_80, _65_80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),    
//      new Via(_65_80, gu80, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),   
//      new Via(gu80, agua, 60, TipoVia("Carrera"), Sentido.dobleVia, "80", Some("80"),None),   
//      new Via(agua, santafe, 60, TipoVia("Calle"), Sentido.dobleVia, "12S", Some("80"),None),   
//      new Via(viva, pqEnv, 60, TipoVia("Calle"), Sentido.dobleVia, "37S", Some("37S"),None), 
//      new Via(viva, gu_37S, 60, TipoVia("Calle"), Sentido.dobleVia, "63", Some("37S"),None))
//      new CamaraFotoDeteccion(arrayDeVias.last, 20)
//      new CamaraFotoDeteccion(arrayDeVias(0), 10)
//      new CamaraFotoDeteccion(arrayDeVias(50), 20)
//      new CamaraFotoDeteccion(arrayDeVias(20), 20)
//      new CamaraFotoDeteccion(arrayDeVias(15), 20)
//      new CamaraFotoDeteccion(arrayDeVias(5), 20)
//      new CamaraFotoDeteccion(arrayDeVias(41), 20)
//      new CamaraFotoDeteccion(arrayDeVias(30), 20)
  }
  
}