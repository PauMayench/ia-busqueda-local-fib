import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import IA.DistFS.Servers;
import IA.DistFS.Requests;


import IA.probTSP.ProbTSPGoalTest;
import IA.probTSP.ProbTSPHeuristicFunction;
import IA.probTSP.ProbTSPSuccessorFunction;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;


public class Main {
    
    public static void main(String[] args){


        /*
        LSState initial_state = createInitialState();

        HeuristicFunction heuristic= new LSHeuristicFunction(); //podem fer que sigui una altre funcio

        int steps = 2000;       // 2000
        int stiter = 100;       // 100
        int k = 5;              // 5
        double lambd = 0.001;    // 0.001
        LSSimulatedAnnealingSearch(initial_state, heuristic, steps,stiter,k, lambd);


        */

    }

    private static LSState createInitialState(){

        int number_of_users = 100;
        int max_number_files_user_can_request = 10;
        int number_servers = 20;
        int minimum_replications_per_file = 7;
        int seed = 10;

        Requests requests = new Requests(number_of_users, max_number_files_user_can_request, seed);
        try {
            Servers servers = new Servers(number_servers, minimum_replications_per_file, seed);
            LSState.InitializeStatic(requests, servers);
        }
        catch(Exception e){
            System.out.println("error parametres");
        }

        return new LSState();
    }

    private static void LSSimulatedAnnealingSearch(LSState initial_state, HeuristicFunction heuristicFunction, int steps, int stiter, int k, double lambd) {
        try {

            Problem problem =  new Problem(initial_state,new LSSuccessorFunctionSA(), new LSGoalTest(), heuristicFunction);

            SimulatedAnnealingSearch search =  new SimulatedAnnealingSearch(steps,stiter,k,lambd);

            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void LSHillClimbingSearch(LSState initial_state) {
        try {
            Problem problem =  new Problem(initial_state, new LSSuccessorFunction(), new LSGoalTest(),new heuristicFunction());
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            System.out.println();
            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
}


