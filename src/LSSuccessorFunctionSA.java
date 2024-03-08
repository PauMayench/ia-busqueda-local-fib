//import LSHeuristicFunction;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LSSuccessorFunctionSA implements SuccessorFunction {
    public List getSuccessors(Object original_state) {

      original_state = (LSState) original_state;
      ArrayList retVal = new ArrayList();
      LSState new_state = new LSState(original_state.getTotalTimeServers(), original_state.getServerRequests());

      

      int nservers = original_state.getNServidors();
      int nrequests = original_state.getNRequests();


      double probabilityMove = probabilityMove(new_state);

      double randomValue = Math.random(); // 0.0 <= randomValue < 1.0
      if (probabilityMove <= randomValue){
        //MOVE

        while( !new_state.move(randomRandint(nrequests - 1), randomRandint(nservers - 1)));
      } 
      else{
        //SWAP

        while( !new_state.swap(randomRandint(nrequests - 1), randomRandint(nrequests - 1)));
      }


      retVal.add(new Successor("", new_state));
      return retVal;
    }
    private int randomRandint(int min, int max){  //includes min and max
      return (int) (Math.random() * ((max - min) + 1)) + min; 
    }
    /*
dades:
  nservidors = nombre de servidors
  nminim = nombre minim de servidors a la que un fitxer esta( estaria be tenir el nombre real)



nombre de moves disponibles per una request:
  x = servidors * (nminim-1)/(servidors -1)

total de moves disponibles = nrequests * x



nombre de swaps disponibles per a una request:

  y = nrequests * ((nminim-1)/(servidors -1) * (nminim-1)/(servidors -1))

total swaps disponibles = (y * nrequests) / 2


amb aixo podem seaber la probabilitat amb la que haurem de fer un move o un swap:


probabilitat de fer un move = nmoves/(nmoves + nswaps)


*/
    private double probabilityMove(LSState state){
      // de moment sempre calcula el mateix aixi que podria ser estatic, o podem adaptarlo a diferents estats
      int nservidors = state.getNServidors();
      int nrequests = state.getNRequests();
      int nminim = state.getNMinimServidorsPerFitxer();
      double moves_per_request = nservidors * (nminim-1)/(nservidors -1)
      double swaps_per_request = nrequests * ((nminim-1)/(nservidors -1) * (nminim-1)/(nservidors -1))

      double total_moves = nrequests * moves_per_request
      double total_swaps = (swaps_per_request * nrequests) / 2

      return total_moves/(total_moves + total_swaps)
    } 


}

