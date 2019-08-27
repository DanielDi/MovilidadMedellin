package main

import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer

import punto._
import main.{Simulacion => s}
import movil._


object Connection {
  
  val url = "bolt://localhost/7687" 
  val user = "neo4j" //Usuario por defecto
  val pass = "123" //Pass de la bd activa
  
  
  def getSession(): (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
  }
  
  
  def saveInterseccion() = {
	  val (driver, session) = getSession()
    val script = s"""MATCH (n1)-[r:VIA]->(n2)
                     return n1, r , n2"""
    val result = session.run(script)
    
    while(result.hasNext()) {
      val values = result.next().values()
      val int1 = values.get(0)
      val relacion = values.get(1)
      val int2 = values.get(2)
      val nombre = int1.get("nombre").asString()
      val xi = int1.get("xI").asInt()
      val yi = int1.get("yI").asInt()
      
      val nom = relacion.get("nom").asString()
      val num = relacion.get("num").asString()
      val sentidoDoble = relacion.get("sentidoDoble").asBoolean()
      val tipoVia = relacion.get("tipoVia").asString()
      val velocidad = relacion.get("velocidad").asInt()
      
      val nombre2 = int2.get("nombre").asString()
      val xi2 = int2.get("xI").asInt()
      val yi2 = int2.get("yI").asInt()
      val interseccion1 = Interseccion(xi, yi, Some(nombre))
      val interseccion2 = Interseccion(xi2, yi2, Some(nombre2))
      val via = new Via(interseccion1, interseccion2, velocidad, TipoVia(tipoVia), Sentido(!sentidoDoble,sentidoDoble), num, Some(nom), None)
      
      
      val script2 = s"""match (n1:Interseccion {nombre: '$nombre'})-[r:VIA]->(n2:Interseccion {nombre: '$nombre2'})
                     where exists(r.camara)
                     return r"""
      val result2 = session.run(script2)
      
      if(result2.hasNext()) {
        val values = result2.next().values().get(0)
        var distanciaCam = values.get("camara").asInt()
        val cam = new CamaraFotoDeteccion(via,distanciaCam)
        via.camara = Some(cam)
      }
    }
	  session.close()
    driver.close()
  }
  
  def saveVehiculo() = {
    val (driver, session) = getSession()
    s.arrayDeVehiculos.foreach(vehi =>{
      val nombre = vehi.getClass.getCanonicalName.substring(6)
      val tasaAc = vehi.tasaAc
      val tasaDesc = vehi.tasaDes
      val velCru = vehi.velCru
      val magnitud = vehi.vel.magnitud
      val grado = vehi.vel.direccion.grado
      val placa = vehi.placa
    	val script = s"""create(:Vehiculo {nombre: '$nombre', placa: '$placa', velCru: '$velCru', tasaAc: '$tasaAc', magnitud: '$magnitud', grado: '$grado'})"""
		  val result = session.run(script)
    })
    session.close()
    driver.close()
  }
  
  def saveViaje() = {
    val (driver, session) = getSession()
    s.arrayDeViajes.foreach(v => {
      val placa = v.vehiculo.placa
      val interIx = v.auxPos.xI
      val interIy = v.auxPos.yI
      val interFx = v.posFinal.xI
      val interFy= v.posFinal.yI
      val xi = v.posInicial.xI
      val yi = v.posInicial.yI
    	val script = s"""match(v:Vehiculo {placa: '$placa'})
    	                 create(:Viaje {placa: '$placa', interIx: '$interIx',interIy: '$interIy',interFx: '$interFx',interFy: '$interFy', xI: '$xi', yI: '$yi'})-[:RECORRIDO_DE]->(v)"""
      val result = session.run(script)
    })
     session.close()
    driver.close()
  }
  
  def saveSemaforos() = {
    val (driver, session) = getSession()
    
     s.arrayDeNodoSema.foreach(semaNodo => {
      
       val script0 = s"""match(I:Interseccion {xI: ${semaNodo.inter.xI}, yI: ${semaNodo.inter.yI}})
                         create(:NodoSema {pos: ${semaNodo.auxPos}, tiempo: ${semaNodo.auxT}})-[:TIENE]->(I)
         """
       val result0 = session.run(script0) 
       
      semaNodo.arraySemaforo.foreach(sema => {
        val tiempoA = sema.tiempoA
        val tiempoV = sema.tiempoV
        val estado = sema.estado
        val via = sema.via.id  
      
        val ubicacion = semaNodo.inter
        val script = s"""match(I:Interseccion {xI: ${semaNodo.inter.xI}, yI: ${semaNodo.inter.yI}})
            create(s:Semaforo {estado: '$estado', ubicacion: $via, tiempoA: $tiempoA, tiempoV: $tiempoV})-[:ESTA_EN]->(I)
          """       
        val result = session.run(script)  
        })     
      }) 
      
    session.close()
    driver.close()
      
  }
  
  def saveComparendos(){
	  val (driver, session) = getSession()
			  s.comparendos.foreach(c => {
				  val placa = c.vehiculo.placa
						  val velVeh = c.vel.magnitud
						  val velVia = c.vMax
						  val script = s"""match(v:Vehiculo {placa: '$placa'})
						  create(:Comparendo {placa: '$placa', velVeh: $velVeh, velVia: $velVia})-[:EFECTUADO_POR]->(v)"""
						  val result = session.run(script)
			  })
			  
			  session.close()
			  driver.close()     
  }
  
   def cargarVehiculos(){
    val (driver, session) = getSession()
    val script = s"""match(v:Vehiculo)-[:RECORRIDO_DE]-(viaje:Viaje) 
                     return v,viaje"""
    val result = session.run(script)
    
    while(result.hasNext()){
      val values = result.next().values()
      val vehi = values.get(0)
      val viaje = values.get(1)
      val velCrus = vehi.get("velCru").asInt()
      val tasaAc = vehi.get("tasaAc").asInt()
      val nombre = vehi.get("nombre").asString()
      val placa = vehi.get("placa").asString()
      val magnitud = vehi.get("magnitud").asDouble()
      val grado = vehi.get("grado").asDouble()
      
      val vehiculo = nombre match{
        case "Bus" => new Bus(velCrus,tasaAc)
        case "Camion" => new Camion(velCrus,tasaAc)
        case "Carro" => new Carro(velCrus,tasaAc)
        case "Moto" => new Moto(velCrus,tasaAc)
        case "MotoTaxi" => new MotoTaxi(velCrus,tasaAc)
      }
      
      vehiculo.placa = placa
      vehiculo.vel = Velocidad(magnitud, Angulo(grado))
      s.arrayDeVehiculos += vehiculo
      
      val interIx = viaje.get("interIx").asDouble()
      val interIy = viaje.get("interIy").asDouble()
      val interFx = viaje.get("interFx").asDouble()
      val interFy = viaje.get("interFx").asDouble()
      val xI = viaje.get("xI").asDouble()
      val yI = viaje.get("yI").asDouble()
      
      val interI = s.arrayDeIntersecciones.filter(i => (i.xI == interIx) && (i.yI == interIy))(0)
      val interF = s.arrayDeIntersecciones.filter(i => (i.xI == interFx) && (i.yI == interFy))(0)
      val viajeV= Viaje(vehiculo)(interI,interF)
      viajeV.posInicial = Interseccion(xI,yI,None)
      s.arrayDeViajes += viajeV
    }
    session.close()
    driver.close()
  }
   
}