package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: String)
extends Recta{
  
  var origen= interO
  var fin= interF
  var distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  var pendiente = (fin.yI - origen.yI)/(fin.xI - origen.xI)
  var angulovia= math.toDegrees(math.atan(pendiente))
}