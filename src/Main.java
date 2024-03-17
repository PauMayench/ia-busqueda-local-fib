import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import IA.DistFS.Servers;
import IA.DistFS.Requests;


import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

/*
*  Comprovar a cada experiment:
*   - parametres generacio problema, seed
*   - algorisme correcte
*   - solucio inicial (amb seed o no)
*   - heurisic correcte
*   - parametres algorisme
*   - prints activats
*
* */

public class Main {
    static String SimulatedAnnealing = "SA";
    static String HillClimbing = "HC";

    private static void generateProblem(int seed){

        int number_of_users = 200;                  //200
        int max_number_files_user_can_request = 5;  //5
        int number_servers = 50;                    //50
        int minimum_replications_per_file = 5;      //5

        try {
            Requests requests = new Requests(number_of_users, max_number_files_user_can_request, seed);
            Servers servers = new Servers(number_servers, minimum_replications_per_file, seed);
            LSState.InitializeStatic(requests, servers, number_servers);
        }
        catch(Exception e){
            System.out.println("error parametres");
        }
    }

    public static void main(String[] args){

        String algorithm = HillClimbing; //pot ser SimulatedAnnealing , HillClimbing o ""

        int seed = 120; //seed dels generadors del problema
        generateProblem(seed);


        if (Objects.equals(algorithm, HillClimbing)){
            // HILL CLIMBING ------------------------------------------------------------------------------------
            print("HILL CLIMBING");


            LSState initial_state = new LSState();
            HeuristicFunction heuristic = new LSHeuristicFunction1();

            LSHillClimbingSearch(initial_state, heuristic);

            initial_state.printSolution();

            print("end HILL CLIMBING");
        }

        if(Objects.equals(algorithm, SimulatedAnnealing)) {
            // SIMULATED ANNEALING ------------------------------------------------------------------------------
            print("SIMULATED ANNEALING");


            LSState initial_state = new LSState();
            HeuristicFunction heuristic = new LSHeuristicFunction1();
            int steps = 2000;        // 2000
            int stiter = 100;        // 100
            int k = 5;               // 5
            double lambd = 0.001;    // 0.001

            LSSimulatedAnnealingSearch(initial_state, heuristic, steps, stiter, k, lambd);

            initial_state.printSolution();

            print("end SIMULATED ANNEALING");

        }

    }


    private static void LSHillClimbingSearch(LSState initial_state, HeuristicFunction heuristicFunction) {
        try {
            Problem problem =  new Problem(initial_state, new LSSuccessorFunctionHC(), new LSGoalTest(), heuristicFunction);
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
        } catch (Exception e) {
            System.out.println("error en hill climbing");
            e.printStackTrace();
        }
    }

    private static void LSSimulatedAnnealingSearch(LSState initial_state, HeuristicFunction heuristicFunction, int steps, int stiter, int k, double lambd) {
        try {

            Problem problem =  new Problem(initial_state,new LSSuccessorFunctionSA(), new LSGoalTest(), heuristicFunction);

            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,stiter,k,lambd);

            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            System.out.println();

        } catch (Exception e) {
            System.out.println("error en simulated annealing");
            e.printStackTrace();
        }
    }

    //fa un print si verbose es true
    private static void print(String s){
        boolean verbose = true;
        if (verbose) {
            System.out.println(s);
        }
    }
}


