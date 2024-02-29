#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <ctype.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>

#define uint32_t unsigned int
#define MAX_THREADS 2

uint32_t MAX = 0;
char *buffer;
uint32_t vowel_count[MAX_THREADS]; // global variable

void *compute(void *arg) {
    uint32_t i, threadID = *((uint32_t *)arg);
    free(arg);

    // my thread with ID "n" starts from
    // n * (MAX / MAX_THREADS) ... (N+1) * (MAX / MAX_THREADS)- 1
    for (i = threadID * (MAX / MAX_THREADS); i < (threadID + 1) * (MAX / MAX_THREADS); i++)
        if (i < MAX) {
            char c = (char)tolower(buffer[i]);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u')
                vowel_count[threadID]++;
        }

    return NULL;
}

uint32_t getLength(FILE *file) {
    fseek(file, 0, SEEK_END);
    uint32_t len = ftell(file);
    fseek(file, 0, SEEK_SET);
    return len;
}

int main(int argc, char **argv) {
    uint32_t i, vowels = 0, start = time(NULL), end;
    if (argc < 2) {
        printf("Usage: %s <text file name>\n", argv[0]);
        return 1;
    }
    FILE *f = fopen(argv[1], "r");
    if (!f) {
        perror("Error opening file");
        return 1;
    }

    MAX = getLength(f);
    buffer = (char *)malloc(sizeof(char) * MAX);

    if (!buffer) {
        perror("Error allocating memory");
        fclose(f);
        return 1;
    }

    fread(buffer, sizeof(char), MAX, f);
    fclose(f);

    pthread_t t[MAX_THREADS];
    for (i = 0; i < MAX_THREADS; i++) {
        uint32_t *p = (uint32_t *)malloc(sizeof(uint32_t));
        if (!p) {
            perror("Error allocating memory for thread argument");
            free(buffer);
            return 1;
        }
        *p = i;
        pthread_create(&t[i], NULL, compute, p);
    }

    for (i = 0; i < MAX_THREADS; i++)
        pthread_join(t[i], NULL);

    for (i = 0; i < MAX_THREADS; i++)
        vowels += vowel_count[i];

    end = time(NULL);
    printf("I found %u vowels in %u seconds!\n", vowels, end - start);

    free(buffer);
    return 0;
}
