import aima.search.framework.HeuristicFunction;

/*
CRITERI 2: Minimizar el tiempo total de transmisión de los ficheros (suma de todos los tiempos de transmisión),
pero con la restricción de que los tiempos de transmisión de los servidores han de ser lo más similares
posibles entre ellos.
*/
public class LSHeuristicFunction2 implements HeuristicFunction  {

    public double getHeuristicValue(Object state) {
        LSState estatActual = (LSState) state;
        // Càlcul de la mitjana.
        int[] totalTimeServers = estatActual.getTotalTimeServers();
        int sum = 0;
        int mida = totalTimeServers.length;
        for (int i : totalTimeServers) sum += i;
        double average = (double)sum / mida;

        // Càlcul de les desviacions de cada servidor respecte la mitjana.
        double variance = 0.0;
        for (int serverTime : totalTimeServers) {
            double v = serverTime - average;
            variance += (v*v);
        }

        // PREGUNTA: RETORNAR VARIANCE O VARIANCE + SUM???
        return (variance); // Quadrat de la desviació respecte a la mitjana de temps entre tots els servidors.
    }

}
