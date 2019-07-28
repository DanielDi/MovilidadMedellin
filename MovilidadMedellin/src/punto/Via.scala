package punto

class Via(var interO: Interseccion,var interF: Interseccion,var velocidad: Int, 
    var tipoVia: TipoVia, var sentido: Sentido,var num: String, var nom: String)
extends Recta{
  
  var origen= interO
  var fin= interF
  var distancia = math.pow((math.pow((fin.xI - origen.xI),2) + math.pow((fin.yI - origen.yI),2)),0.5)
  var Y = (fin.yI - origen.yI)
  var X = (fin.xI - origen.xI)
  var pendiente = Y/X
  var anguloVia = (math.atan(pendiente))
  var anguloVia2 = anguloVia + math.Pi
  
//  var anguloVia = {
//    var negA = (fin.yI - origen.yI)
//    var negB = (fin.xI - origen.xI)
//    var pendiente = negA/negB
//    var suma = (math.atan(pendiente))
//    println(this.nom+" "+negA+" - "+negB)
//    if(negA==0 && negB<0) suma += math.Pi
//    suma
//  }
  
//  if(negB<0 && negA >=0) anguloVia -= math.Pi/2
//  if(negB>=0 && negA <0) anguloVia -= math.Pi
  
}  