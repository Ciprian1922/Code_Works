#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#define ARRAY_SIZE 5
#define NUM_PRODUCERS 5

char sharedArray[ARRAY_SIZE] = {'#', '#', '#', '#', '#'};

// Variables to track completion of each producer thread
int producersDone[NUM_PRODUCERS] = {0};

// Mutex for protecting sharedArray
pthread_mutex_t arrayMutex = PTHREAD_MUTEX_INITIALIZER;

// Function executed by each producer thread
void *producerFunction(void *arg) {
    char producerChar = *((char *)arg);

    // Calculate the index for this producer
    int producerIndex = producerChar - 'A';

    long totalCount = (producerIndex + 1) * 100000;

    for (long i = 0; i < totalCount; ++i) {
        pthread_mutex_lock(&arrayMutex);

        // Check if the character is already set
        if (sharedArray[producerIndex] == '#') {
            sharedArray[producerIndex] = producerChar;
            pthread_mutex_unlock(&arrayMutex);
        } else {
            // If the character is already set, release the lock and try again
            pthread_mutex_unlock(&arrayMutex);
            --i;
        }
    }

    // Mark the producer as done
    producersDone[producerIndex] = 1;

    pthread_exit(NULL);
}

// Function executed by the main thread
void mainFunction() {
    while (1) {
        pthread_mutex_lock(&arrayMutex);

        // Display the current state of the array
        for (int i = 0; i < ARRAY_SIZE; ++i) {
            printf("%c ", sharedArray[i]);
        }
        printf("\n");

        pthread_mutex_unlock(&arrayMutex);

        // Check if all producers are done
        int allDone = 1;
        for (int i = 0; i < NUM_PRODUCERS; ++i) {
            if (!producersDone[i]) {
                allDone = 0;
                break;
            }
        }

        // If all producers are done, exit the loop
        if (allDone) {
            break;
        }

        usleep(500000); // Sleep for 0.5 seconds
    }
}

int main() {
    pthread_t producers[NUM_PRODUCERS];
    char producerChars[NUM_PRODUCERS] = {'A', 'B', 'C', 'D', 'E'};

    // Create producer threads
    for (int i = 0; i < NUM_PRODUCERS; ++i) {
        if (pthread_create(&producers[i], NULL, producerFunction, (void *)&producerChars[i])) {
            fprintf(stderr, "Error creating thread\n");
            return 1;
        }
    }

    // Run the main function
    mainFunction();

    // Wait for producer threads to finish
    for (int i = 0; i < NUM_PRODUCERS; ++i) {
        pthread_join(producers[i], NULL);
    }

    return 0;
}
