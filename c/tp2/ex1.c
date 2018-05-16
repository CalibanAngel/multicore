//
// Created by quiefh on 08/05/18.
//

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void *c(void* i) {
    printf("Thread %d says hi\n", (int)i);
    printf("Thread %d says bye\n", (int)i);
}

int main (int argc, char *argv[]) {
    pthread_t threads[20];
    int rc, t;
    printf("main thread start\n");
    for(t = 1; t <= 20; t++){
        rc = pthread_create(&threads[t], NULL, c, (void *)t);
        if (rc) {
            printf("ERROR code is %d\n", rc);
            exit(-1);
        }
    }
    printf("main thread end\n");
    pthread_exit(NULL);
}