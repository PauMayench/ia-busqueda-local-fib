import random
from LSexecutionPool import LSexecutionPool


pool = LSexecutionPool() 

random.seed(4321)

# add_SA(name_file, number_of_users, max_files_user, n_servers, min_rep_file, seed, initial_state, heuristic, steps, stiter, k, lambd):

seedsProblema = [random.randint(0, 1000) for _ in range(20)]
seed = seedsProblema[0]

n_serversList = [50, 100, 150, 200 ,250 , 300, 350, 400, 450, 500, 550, 600, 650, 700 ,750, 800]
for i,n_servers in enumerate(n_serversList):
            for r in seedsProblema:
                pool.add_HC(f'HC-{i+10}', 200, 5, n_servers, 5, r, 'G', 'h1')



pool.execute_parallel(1)
