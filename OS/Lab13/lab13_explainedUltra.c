/**
Complete the TODOs in the code to implement synchronization of a producer with a consumer
that share a buffer.

The producer will put objects into the buffer as long as the buffer is not full and will
crash when the buffer is full. The producer will be woken up by the consumer after the buffer
is no longer full.

The consumer will pop objects from the buffer as long as the buffer is not empty and will crash
when the buffer is empty. The consumer will be woken up by the producer after the buffer is no
longer empty.

Synchronize access to the shared buffer using condition variables
 */
#include <pthread.h>
#include <unistd.h>

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "utils.h"

// Define constants for the size of the shared buffer, number of iterations, and random delay.

#define BUFFER_SIZE	3
#define NR_ITERATIONS	10
#define RAND_DELAY	-1

// Define the structure for the shared buffer.
typedef struct {
    int buff[BUFFER_SIZE];
    int count;
} buffer_t;

// Initialize the buffer with zeros and set the count to 0
void init_buffer(buffer_t *b)
{
    /// Set all elements to zero
    memset(b->buff, 0, sizeof(int) * BUFFER_SIZE);  /// atatea zerouri cat este bufferul
    b->count = 0;///(*b).count          ^-trebuie populat cu acelasi tip de date (aici ar trebui sa fie char in loc de int)
    /// Initialize the count to zero
}

// Insert an item into the buffer (LIFO - Last In First Out)
void insert_item(buffer_t *b, int item)
{
    // Place the item at the current count position in the buffer.
    b->buff[b->count] = item; ///concept de stiva - insert at the end
    // Increment the count of elements in the buffer.
    b->count++;
}

// Remove an item from the buffer (LIFO)
int remove_item(buffer_t *b)
{ 
    // Decrement the count of elements in the buffer.
    b->count--;   ///concept de stiva- remove from the end
    // Return the item at the current count position.
    return b->buff[b->count];
}

// Check if the buffer is full
int is_buffer_full(buffer_t *b)
{
    return b->count == BUFFER_SIZE;
}

// Check if the buffer is empty
int is_buffer_empty(buffer_t *b)
{
    return b->count == 0;
}
/* The shared buffer where the producer will place items and the consumer will take items
*/
/* the buffer where the producer will place items
 * and from which the consumer will take items
 */
// Global variable representing the shared buffer.
buffer_t common_area;

// Condition variables for signaling whether the buffer is not full or not empty

pthread_cond_t buffer_not_full  = PTHREAD_COND_INITIALIZER;
pthread_cond_t buffer_not_empty = PTHREAD_COND_INITIALIZER;

// Mutex for protecting access to the shared buffer
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

// Producer thread function
void *producer_fn(void *arg)
{
    int item_to_insert;
    int i;
    int rc;
    int delay = *(int *) arg;

    for (i = 0; i < NR_ITERATIONS; i++) {
        item_to_insert = i;

        /* TODO - lock mutex */
        rc = pthread_mutex_lock(&mutex);
        DIE(rc != 0, "pthread_mutex_lock in producer");

        /* TODO - if common area is full, 
                          then wait until the common area is not full */
        if (is_buffer_full(&common_area))
        {
            rc = pthread_cond_wait(&buffer_not_full, &mutex);
            DIE(rc != 0, "pthread_cond_wait in producer");
        }

        /* TODO - insert item into common area */
        insert_item(&common_area, item_to_insert);
        printf("Inserted item %d\n", item_to_insert);

        /* TODO - if we have one element in common area,
                        then signal that the buffer is not empty */
        if (common_area.count == 1){
            rc = pthread_cond_signal(&buffer_not_empty);
            DIE(rc != 0, "pthread_cond_signal in producer");
        }

        /* TODO - unlock mutex */
        pthread_mutex_unlock(&mutex);

        if (delay == RAND_DELAY)
            sleep(rand() % 3);
        else
            sleep(delay);
    }

    return NULL;
}

// Consumer thread function
void *consumer_fn(void *arg)
{
    int item_consumed;
    int i;
    int rc;
    int delay = *(int *) arg;

    for (i = 0; i < NR_ITERATIONS; i++) {
        /* TODO - Lock the mutex */
        rc = pthread_mutex_lock(&mutex);
        DIE(rc != 0, "pthread_mutex_lock in consumer");

        /* TODO - If the common area is empty, wait until it's not empty */
        while (is_buffer_empty(&common_area)) {
            pthread_cond_wait(&buffer_not_empty, &mutex);
        }

        /* TODO - Remove item from the common area */
        item_consumed = remove_item(&common_area);
        printf("\t\tConsumed item %d\n", item_consumed);

        /* TODO - If we have BUFFER_SIZE - 1 elements in the common area, signal that the buffer is not full */
        if (common_area.count == BUFFER_SIZE - 1) {
            rc = pthread_cond_signal(&buffer_not_full);
            DIE(rc != 0, "pthread_cond_signal in consumer");
        }

        /* TODO - Unlock the mutex */
        pthread_mutex_unlock(&mutex);

        if (delay == RAND_DELAY)
            sleep(rand() % 3);
        else
            sleep(delay);
    }

    return NULL;
}

// Function to create and run producer and consumer threads
void run_threads(int producer_delay, int consumer_delay)
{
    int rc;
    pthread_t producer_th;
    pthread_t consumer_th;

    /* initialization */
    init_buffer(&common_area);

    /* create the threads */
    rc = pthread_create(&producer_th, NULL, producer_fn, &producer_delay);
    DIE(rc != 0, "pthread_create");
    rc = pthread_create(&consumer_th, NULL, consumer_fn, &consumer_delay);
    DIE(rc != 0, "pthread_create");

    /* wait for the threads to finish execution */
    rc = pthread_join(producer_th, NULL);
    DIE(rc != 0, "pthread_join");
    rc = pthread_join(consumer_th, NULL);
    DIE(rc != 0, "pthread_join");
}


// Main function
int main(void)
{
    srand(time(NULL));

    /* run fast producer and slow consumer */
    printf("fast producer - slow consumer:\n");
    run_threads(0, 1);
    /* run slow producer and fast consumer */
    printf("slow producer - fast consumer:\n");
    run_threads(1, 0);
    /* run rand producer and rand consumer */
    printf("rand producer - rand consumer:\n");
    run_threads(RAND_DELAY, RAND_DELAY);

    return 0;
}
//prodcons.c
//        Se afișează documentul prodcons.c.
//be careful how you call the funciton, because it depends on it

//pid 0 e copilu iar parintele este mai mare ca 0
