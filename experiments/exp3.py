import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(42)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(10)]


for _, seedP in enumerate(seedsProblema): 
    ks = [10, 50, 500, 5000, 50000]
    lambds = [0.0001, 0.001, 0.01, 1]
    for il, lambd in enumerate(lambds):
        for ik, k in enumerate(ks):
            pool.add_SA(f'sa_L{il}-K{ik}', 200, 5, 50, 5, seedP, 'G', 'h1', 100000, 100, k, lambd)



pool.execute_parallel(8)