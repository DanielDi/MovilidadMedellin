

object Simulacion extends Runnable {
  
  var dt = 0
  var tRefresh = 0
  var vehiculos = Array[Int]()
  var velocidad = Array[Int]()
  var proporciones = Array[Int]()
  
  var listaDeVehiculos = List[Vehiculo]()
  var listaDeVias = List[Via]()
  
  def run() {
    while(true) {
//      listaDeVehiculos.foreach(_.mover(dt))
//      t += dt
//      Grafico.graficarVehiculos(listaDeVehiculos)
//      
//      Thread.sleep(tRefresh)
    }
  }
  
}