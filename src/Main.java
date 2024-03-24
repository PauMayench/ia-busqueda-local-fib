import java.util.*;

import IA.DistFS.Servers;
import IA.DistFS.Requests;


import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

public class Main {
    static String SimulatedAnnealing = "SA";
    static String HillClimbing = "HC";
    static boolean print_all_info = false;
    
    private static void generateProblem(int seed, String[] args){

        int number_of_users = -1;                  //200
        int max_number_files_user_can_request = -1;  //5
        int number_servers = -1;                    //50
        int minimum_replications_per_file = -1;      //5

        try {
            number_of_users = Integer.parseInt(args[0]);
            max_number_files_user_can_request = Integer.parseInt(args[1]);
            number_servers = Integer.parseInt(args[2]);
            minimum_replications_per_file = Integer.parseInt(args[3]);
        } catch (NumberFormatException e) {
            usage("Some parameter/s of teh problem generation could not be parsed:" + "\nnumber_of_users=" + args[0] + "\nmax_number_files_user_can_request=" + args[1] + "\nnumber_servers=" + args[2] + "\nminimum_replications_per_file=" + args[3], 3 );
        }

        try {
            Requests requests = new Requests(number_of_users, max_number_files_user_can_request, seed);
            Servers servers = new Servers(number_servers, minimum_replications_per_file, seed);
            LSState.InitializeStatic(requests, servers, number_servers);
        }
        catch(Exception e){ // la creacio del Servers pot llançar excepcio si: nrep/nserv > 0.5   (el fitxer esta replicat a mes del 50% dels servidors
            usage("minimum_replications_per_file/number_servers must be < 0.5 ", 3);
        }
    }

    public static void main(String[] args){
        // argv[0] number_of_users
        // argv[1] max_number_files_user_can_request
        // argv[2] number_servers
        // argv[3] minimum_replications_per_file
        // argv[4] seed per a generar el problema, si es 0 es una seed random
        // argv[5] Algorisme HC SA
        // argv[6] Estat inicial G R
        // argv[7] heuristic h1->heuristic1  h2->heuristic2
        // argv[8] steps
        // argv[9] stiter
        // argv[10] k
        // argv[11] lambd
        // argv[8/12] seed per al Random initialize, sense aqeust parametre es una seed random


        if (args.length < 8) usage("must use 8 arguments at least", 2);

        if(print_all_info) displayArgsInfo(args);

        //Seed
        String seedARG = args[4];
        int seed = 0;
        try {
            seed = Integer.parseInt(seedARG);
        } catch (NumberFormatException e) {
            usage("args[4] must be a number or 0, it is: " + seedARG, 2);
        }
        if (seed == 0) {
            seed = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        }

        //Search algorithm
        String algorithmARG = args[5];
        if(!Arrays.asList("SA", "HC").contains(algorithmARG) ) usage("args[5] must be HC or SA, it is: " + algorithmARG ,2);

        //Initial Algorithm
        String intitial_algorithmARG = args[6];
        if(!Arrays.asList("G", "R").contains(intitial_algorithmARG) ) usage("args[6] must be G or R, it is: " + intitial_algorithmARG ,2);

        //seed for Random initial Algorithm
        int seedRandomAlg = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        int argsIndex = 8;
        if(algorithmARG.equals("SA")) argsIndex = 12;
        if (args.length == argsIndex + 1) {
            try {
                seed = Integer.parseInt(args[argsIndex]);
            } catch (NumberFormatException e) {
                usage("args[8(HC) or 12(SA)] must be a number, it is: " + args[argsIndex], 2);
            }
        }


        //Heuristic
        String heuristicARG = args[7];
        if(!Arrays.asList("h1", "h2").contains(heuristicARG) ) usage("args[7] must be h1 or h2, it is: " + heuristicARG ,2);




        // MAIN

        generateProblem(seed, args);

        //initialize state
        LSState state = new LSState();
        if(intitial_algorithmARG.equals("G")) state.initializeGreedy();
        if(intitial_algorithmARG.equals("R")) state.initializeRandom(seed);

        //printSolution(state, "INITIAL STATE"); // Initial Solution

        //heuristic
        HeuristicFunction heuristic = new LSHeuristicFunction1();
        if(heuristicARG.equals("h2"))  heuristic = new LSHeuristicFunction2();

        //Hill Climbing
        if (Objects.equals(algorithmARG, HillClimbing)) {
            LSHillClimbingSearch(state, heuristic);
        }
        //Simulated Annealing
        else if(algorithmARG.equals(SimulatedAnnealing)) {
            if (args.length < 12) usage("to use SA you need at least 12 arguments", 2);

            int steps = -1;
            int stiter = -1;
            int k = -1;
            double lambd = -1;

            try {
                steps = Integer.parseInt(args[8]);
                stiter = Integer.parseInt(args[9]);
                k = Integer.parseInt(args[10]);
                lambd = Double.parseDouble(args[11]);
            } catch (NumberFormatException e) {
                usage("Some parameter/s on the SA could not be parsed:" + "\nsteps=" + args[8] + "\nstiter=" + args[9] + "\nk=" + args[10] + "\nlambd=" + args[11], 3 );
            }



            LSSimulatedAnnealingSearch(state, heuristic, steps, stiter, k, lambd, seed);

        }
    }


    private static void LSHillClimbingSearch(LSState initial_state, HeuristicFunction heuristicFunction) {
        try {
            if(print_all_info) System.out.println("___________________________________ STARTING SEARCH __________________________________\n");

            Problem problem =  new Problem(initial_state, new LSSuccessorFunctionHC(), new LSGoalTest(), heuristicFunction);
            Search search =  new HillClimbingSearch();
            SearchAgent agent = new SearchAgent(problem,search);

            if(print_all_info) printEndSearchInfo(agent, search);

        } catch (Exception e) {
            System.out.println("error en hill climbing");
            e.printStackTrace();
        }
    }

    private static void LSSimulatedAnnealingSearch(LSState initial_state, HeuristicFunction heuristicFunction, int steps, int stiter, int k, double lambd, int seed) {
        try {
            if(print_all_info) System.out.println("___________________________________ STARTING SEARCH __________________________________\n");

            Problem problem =  new Problem(initial_state,new LSSuccessorFunctionSA(seed), new LSGoalTest(), heuristicFunction);
            Search search =  new SimulatedAnnealingSearch(steps,stiter,k,lambd);
            //search.traceOn();
            SearchAgent agent = new SearchAgent(problem,search);
            
            if(print_all_info) printEndSearchInfo(agent, search);


        } catch (Exception e) {
            System.out.println("error en simulated annealing");
            e.printStackTrace();
        }
    }

    private static void usage(String errorMessage, int exitCode){
        System.out.println("\nERROR MESSAGE: \n" + errorMessage );
        System.out.println("\nUsage: make run <number_of_users> <max_number_files_user_can_request> <number_servers> <minimum_replications_per_file> <seed> <algorithm> <initial_state> <heuristic> [<steps> <stiter> <k> <lambda>] []");
        System.out.println("where:");
        System.out.println("\t<number_of_users>:\t\t\tThe number of users generating requests.");
        System.out.println("\t<max_number_files_user_can_request>:\tThe maximum number of files a user can request.");
        System.out.println("\t<number_servers>:\t\t\tThe number of servers storing the files.");
        System.out.println("\t<minimum_replications_per_file>:\tThe minimum number of replications for each file across the servers.");
        System.out.println("\t<seed>:\t\t\t\tAn integer value for seeding the problem generation. Use 0 for a random seed.");
        System.out.println("\t<algorithm>:\t\t\tHC (Hill Climbing) or SA (Simulated Annealing)");
        System.out.println("\t<initial_state>:\t\tG (Greedy Initial State) or R (Random Initial State)");
        System.out.println("\t<heuristic>:\t\t\th1 (Heuristic 1) or h2 (Heuristic 2)");
        System.out.println("\nFor Simulated Annealing (SA) algorithm, the following additional parameters are required:");
        System.out.println("\t<steps>:\t\t\tThe number of steps in the SA algorithm.");
        System.out.println("\t<stiter>:\t\t\tThe number of iterations per step in the SA algorithm.");
        System.out.println("\t<k>:\t\t\t\tThe k parameter in the SA algorithm.");
        System.out.println("\t<lambda>:\t\t\tThe lambda cooling coefficient in the SA algorithm.");
        System.out.println("\nFor the Random initializer algorithm, the following parameter is optional");
        System.out.println("\t<random_initializer_seed>:\t\t\tThe seed for the Random initializer algorithm, if lefts blank its a random seed");
        System.out.println("\nExample:");
        System.out.println("make run 5 2 10 3 0 HC G h1");
        System.out.println("make run 200 5 50 5 0 SA R h2 1000 100 5 0.95\n");
        System.exit(exitCode);
    }

    public static void displayArgsInfo(String[] args) {
        if (args.length < 8) {
            return;
        }

        String[] keys = {
                "number_of_users",
                "max_number_files_user_can_request",
                "number_servers",
                "minimum_replications_per_file",
                "seed",
                "algorithm",
                "initial_state",
                "heuristic"
        };

        String[] saKeys = {"steps", "stiter", "k", "lambd"};

        // Find the longest key for padding
        int maxKeyLength = 0;
        for (String arg : args) {
            if (arg.length() > maxKeyLength) {
                maxKeyLength = arg.length();
            }
        }
        System.out.println("\n___________________________________ CALL INFO __________________________________\n\n");

        for (int i = 0; i < args.length; ++i) {
            String key = i < keys.length ? keys[i] : (i - keys.length < saKeys.length ? saKeys[i - keys.length] : "unknown");
            // Padding the parameter for alignment, switch positions of key and args[i]
            System.out.printf("%-" + (maxKeyLength + 3) + "s: %-20s [%d]\n", args[i], key, i);
        }

                    System.out.println("________________________________________________________________________________\n");

        System.out.print("\n\n");

    }

    // Coeficient de Variacio CV  com mes baix el valor millor, vol dir que els valors estan propers a la mitjana i balancejat tot
    // es calcula dividint la desviacio estandard per la mitjana
    public static double calculateCoefficientOfVariation(int[] totalServersTime) {

        int sizeServers = totalServersTime.length;

        // calcular mitjana
        double sum = 0.0;
        for (int value : totalServersTime) sum += value;

        double mean = sum / sizeServers;

        // Calcular la desviacio estandar
        double variance = 0;
        for (int time : totalServersTime) {
            variance += Math.pow(time - mean, 2);
        }
        double standardDeviation = Math.sqrt(variance / sizeServers);

        // Calcular el coeficient de variacio
        double coefficientOfVariation = standardDeviation / mean;

        // Nomes tenir 3 decimals
        coefficientOfVariation = Math.round(coefficientOfVariation * 1000.0) / 1000.0;

        return coefficientOfVariation;
    }


    public static void printTotalTimeTransmissionAndMax(int[] totalTimeServers) {
        int totalTime = 0;
        int max = -1;
        for (int time : totalTimeServers) {
            totalTime += time;
            if (time > max) {
                max = time;
            }
        }
        System.out.println("\n\nTotal time transmission: " + totalTime);
        System.out.println("\nTime of the slowest server (H1 minimitza) " + max);
    }

    // Imprimeix l'estat solucio, els servidors amb el temps emplenat i cada request el servidor que va
    public static void printSolution(LSState state, String name_display) {
        System.out.println("\n\n--------------------------------------- " + name_display +" ---------------------------------------\n");

        int[] totalTimeServers = state.getTotalTimeServers();
        int numServers = totalTimeServers.length;

        System.out.println("Num servers: " + numServers + " - vector amb cada pos el temps ple del servidor:\n");

        for (int i = 0; i < numServers; i++) System.out.print(totalTimeServers[i] + " ");

        printTotalTimeTransmissionAndMax(totalTimeServers);

        double CV = calculateCoefficientOfVariation(totalTimeServers);

        System.out.println("\nCoeficient de Correlacio (H2 minimitza): " + CV);

        System.out.println("\n");
        int[] serverRequests = state.getServerRequests();
        int numRequests = serverRequests.length;
        System.out.println("Num requests: " + numRequests + " - Requests al server que van:\n");
        for (int i = 0; i < numRequests; i++) System.out.print(serverRequests[i] + " ");

        System.out.println("\n--------------------------------------------------------------------------------------");
    }

    private static void printInstrumentation(Properties properties) {
        System.out.println("PROPERTIES:");
        Iterator keys = properties.keySet().iterator();

        while(keys.hasNext()) {
            String key = (String)keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        System.out.println("ACTIONS:");
        for(int i = 0; i < actions.size(); ++i) {
            String action = (String)actions.get(i);
            System.out.println(action);
        }

    }

    private static void printEndSearchInfo(SearchAgent agent, Search search){
            System.out.println("____________________________________ SEARCH ENDED ____________________________________\n");


            printActions(agent.getActions());
            printInstrumentation(agent.getInstrumentation());

            LSState finalState = (LSState) search.getGoalState();
            printSolution(finalState, "FINAL STATE");



            System.out.println();
            System.out.println();
    }
}




