//
// Created by quiefh on 15/05/18.
//

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>

#define CAR_NUMBER 40
#define PLACE_NUMBER 10


typedef struct {
    unsigned int place;
    pthread_mutex_t lock;
    pthread_cond_t cv;
} ParkingGarage;

typedef struct {
    char name[10];
    ParkingGarage *location;
    pthread_t thread;
} Car;

void enter(ParkingGarage *garage, char *id) {
    while (garage->place == 0) {
        pthread_cond_wait(&garage->cv, &garage->lock);
    }
    garage->place--;
    printf("%s: entered\n", id);
}

void leave(ParkingGarage *garage, char *id) {
    garage->place++;
    printf("%s: left\n", id);
    pthread_cond_broadcast(&garage->cv);
}

void *run(void *data) {
    Car *car = (Car*)data;

    while (1) {
        sleep(rand() % 2);
        pthread_mutex_lock(&(car->location->lock));
        enter(car->location, car->name);
        pthread_mutex_unlock(&(car->location->lock));

        sleep(rand() % 5);
        pthread_mutex_lock(&(car->location->lock));
        leave(car->location, car->name);
        pthread_mutex_unlock(&(car->location->lock));
    }
}

int main() {
    time_t t;
    Car cars[CAR_NUMBER];
    ParkingGarage garage = { .place = PLACE_NUMBER };

    srand((unsigned)time(&t));
    if (pthread_mutex_init(&(garage.lock), NULL) != 0) {
        printf("\n mutex init failed\n");
        return (1);
    }
    if (pthread_cond_init(&(garage.cv), NULL)) {
        printf("\n condition init failed\n");
        return (1);
    }
    for (unsigned int i = 0; i < CAR_NUMBER; i++) {
        cars[i] = (Car){ .location = &garage };
        sprintf(cars[i].name, "Car %d", (int)i + 1);
        int err = pthread_create(&cars[i].thread, NULL, &run, &cars[i]);

        if (err) {
            printf("ERROR create thread code is %d\n", err);
            exit(-1);
        }
    }
    for (unsigned int i = 0; i < CAR_NUMBER; i++) {
        int err = pthread_join(cars[i].thread, NULL);

        if (err) {
            printf("ERROR join thread code is %d\n", err);
            exit(-1);
        }
    }
    pthread_cond_destroy(&garage.cv);
    return (0);
}