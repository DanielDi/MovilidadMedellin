package punto

import main.Simulacion

case class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: Option[String], var camara: Option[CamaraFotoDeteccion])
extends Recta{
  
  Simulacion.arrayDeVias += this
  Simulacion.arrayDeVias  = Simulacion.arrayDeVias.distinct
  val id = Simulacion.arrayDeVias.size
  
  var origen= interO
  var fin= interF
  val distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  var anguloVia = angulo()
  
  override def toString = s"interO: $interO interF: $interF"

}  