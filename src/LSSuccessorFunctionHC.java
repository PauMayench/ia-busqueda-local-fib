import IA.probTSP.ProbTSPBoard;
import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;


public class LSSuccessorFunctionHC implements SuccessorFunction {
    @SuppressWarnings("unchecked")
    public ArrayList getSuccessors(Object aState) {

        ArrayList retVal = new ArrayList();  // llista que contindra tots els successors
        LSState state = (LSState) aState;
        LSHeuristicFunction1 LSHF1 = new LSHeuristicFunction1(); // Heurístic 1

        int numRequests = state.getNumRequests();

        double bestChoiceHCSwap = -1.0; //Debugging
        String BS = "";                      //Debugging
        // per tots els requests    fer Swap
        for (int r1 = 0; r1 < numRequests; r1++) {

            // per tots els altres requests
            for (int r2 = r1 + 1; r2 < numRequests; r2++) {  // r2 = r1 + 1 vol dir que al fer swaps evitem tractar el mateix request i simetrics

                LSState newState = new LSState(state.getTotalTimeServers(), state.getServerRequests());
                boolean swapFet = newState.swapRequests(r1, r2);
                
                if (swapFet) {  // comprobar primer que s'hagi pogut fer el swap
                    double    v = LSHF1.getHeuristicValue(newState);
                    String S = "SWAP" + r1 + " <-> " + r2 + " Heuristic Value(" + v + ")";
                    if (bestChoiceHCSwap == -1.0 || bestChoiceHCSwap > v) {
                        bestChoiceHCSwap = v;
                        BS = S;
                    }
                    //System.out.println("S VALUE FOR SWAP: " + S);
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
                    double    v = LSHF1.getHeuristicValue(newState2);
                    String S = "MOVE " + r + " -> " + s + " Heuristic Value(" + v + ")";
                    if (bestChoiceHCSwap == -1.0 || bestChoiceHCSwap > v) {
                        bestChoiceHCSwap = v;
                        BS = S;
                    }
                    //System.out.println("S VALUE FOR MOVE: " + S);
                    retVal.add(new Successor(S, newState2));
                }
            }
        }
        //System.out.println("Absolute best option: " + bestChoiceHCSwap + " for " + BS);
        //System.out.println("END CHILD GENERATION");
        return retVal;
    }
}
