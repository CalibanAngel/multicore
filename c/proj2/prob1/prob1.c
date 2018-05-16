//
// Created by quiefh on 10/05/18.
//

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <omp.h>

#define NUM_END 200000
int NUM_THREAD = 4;

bool is_prime(unsigned int num) {
    int i;
    if (num <= 1) return false;
    for (i = 2; i < num; i++) {
        if ((num % i == 0) && (i != num)) return false;
    }
    return true;
}
// ompsetschedule
int main(int ac, char **av) {
    if (ac != 3) {
        printf("HELP: ./a.out scheduling_type# #_of_thread with the following sheduling type (1=static, 2=dynamic, 3=guided)\n");
        return 1;
    }
    NUM_THREAD = atoi(av[2]);
    omp_set_schedule(atoi(av[1]), 4);

    unsigned int ans = 0;

    omp_set_num_threads(NUM_THREAD);
    double start_t = omp_get_wtime( );

#pragma omp parallel
    {
#pragma omp for reduction(+:ans) schedule(runtime)
        for (unsigned int i = 0; i < NUM_END; i++) {
            if (is_prime(i + 1) == true) ans += 1;
        }
    };

    double end_t = omp_get_wtime( );

    printf("time: %fs; ans = %d\n",end_t - start_t, ans);
    return 0;
}