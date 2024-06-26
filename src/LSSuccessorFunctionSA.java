import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;


import java.util.Random;
import java.util.ArrayList;
import java.util.List;


public class LSSuccessorFunctionSA implements SuccessorFunction {
    private static Random random;
    int countIterations;
    public LSSuccessorFunctionSA() {

        random = new Random();
    }

    public LSSuccessorFunctionSA(long seed) {
       random = new Random(seed);
        countIterations = 0;
    }

    public List getSuccessors(Object state){
        countIterations +=1;
        LSState original_state = (LSState) state;
        ArrayList<Successor> retVal = new ArrayList<>();
        LSState new_state = new LSState(original_state.getTotalTimeServers(), original_state.getServerRequests());

        int[] requests_servers = original_state.getServerRequests();
        int size = requests_servers.length;
        /*
        // print per l'exepriment:
        int[] totalTimeServers = original_state.getTotalTimeServers();
        int max = -1;
        for (int time : totalTimeServers) {
            if (time > max) {
                max = time;
            }
        }
        //if(countIterations%10 == 0)
            System.out.println(max);
        */


        //Nombre de moves possibles
        int number_of_moves = 0;
        for (int id_request = 0;  id_request < size; id_request++ )
            number_of_moves += new_state.getServersOfRequest(id_request).size() -1;

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

        String S  = "";
        LSHeuristicFunction1 LSHF1 = new LSHeuristicFunction1(); // Heurístic 1
        LSHeuristicFunction2 LSHF2 = new LSHeuristicFunction2(); // Heurístic 2

        //segons la probabilitat fem un move o un swap
        double p = Math.random();
        if (probability_to_move >= p) {
            //fem un MOVE random
            int move_id = randomRandint(1, number_of_moves);

            int id_request = 0;
            int total_moves = new_state.getServersOfRequest(id_request).size() -1;
            while(move_id > total_moves) {
                id_request += 1;
                total_moves += new_state.getServersOfRequest(id_request).size() -1;
            }

            // escollirem dels servers de id_request el total_moves - move_id
            int server_id_location = total_moves - move_id;
            List<Integer> possible_servers = new ArrayList<>(new_state.getServersOfRequest(id_request));
            int own_server = requests_servers[id_request];
            //possible_servers.remove(own_server);
            possible_servers.removeIf(s -> s==own_server);
            int id_server = possible_servers.get(server_id_location);
            new_state.moveRequest(id_request, id_server);

            double    h1 = LSHF1.getHeuristicValue(new_state);
            double    h2 = LSHF1.getHeuristicValue(new_state);
            S = "MOVE   r:" + id_request + " to server: " + id_server + "\t\t h1=" + h1 +  " h2=" + h2;
        }
        else {
            //SWAP a random request
            int[] id_request_pair = swaps.get(randomRandint(0, swaps.size()-1));
            new_state.swapRequests(id_request_pair[0], id_request_pair[1]);

            double    h1 = LSHF1.getHeuristicValue(new_state);
            double    h2 = LSHF1.getHeuristicValue(new_state);

            S = "SWAP   " + id_request_pair[0] + " <-> " + id_request_pair[1] + "\t\t h1=" + h1 +  " h2=" + h2;
        }



        retVal.add(new Successor(S, new_state));
        return retVal;

    }

    //retorna un enter random entre min i max, els dos inclosos
    private static int randomRandint(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }


}

