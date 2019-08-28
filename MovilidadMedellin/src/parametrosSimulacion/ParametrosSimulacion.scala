package parametrosSimulacion

case class ParametrosSimulacion(dt: Int, tRefresh: Int, vehiculos: Vehiculos, velocidad: Velocidad, 
    proporciones: Proporciones, semaforos: Semaforos, aceleracion: Aceleracion, distanciasFrenadoVehiculos: distanciasFrenadoVehiculos) {
  
}