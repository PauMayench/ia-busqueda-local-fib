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

        // per tots els requests
        for (int i = 0; i < numRequests; i++) {
            for (int j = 0; j < numRequests; j++) {  // o tambe? el tenir j = i + 1 al fer swaps evitem tractar el mateix request i simetrics

                // Swap
                LSState newState = new LSState(state.getTotalTimeServers(), state.getServerRequests());

                newState.swapRequest(i, j);

                double v = LSHF.getHeuristicValue(newState);
                String S = "Swap " + i + " " + j + " Cost(" + v + ")";

                retVal.add(new Successor(S, newState));

                // Move
                LState newState2 = new LSState(state.getTotalTimeServers(), state.getServerRequests());

                newState2.moveRequest(i, );   // index del server  (altre bucle ?)

                double v = LSHF.getHeuristicValue(newState2);
                String S = "Move " + i + " al server" + _______ + " Cost(" + v + ")";

                retVal.add(new Successor(S, newState2));
            }
        }

        return retVal;
    }
}
