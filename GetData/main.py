import search
import sys
import os
import shutil

if len(sys.argv) < 3:
    raise Exception("Query is empty")

repositories_list = search.GetRepositoriesList(int(sys.argv[1]), int(sys.argv[2]))

if os.path.exists('Query'):
    shutil.rmtree('Query')
os.makedirs('Query')

if os.path.exists("data"):
    shutil.rmtree("data")
os.makedirs("data")

tot_query_numbers = 0
done_repositories_number = 0
for repositories in repositories_list:
    print("Now search in id " + str(repositories["id"]));
    new_query_numbers = search.find_query(repositories, tot_query_numbers);
    print("New query number: " + str(new_query_numbers))
    if new_query_numbers > 0:
        os.system('java -jar QueryMethodFinder.jar ' + str(tot_query_numbers + 1) +
                  ' ' + str(tot_query_numbers + new_query_numbers))
        input_java_log = open("new_data.log", "r")
        new_method_l, new_method_r = map(int, input_java_log.readline().split(' '))

        os.system("python3 ../GetRelativeCodes/getcode.py " + str(new_method_l + 1) + " " + str(new_method_r))

    tot_query_numbers += new_query_numbers
    print("Total query number: " + str(tot_query_numbers))
    done_repositories_number += 1
    log_output = open("search.log", "w")
    log_output.write(str(done_repositories_number))
    log_output.close()

print("Search finished, Total query number: " + str(tot_query_numbers))
