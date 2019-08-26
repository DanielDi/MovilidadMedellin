package main

import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer

import punto._
import main.{Simulacion => s}


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
    val script = s"""MATCH (n1)-[r:VIA]-(n2)
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
      val interI = v.auxPos
      val interF = v.posFinal
      val xi = v.posInicial.xI
      val yi = v.posInicial.yI
    	val script = s"""match(v:Vehiculo {placa: '$placa'})
    	                 create(:Viaje {placa: '$placa', interI: '$interI', interF: '$interF', xI: '$xi', yI: '$yi'})-[:RECORRIDO_DE]->(v)"""
      val result = session.run(script)
    })
     session.close()
    driver.close()
  }
  
  def saveSemaforos() = {
    val (driver, session) = getSession()
    s.arrayDeSemaforos.foreach(sema => {
      val tiempoA = sema.tiempoA
      val tiempoV = sema.tiempoV
      val estado = sema.estado
      val ubicacion = sema.ubicacion
      val via = sema.via
      println("AZOPOTAMADRE")
      val inter = if(via.interO.x == ubicacion.xI && via.interO.y == ubicacion.yI) via.interF else via.interO
      val script = s"""match(I:Interseccion {xI: ${inter.xI}, yI: ${inter.yI} })
                        match(U:Interseccion {xI: ${ubicacion.xI}, yI: ${ubicacion.yI} })
                        create(s:Semaforo {estado: '$estado', ubicacion: '${ubicacion.nombre}', tiempoA: $tiempoA, tiempoV: $tiempoV})
                        create(s)-[r:CONTRARIO_A]->(I)
                        create(s)-[:ESTA_EN]->(U)
                        """
      val result = session.run(script)
    })
    session.close()
    driver.close()
    
  }
  
  
}
//match(U:Interseccion {xI: '${ubicacion.xI}', yI: '${ubicacion.yI}'})
//                       match(I:Interseccion {xI: '${inter.xI}', yI: '${inter.yI}'})
//                       create(s:Semaforo {estado: '$estado', tiempoA: '$tiempoA', tiempoV: '$tiempoV'})
//                       create(s)-[r:CONTRARIO_A]->(I)
//                       create(s)-[:ESTA_EN]->(U)