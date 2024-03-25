import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Set;


public class LSSuccessorFunctionHC implements SuccessorFunction {
    public ArrayList getSuccessors(Object aState) {

        ArrayList<Successor> retVal = new ArrayList<>();  // llista que contindra tots els successors
        LSState state = (LSState) aState;
        LSHeuristicFunction1 LSHF1 = new LSHeuristicFunction1(); // Heurístic 1
        LSHeuristicFunction2 LSHF2 = new LSHeuristicFunction2(); // Heurístic 2

        int numRequests = state.getNumRequests();

        // per tots els requests    fer Swap
        for (int r1 = 0; r1 < numRequests; r1++) {

            // per tots els altres requests
            for (int r2 = r1 + 1; r2 < numRequests; r2++) {  // r2 = r1 + 1 vol dir que al fer swaps evitem tractar el mateix request i simetrics

                LSState newState = new LSState(state.getTotalTimeServers(), state.getServerRequests());
                boolean swapFet = newState.swapRequests(r1, r2);
                
                if (swapFet) {  // comprobar primer que s'hagi pogut fer el swap
                    double    v1 = LSHF1.getHeuristicValue(newState);
                    double    v2 = LSHF2.getHeuristicValue(newState);
                    String S = "SWAP " + r1 + " <-> " + r2 + " H1(" + v1 + ") H2(" + v2 + ")";

                    retVal.add(new Successor(S, newState));
                }
            }
        }


        // per tots els requests      fer Move
        for (int r = 0; r < numRequests; r++) {

            Set<Integer> serversDelRequest = state.getServersOfRequest(r);

            // per tots els servidors que tinguin copia del fitxer del request
            for (int s : serversDelRequest) {

                LSState newState2 = new LSState(state.getTotalTimeServers(), state.getServerRequests());

                boolean moveFet = newState2.moveRequest(r, s);   // moure paquet a un altre servidor

                if (moveFet) {  // comprobar primer que s'hagi pogut fer el move
                    double    v1 = LSHF1.getHeuristicValue(newState2);
                    double    v2 = LSHF2.getHeuristicValue(newState2);
                    String S = "MOVE req " + r + " -> serv " + s + " Heuristic Value1(" + v1 + ") H2(" + v2 + ")";

                    retVal.add(new Successor(S, newState2));
                }
            }
        }


        return retVal;
    }
}
