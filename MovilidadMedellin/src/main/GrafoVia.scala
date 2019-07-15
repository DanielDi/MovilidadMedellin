package main


import scalax.collection.edge.WLUnDiEdge
import scala.collection.mutable.ArrayBuffer
import punto.Via
import punto.Interseccion
import scalax.collection.mutable.Graph
import scalax.collection.edge.Implicits._


object GrafoVia {
  var g= Graph[Interseccion,WLUnDiEdge]()
  
  def construir(vias :ArrayBuffer[Via]){
    vias.foreach(x => g.add(WLUnDiEdge.apply(x.origen,x.fin)(x.distancia, x.nom)))
    println(g.nodes.mkString("\n"))
  }
  
  //def n(n1: Interseccion) = g.get(n1)
  
  def menorCamino(n1: Interseccion, n2: Interseccion){
    var nodo1= g.get(n1)
    var nodo2= g.get(n2)
 
  }
}