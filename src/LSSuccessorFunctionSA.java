//import LSHeuristicFunction;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.util.Pair;
import java.util.Random;

import java.util.*;


public class LSSuccessorFunctionSA implements SuccessorFunction {
    private static Random random;

    public LSSuccessorFunctionSA() {
        random = new Random();
    }

    public LSSuccessorFunctionSA(long seed) {
       random = new Random(seed);
    }

    public List getSuccessors(Object state){

        LSState original_state = (LSState) state;
        ArrayList<Successor> retVal = new ArrayList<>();
        LSState new_state = new LSState(original_state.getTotalTimeServers(), original_state.getServerRequests());

        int[] requests_servers = original_state.getServerRequests();
        int size = requests_servers.length;

        //Nombre de moves possibles
        int number_of_moves = 0;
        for (int id_request = 0;  id_request < size; id_request++ )
            number_of_moves += new_state.getServersOfRequest(id_request).size();

        //Llista de swaps possibles
        ArrayList<int[]> swaps = new ArrayList<>();
        for (int id_request = 0; id_request < size ; id_request++ ){
            int own_server = requests_servers[id_request];

            for (int id_request2 = id_request + 1; id_request2 < size; ++id_request2){  //iterem sobre les altres requests per a veure tots els swaps possibles
                int destination_server = requests_servers[id_request2];
                if (requests_servers[id_request2] != requests_servers[id_request]    &&  //comprovem que no es troben en el mateix servidor
                        new_state.requestInServer(id_request, destination_server)    &&  // comprovem que la request podria anar al servidor de la segona request
                        new_state.requestInServer(id_request2, own_server)           ){  //comprovem que la segona request pot anar al nostre servidor
                    swaps.add(new int[]{id_request, id_request2});
                }
            }
        }

        int total_number_operators = number_of_moves + swaps.size();
        double probability_to_move = (double) number_of_moves / total_number_operators;

        //segons la probabilitat fem un move o un swap
        if (probability_to_move <= Math.random()) {
            //fem un MOVE random
            int move_id = randomRandint(0, number_of_moves - 1);

            int id_request = 0;
            int total_moves = new_state.getServersOfRequest(id_request).size();
            while(move_id > total_moves) {
                id_request++;
                total_moves += new_state.getServersOfRequest(id_request).size();
            }

            // escollirem dels servers de id_request el total_moves - move_id

            int server_id_location = total_moves - move_id;
            List<Integer> possible_servers = new ArrayList<>(new_state.getServersOfRequest(id_request));
            int id_server = possible_servers.get(server_id_location);
            new_state.moveRequest(id_request, id_server);
        }
        else {
            //SWAP a random request
            int[] id_request_pair = swaps.get(randomRandint(0, swaps.size()-1));
            new_state.swapRequests(id_request_pair[0], id_request_pair[1]);
        }

        retVal.add(new Successor("", new_state));
        return retVal;

    }

    //retorna un enter random entre min i max, els dos inclosos
    private static int randomRandint(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }


}

