import random
from LSexecutionPool import LSexecutionPool

'''
# add_HC(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic):
# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):


pool.add_HC("execution-g-h2", 200, 5, 50, 5, 123, 'G', 'h2')

pool.add_SA('sa_output', 200, 5, 50, 5, 0, 'G', 'h2', 1000, 100, 5, 0.95, 33)


make run 200 5 50 5 1 HC R h1
'''

pool = LSexecutionPool() 

random.seed(42)



seedsProblema = [random.randint(0, 1000) for _ in range(10)]


for i, seedP in enumerate(seedsProblema): 
    random.seed(42)
    seedsRandom = [random.randint(0, 1000) for _ in range(5)]
    for j, seedR in enumerate(seedsRandom):
        pool.add_HC(f"exp2-execucio-{i}-{j}", 200, 5, 50, 5, seedP, 'R', 'h1', seedR)
    pool.add_HC(f"exp2-execucio-G-1", 200, 5, 50, 5, seedP, 'G', 'h1')



pool.execute_parallel(8)