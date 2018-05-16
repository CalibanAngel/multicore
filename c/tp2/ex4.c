//
// Created by quiefh on 10/05/18.
//

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>


#define NUM_THREAD 4
#define NUM_END 10000

typedef struct sum_thread {
    unsigned int lo;
    unsigned int hi;
    unsigned int *arr;
    unsigned int ans;
} sum_thread;

bool is_prime(unsigned int num) {
    int i;
    if (num <= 1) return false;
    for (i = 2; i < num; i++) {
        if ((num % i == 0) && (i != num)) return false;
    }
    return true;
}

void *run(void *data) {
    sum_thread *th = (sum_thread*)data;
    for (unsigned int i = th->lo; i < th->hi; i++) {
        if (is_prime(i) == true) th->ans += 1;
    }
}

int main() {
    pthread_t threads[NUM_THREAD];

    unsigned int int_arr[NUM_END];
    sum_thread arr_thread[NUM_THREAD];
    unsigned int ans = 0;

    for (unsigned int i = 0; i < NUM_END; i++) {
        int_arr[i] = i + 1;
    }

    time_t start_t = time(NULL);
    for(unsigned int i = 0; i < NUM_THREAD; i++) {
        int rc;

        arr_thread[i] = (sum_thread){ .lo = NUM_END / NUM_THREAD * i, .hi = NUM_END / NUM_THREAD * (i + 1), .arr = int_arr, .ans = 0};
        rc = pthread_create(&threads[i], NULL, run, &arr_thread[i]);
        if (rc) {
            printf("ERROR code is %d\n", rc);
            exit(-1);
        }
    }

    for(unsigned int i = 0; i < NUM_THREAD; i++) {
        int rc;

        rc = pthread_join(threads[i], NULL);
        ans += arr_thread[i].ans;
        if (rc) {
            printf("ERROR code is %d\n", rc);
            exit(-1);
        }
    }
    time_t end_t = time(NULL);
    double diff_t = difftime(end_t, start_t);

    printf("time: %f; ans = %d\n", diff_t, ans);
    pthread_exit(NULL);
}