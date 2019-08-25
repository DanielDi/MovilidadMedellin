package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: Option[String])
extends Recta{
  
  var origen= interO
  var fin= interF
  val distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  var anguloVia = angulo()
  
  override def toString = s"interO: $interO interF: $interF"

}  