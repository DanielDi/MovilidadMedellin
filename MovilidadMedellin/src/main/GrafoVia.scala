package main


import scalax.collection.edge.WLDiEdge
import scala.collection.mutable.ArrayBuffer
import punto.Via
import punto.Interseccion
import scalax.collection.mutable.Graph
import scala.collection.Traversable
import scalax.collection.GraphTraversal

object GrafoVia {
  val g= Graph[Interseccion,WLDiEdge]()
  
  def construir(vias :ArrayBuffer[Via]){
    vias.foreach(x => if(x.sentido.doblevia) {
                      g.add(WLDiEdge(x.origen,x.fin)(x.distancia, x.nom))
                      g.add(WLDiEdge(x.fin,x.origen)(x.distancia, x.nom))
                      }else{
                         g.add(WLDiEdge(x.origen,x.fin)(x.distancia, x.nom))
                      })
  }
 
  
  def menorCamino(n1: Interseccion, n2: Interseccion)={
    var nodo1= g.get(n1)
    var nodo2= g.get(n2)
    var path = nodo1.shortestPathTo(nodo2).get
    path.nodes
  }
}