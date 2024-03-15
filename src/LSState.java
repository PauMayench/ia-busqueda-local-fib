import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Random;
import java.util.Set;

public class LSState {

  private static Requests requests;
  private static Servers servers;

  private static int min_replications_per_file;
  private int [] totalTimeServers;  // vector amb cada pos el temps ple del servidor
  private int [] serverRequests;    // index del vector son els index dels requests i el valor el index del servidor


  //Inicialitza
  public static void InitializeStatic(Requests req, Servers serv, int minRep) {
    requests = req;
    servers = serv;
    min_replications_per_file = minRep;
  }


  //Crea solució incial Greedy temps total mínim.
  public LSState() {
    int numServers = servers.size();
    int numRequests = requests.size();
    totalTimeServers = new int[numServers];
    serverRequests = new int[numRequests];
    for (int i = 0; i < numServers; ++i) totalTimeServers[i] = 0;
    for (int i = 0; i < numRequests; ++i) {
      int[] actualRequest = requests.getRequest(i);
      int userId = actualRequest[0];
      int minTime = -1;
      int minServer = -1;
      for (int j = 0; j < numServers; ++j) {
        int actualTime = servers.tranmissionTime(j,userId);
        if ((actualTime < minTime) || (minTime == -1)) {
          minTime = actualTime;
          minServer = j;
        }
      }
      serverRequests[i] = minServer; //Assignem request al sevidor minServer.
      totalTimeServers[minServer] += minTime; //Afegim al total del servidor el temps de la nova request.
    }
  }

  //Generació full Random de la solució inicial (pot generar una solució molt dolenta).
  public LSState(long seed) {
    Random random = new Random(seed);
    int numServers = servers.size();
    int numRequests = requests.size();
    totalTimeServers = new int[numServers];
    serverRequests = new int[numRequests];
    for (int i = 0; i < numServers; ++i) totalTimeServers[i] = 0;
    for (int i = 0; i < numRequests; ++i) {
      int randomServer = (random.nextInt()) % numServers;
      int userId = requests.getRequest(i)[0];
      int RandServTime = servers.tranmissionTime(randomServer, userId);
      serverRequests[i] = randomServer;
      totalTimeServers[randomServer] += RandServTime;
    }
  }

  //Crear un nou estat clon d'un altre (per a aplicar operadors a partir d'aquest).
  public LSState (int[] actualTotalTimeServers, int[] actualServerRequests) {
    totalTimeServers = actualTotalTimeServers;
    serverRequests = actualServerRequests;
  }

  //Getters
  public int[] getTotalTimeServers() {return totalTimeServers;}
  public int[] getServerRequests() {return serverRequests;}

    //PRECAUCIÓ: El temps d'un request CANVIA ENTRE SERVIDORS (DEPÈN DEL SERVIDOR AL QUE FEM LA REQUEST).
    public int getRequestTime(int idReq) {
      int userId = requests.getRequest(idReq)[0];
      int idServer = serverRequests[idReq];
      return servers.tranmissionTime(idServer, userId);
    }

    public int getNumRequests() {
      return requests.size();
    }

    public int getMinReplicationsPerFile() {return min_replications_per_file;}

    public int getNumServers() {return servers.size();}

  public LSState copyState() {
    LSState newState = new LSState();
  }


    // donat un request que conte un file, retorna el set amb tots els servers que tenen replicat el file
    public Set<Integer> getServersOfRequest(int request) {
        // obtenir el file
        int[] UsrFile = requests.getRequest(request);
        int file = UsrFile[1];

        Set<Integer> ServersOfFile = servers.fileLocations(file);  // conjunt amb tots els servers del file

        return ServersOfFile;
    }

    // donat el request i el server, retorna true, si el file del request es troba en el server
    public boolean requestInServer(int request, int server) {
                return false;
    }


    // Operadors:

    // Operador de swap entre dos peticions (fitxers)
    // Input: dos index del les dos peticions a intercanviar
    // Output: boolean de si ha pogut fer el swap, en cas que pugui fa el swap
    public boolean swapRequests(int id_req1, int id_req2) {
        // comprobar que es pot fer el swap (no amb mateix servidor)
        if (serverRequests[id_req1] == serverRequests[id_req2]) { // estan al mateix server
            return false;
        }

        // Intentar fer swap amb ell mateix
        if (id_req1 == id_req2) return false;

        // fer swap
        int serv1_original = serverRequests[id_req1];
        int serv2_original = serverRequests[id_req2];


        // TODO: enviar els dos req i servers alhora a la consultora, aixi nomes fa una consulta a tots els servers?

        // comprobar que el file del primer request estigui al server que es vol fer el swap
        if (!requestInServer(id_req1, serv2_original)) { // TODO: et passo request i server i em reotrna bool si el file del req hi es al server
            return false;
        }

        // comprobar que el file del segon request, estigui al server del primer request
        if(!requestInServer(id_req2, serv1_original)) {
            return false;
        }

        serverRequests[id_req2] = serverRequests[id_req1];  // request 2 te el server del req 1
        serverRequests[id_req1] = serv2_original;

        // actualitzar temps totals a totalTimeServers

        // TODO: temps double? (esta en milisegons)
        int time_req1 = getRequestTime(id_req1);  // temps que tarda el paquet individual,  passantli el paquet i el servidor
        int time_req2 = getRequestTime(id_req2);

        // diferencia: surti positiva o negativa, es el que li hem de sumar al temps del segon server  (i al primer server restar)
        // manera eficient de nomes fer un access al vector
        int diferencia_temps = time_req1 - time_req2;

        totalTimeServers[id_req2] += diferencia_temps;
        totalTimeServers[id_req1] -= diferencia_temps;

        return true;
    }

    // Operador per moure un request a un altre servidor
    // Input: index request  index servidor
    // Output: boolean de si ha pogut fer el move, en cas que pugui el mou
    public boolean moveRequest(int id_req, int id_serv) {
        // comprobar que es pot moure (no al mateix servidor que es troba)
        if (serverRequests[id_req] == id_serv) {
            return false;
        }

        // fer move
        int id_serv_original = serverRequests[id_req];

        serverRequests[id_req] = id_serv;  // request pasa a un nou servidor

        // actualitzar temps
        int time_req1 = getRequestTime(id_req);

        totalTimeServers[id_serv_original] -= time_req1;  // treure el temps que ocupava el paquet al servidor original
        totalTimeServers[id_serv] += time_req1;

        return true;
    }

}





