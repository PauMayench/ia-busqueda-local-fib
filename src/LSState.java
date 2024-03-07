import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Random;

public class LSState {



  private static Requests requests;
  private static Servers servers;
  private int [] totalTimeServers;
  private int [] serverRequests;

  //Inicialitza
  public static void InitializeStatic(Requests req, Servers serv) {
    requests = req;
    servers = serv;
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

  //Generació full Random de la solució inicial (pot ser una solució molt dolenta).
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
  

  // Operadors:


  // totalTimeServers: vector amb cada pos el temps ple del servidor
  // serverRequests: index es els index dels requests i el valor el index del servidor
  // input index dels paquets
  // retorna boolean si pot fer el swap
  public boolean swap(int orig, int dest) {
      // comprobar que es pot fer el swap (no amb mateix servidor)

      // fer swap

  }

  // move

}





