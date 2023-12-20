#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>

#define NUM_CHAIRS 5
sem_t waitingRoomMutex;  //semaphore for access to the waiting room
sem_t barberMutex;      //semaphore for access to the barber
int clientsWaiting = 0;     //no. of clients waiting
int clientsServed = 0;       //no. of clients served
int clientsUnsatisfied = 0; //no. of clients unsatisfied

void *barber(void *arg){
    while(1) {
        sem_wait(&waitingRoomMutex);

        if(clientsWaiting > 0){
            clientsWaiting--;
            sem_post(&waitingRoomMutex);

            printf("The barber is cutting client's hair.\n");
            sleep(1);  //simulation of haircut time

            sem_post(&barberMutex);
            clientsServed++;
        } else{
            sem_post(&waitingRoomMutex);
            printf("Barber is sleeping.\n");
            sem_wait(&barberMutex);  //wait for the client
        }
    }
}
void *client(void *arg){
    sem_wait(&waitingRoomMutex);

    if(clientsWaiting < NUM_CHAIRS){
        clientsWaiting++;
        sem_post(&waitingRoomMutex);
        printf("The client is waiting.\n");
        sem_wait(&barberMutex);
        printf("The client is getting a haircut.\n");
    } else{
        sem_post(&waitingRoomMutex);
        printf("Unfortunately the client is leaving unsatisfied (no more seats available =).\n");
        clientsUnsatisfied++;
    }
    return NULL;
}

int main(int argc, char *argv[]){
    int numClients = 10; //setting the number of clients
    pthread_t barberThread, clientThreads[numClients];
    sem_init(&waitingRoomMutex, 0, 1);
    sem_init(&barberMutex, 0, 0);
    pthread_create(&barberThread, NULL, barber, NULL);

    for(int i = 0; i < numClients; i++){
        pthread_create(&clientThreads[i], NULL, client, NULL);
        sleep(1); //delay between client arrivals
    }
    for(int i = 0; i < numClients; i++){
        pthread_join(clientThreads[i], NULL);
    }
    //wait for all threads to finish
    pthread_join(barberThread, NULL);
    printf("Total clients served: %d\n", clientsServed);
    printf("Total clients unsatisfied: %d\n", clientsUnsatisfied);

    //destroy the semaphores
    sem_destroy(&waitingRoomMutex);
    sem_destroy(&barberMutex);

    return 0;
}
