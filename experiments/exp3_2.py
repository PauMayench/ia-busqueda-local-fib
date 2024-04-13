import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(42)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(2)]

#iteracionsLlista = [1, 10, 50 ,100]
iteracionsLlista = [500 , 1000]
for i, stiter in enumerate(iteracionsLlista):
    for seedP in seedsProblema:
        pool.add_SA(f'sa_it{i+4}', 200, 5, 50, 5, seedP, 'G', 'h1', 1000000, stiter, 50, 0.1)



pool.execute_parallel(8)