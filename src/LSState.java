import IA.DistFS.Requests;
import IA.DistFS.Servers;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import static java.lang.Math.abs;

public class LSState {

    private static Requests requests;
    private static Servers servers;

    private static int numberOfServers;

    // Representacio de l'estat
    private int [] totalTimeServers;    // index del vector els servers i els valors el temps total de tranmissio dels fitxers del server
    private int [] serverRequests;      // index del vector son els index dels requests i el valor el index del servidor


    // Inicialitzacio Requests i Servers

    // nomes els parametres statics que representen els Requests i Servers del Distributed File System
    // aquesta funcio es crida abans de cridar a qualsevol constructora
    public static void InitializeStatic(Requests req, Servers serv, int numServ) {
        requests = req;
        servers = serv;
        numberOfServers = numServ;
    }

    // Constructores

    // Constructora buida
    public LSState() {
        totalTimeServers = new int[numberOfServers];
        serverRequests = new int[requests.size()];
    }

    //Crear un nou estat clon d'un altre (per a aplicar operadors a partir d'aquest).
    public LSState(int[] actualTotalTimeServers, int[] actualServerRequests) {
        totalTimeServers = Arrays.copyOf(actualTotalTimeServers, actualTotalTimeServers.length);
        serverRequests = Arrays.copyOf(actualServerRequests, actualServerRequests.length);
    }


    // Generacio solucions inicials  (Estats solucions)

    //Crea solució incial Greedy temps total mínim.
    public void initializeGreedy() {
        int numServers = numberOfServers;
        int numRequests = requests.size();
        totalTimeServers = new int[numServers];
        serverRequests = new int[numRequests];
        for (int i = 0; i < numServers; ++i) totalTimeServers[i] = 0;
        for (int i = 0; i < numRequests; ++i) {
            int[] actualRequest = requests.getRequest(i);
            Set<Integer> availableServers = servers.fileLocations(actualRequest[1]);
            int[] availableServA = new int[availableServers.size()];
            int counter = 0;
            for (Integer v : availableServers) {
                availableServA[counter] = v;
                ++counter;
            }
            int userId = actualRequest[0];
            int minTime = -1;
            int minServer = -1;
            for (int j = 0; j < availableServA.length; ++j) {
                int serverId = availableServA[j];
                int actualTime = servers.tranmissionTime(serverId, userId);
                if ((actualTime < minTime) || (minTime == -1)) {
                    minTime = actualTime;
                    minServer = serverId;
                }
            }
            serverRequests[i] = minServer; //Assignem request al sevidor minServer.
            totalTimeServers[minServer] += minTime; //Afegim al total del servidor el temps de la nova request.
        }
    }

    //Generació full Random de la solució inicial (pot generar una solució molt dolenta).
    public void initializeRandom(long seed) {
        Random random = new Random(seed);
        int numServers = numberOfServers;
        int numRequests = requests.size();
        totalTimeServers = new int[numServers];
        serverRequests = new int[numRequests];
        for (int i = 0; i < numServers; ++i) totalTimeServers[i] = 0;
        for (int i = 0; i < numRequests; ++i) {
            int[] req = requests.getRequest(i);
            Set<Integer> availableServers = servers.fileLocations(req[1]);
            int[] availableServA = new int[availableServers.size()];
            int counter = 0;
            for (Integer v : availableServers) {
                availableServA[counter] = v;
                ++counter;
            }
            int randServId = (abs(random.nextInt())) % availableServA.length;
            int randomServer = availableServA[randServId];
            int userId = req[0];
            int RandServTime = servers.tranmissionTime(randomServer, userId);
            serverRequests[i] = randomServer;
            totalTimeServers[randomServer] += RandServTime;
        }
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


    // donat un request que conte un file, retorna el set amb tots els servers que tenen replicat el file
    public Set<Integer> getServersOfRequest(int request) {
        int file = requests.getRequest(request)[1];
        Set<Integer> ServersOfFile = servers.fileLocations(file);  // conjunt amb tots els servers del file

        return ServersOfFile;
    }

    // donat el request i el server, retorna true, si el file del request es troba en el server
    public boolean requestInServer(int request, int server) {
        int file = requests.getRequest(request)[1];
        Set<Integer> ServersOfFile = servers.fileLocations(file);

        return ServersOfFile.contains(server);  // retorna true si el server esta al set
    }


    // Operadors:

    // Operador Swap per intercanviar dos peticions (fitxers)
    // Input: dos index del les dos peticions a intercanviar
    // Output: boolean de si ha pogut fer el swap, en cas que pugui fa el swap
    public boolean swapRequests(int id_req1, int id_req2) {
        // comprobar que es pot fer el swap (no amb mateix servidor)
        if (serverRequests[id_req1] == serverRequests[id_req2]) { // estan al mateix server
            return false;
        }
        // Intentar fer swap amb ell mateix
        if (id_req1 == id_req2) return false;

        // servidors originals
        int serv1_original = serverRequests[id_req1];
        int serv2_original = serverRequests[id_req2];

        // comprobar que el file del primer request estigui al server que es vol fer el swap
        if (!requestInServer(id_req1, serv2_original)) {
            return false;
        }
        // comprobar que el file del segon request, estigui al server del primer request
        if (!requestInServer(id_req2, serv1_original)) {
            return false;
        }

        // actualitzar temps totals a totalTimeServers, restar temps de treure el request

        int time_req1Old = getRequestTime(id_req1);  // temps que tarda el paquet individual,  passantli el paquet i el servidor
        int time_req2Old = getRequestTime(id_req2);

        totalTimeServers[serv1_original] -= time_req1Old;
        totalTimeServers[serv2_original] -= time_req2Old;

        // fer swap

        serverRequests[id_req2] = serv1_original; // request 2 te el server del req 1
        serverRequests[id_req1] = serv2_original;

        // actualitzar temps totals a totalTimeServers, sumar els nous temps

        int time_req1New = getRequestTime(id_req1);  // temps que tarda el paquet individual,  passantli el paquet i el servidor
        int time_req2New = getRequestTime(id_req2);

        totalTimeServers[serv2_original] += time_req1New;
        totalTimeServers[serv1_original] += time_req2New;

        return true;
    }

    // Operador Move per moure un request a un altre servidor
    // Input: index request  index servidor
    // Output: boolean de si ha pogut fer el move, en cas que pugui el mou
    public boolean moveRequest(int id_req, int id_serv) {
        // comprobar que es pot moure (no al mateix servidor que es troba)
        // no comprobem que el fitxer del request tb hi sigui replicat al server desti, al successor function, ja mira nomes els disponibles
        if (serverRequests[id_req] == id_serv) {
            return false;
        }

        int id_serv_original = serverRequests[id_req];
        // actualitzar temps, restar al treure'l d'alla
        int time_reqServOrigin = getRequestTime(id_req);
        totalTimeServers[id_serv_original] -= time_reqServOrigin;  // treure el temps que ocupava el paquet al servidor original

        // fer move
        serverRequests[id_req] = id_serv;  // request pasa a un nou servidor

        // actualitzar temps, en el servidor desti
        int time_reqServDestiny = getRequestTime(id_req);

        totalTimeServers[id_serv] += time_reqServDestiny;

        return true;
    }
    
}





