import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;


public class LSSuccessorFunction implements SuccessorFunction {
    @SuppressWarnings("unchecked")
    public List getSuccessors(Object aState) {
        ArrayList                retVal = new ArrayList();
        Board             board  = (ProbTSPBoard) aState;
        HeuristicFunction TSPHF  = new HeuristicFunction();



        return retVal;
    }
}
