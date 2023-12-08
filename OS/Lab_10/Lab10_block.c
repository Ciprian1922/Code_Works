#include <stdlib.h>
#include <signal.h>
#include <math.h>

// Function to perform a computation with signal blocking
void computation() {
    // Block all signals during the execution of this code
    sigset_t allSignals;
    sigfillset(&allSignals);
    sigprocmask(SIG_BLOCK, &allSignals, NULL);

    // Actual computation code
    int i;
    float array[100];
    for (i = 0; i < 100; i++)
        array[i] = 1000 * sin(cos((float)i) + tan((float)(100 - i)));

    // Restore the pending signals
    sigprocmask(SIG_UNBLOCK, &allSignals, NULL);

    // Exiting the process after the computation
    exit(0);
}

int main() {
    // Calling the computation function with signal blocking
    computation();

    return 0;
}
