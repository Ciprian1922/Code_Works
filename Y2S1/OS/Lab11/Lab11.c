#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define ARRAY_SIZE 5
#define CHARACTERS "ABCDE"

pthread_t producerThreads[5];//five threads for each producer
char sharedArray[ARRAY_SIZE] = {'#', '#', '#', '#', '#'}; //shared array of characters
volatile long characterCounts[5] = {0};  //variables to track the number of characters added by each producer(also synchronized)
volatile int producersFinished[5] = {0}; //flags to indicate whether each producer has finished
pthread_mutex_t sharedArrayMutex = PTHREAD_MUTEX_INITIALIZER; //mutex for protecting shared resources

//adding characters to the shared array
void* producer(void* arg){
    int producerId = *((int*)arg);

    while (characterCounts[producerId] < (producerId + 1) * 100000){  //ensuring that each producer adds a different number of characters
        char character = CHARACTERS[producerId];
        pthread_mutex_lock(&sharedArrayMutex);   //accessing and updating sharedArray(this is necessary)

        if (characterCounts[producerId] < (producerId + 1) * 100000){
            //add the character to a random location in the array
            sharedArray[rand() % ARRAY_SIZE] = character;
            characterCounts[producerId]++;
        }
        pthread_mutex_unlock(&sharedArrayMutex);
        usleep(10); //sleep for a short time to simulate work in progress...
    }
    producersFinished[producerId] = 1; //set the flag to indicate that this producer has finished
    return NULL;
}

//printing the contents of the shared array
void printSharedArray(){
    for (int i = 0; i < ARRAY_SIZE; ++i){
        printf("%c ", sharedArray[i]);
    }
    printf("\n");
}
int main(){
    int producerIds[5] = {0, 1, 2, 3, 4};
    //create producer threads
    for (int i = 0; i < 5; ++i){
        pthread_create(&producerThreads[i], NULL, producer, &producerIds[i]);
    }
    //the main thread is continuously printing the array until all producer threads finish
    while (1){
        pthread_mutex_lock(&sharedArrayMutex); //lock to protect access to shared resources
        printSharedArray();
        pthread_mutex_unlock(&sharedArrayMutex);

        //check if all producer threads have finished
        int allFinished = 1;
        for (int i = 0; i < 5; ++i){
            if (!producersFinished[i]){
                allFinished = 0;
                break;
            }
        }
        if (allFinished){
            break;
        }
        usleep(500000); //sleep for a short time between the prints
    }
    //wait for all producer threads to finish
    for (int i = 0; i < 5; ++i){
        pthread_join(producerThreads[i], NULL);
    }
    pthread_mutex_destroy(&sharedArrayMutex);  //clean up and exit
    return 0;
}
