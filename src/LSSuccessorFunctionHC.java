import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;


public class LSSuccessorFunctionHC implements SuccessorFunction {
    //@SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) {

        ArrayList retVal = new ArrayList();  // llista que contindra tots els successors
        LSState state = (LSState) aState;
        LSHeuristicFunction LSHF = new LSHeuristicFunction();

        int numRequests = state.getNumRequests();

        // per tots els requests    fer Swap
        for (int r1 = 0; r1 < numRequests; r1++) {
            for (int r2 = r1 + 1; r2 < numRequests; r2++) {  // j = i + 1 al fer swaps evitem tractar el mateix request i simetrics

                LSState newState = new LSState(state.getTotalTimeServers(), state.getServerRequests());

                boolean swapFet = newState.swapRequests(r1, r2);
                
                if (swapFet) {
                    retVal.add(new Successor("", newState));
                }
            }
        }
        
        int numServers = getNumServers();

        // per tots els requests      fer Move
        for (int r = 0; r < numRequests; r++) {
            
            // per tots els servidors
            for (int s = 0; s < numServers; s++) {

                LState newState2 = new LSState(state.getTotalTimeServers(), state.getServerRequests());

                boolean moveFet = newState2.moveRequest(r, s);   // moure paquet a un altre servidor

                if (moveFet) {
                    retVal.add(new Successor("", newState2));
                }
            }
        }

        return retVal;
    }
}
