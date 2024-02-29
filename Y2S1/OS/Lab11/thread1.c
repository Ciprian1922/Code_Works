#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

void functie(void *threadid) {
    printf("I am the thread(son). My id is %ld\n", (long)threadid);
    pthread_exit((void *)6);
    //return threadid;
    // Or return (void *)6;
}

int main(int argc, char *argv[]) {
    pthread_t thread;
    long id = 5;
    int rc = pthread_create(&thread, NULL, (void *)functie, (void *)id);

    if (rc) {
        printf("ERROR: return code from pthread_create() is %d\n", rc);
        exit(-1);
    }

    printf("This is the father!\n");

    void *status;
    pthread_join(thread, &status);

    printf("Everything was completed! Status %ld\n", (long)status);

    // Or pthread_exit(NULL);
    exit(0);
}
