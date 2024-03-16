import aima.search.framework.HeuristicFunction;

/*CRITERI 1: Minimización del tiempo de transmisión de los ficheros para el servidor que necesita más tiempo para
transmitir sus peticiones.*/
public class LSHeuristicFunction1 implements HeuristicFunction  {
    public double getHeuristicValue(Object state) {
        LSState estatActual = (LSState)state;
        int[] totalTimeServers = estatActual.getTotalTimeServers();
        int nElems = totalTimeServers.length;
        int max = totalTimeServers[0];
        for(int i = 1; i < nElems; ++i) {
            int actual = totalTimeServers[i];
            if (actual > max) max = actual;
        }
        return max * max; //Quadrat per a augmentar les diferències entre valors.
    }
  
}
