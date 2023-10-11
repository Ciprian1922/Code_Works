#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <math.h>
int prime(int n) {
    if (n <= 1)
        return 0;
    if (n <= 3)
        return 1;
    if (n % 2 == 0 || n % 3 == 0)
        return 0;
    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0)
            return 0;
    }
    return 1;
}
int main(int argc, char **argv) {
    int aflag = 0;
    int bflag = 0;
    char *cvalue = NULL;
    int gflag = 0;
    int eflag = 0;
    int evalue = 0;
    double avg100 = 0.0;
    opterr = 0;
    int c;
    while ((c = getopt(argc, argv, "abc:ge:")) != -1) { // Modified 'd' to 'g'
        switch (c) {
            case 'a':
                aflag = 1;
                break;
            case 'b':
                bflag = 1;
                break;
            case 'c':
                cvalue = optarg;
                break;
            case 'g':
                gflag = 1;
                break;
            case 'e':
                eflag = 1;
                evalue = atoi(optarg);
                break;
            case '?':
                if (optopt == 'c' || optopt == 'e')
                    fprintf(stderr, "Option -%c requires an argument.\n", optopt);
                else if (isprint(optopt))
                    fprintf(stderr, "Unknown option `-%c'.\n", optopt);
                else
                    fprintf(stderr,
                            "Unknown option character `\\x%x'.\n",
                            optopt);
                return 1;
            default:
                abort();
        }
    }
    if (gflag) {
        int prime_count = 0;
        int sum = 0;
        for (int i = 2; prime_count < 100; i++) {
            if (prime(i)) {
                sum += i;
                prime_count++;
            }
        }
        avg100 = (double)sum / prime_count;
        printf("avg of the first 100 numbers equals to:  %lf\n", avg100);
    }
    if (eflag) {
        double cvalue_avg = 0.0;
        int cvalue_count = 0;
        if (cvalue != NULL) { /// Check if cvalue is provided, because if not, average cannot be computed
            for (int i = 2; cvalue_count < atoi(cvalue); i++) {
                if (prime(i)) {
                    cvalue_avg += i;
                    cvalue_count++;
                }
            }
            cvalue_avg /= cvalue_count;
        }
        int evalue_count = 0;
        int evalue_sum = 0;
        for (int i = 2; evalue_count < evalue; i++) {
            if (prime(i)) {
                evalue_sum += i;
                evalue_count++;
            }
        }
        double evalue_avg = (double)evalue_sum / evalue_count;
        double result;
        if (cvalue != NULL) {
            result = (cvalue_avg + evalue_avg) / 2.0;
            printf("AVG(%s, %d) = %lf\n", cvalue, evalue, result);
        } else {
            printf("cvalue is null, so average cannot be computed.\n");
        }
    }
    printf("aflag = %d, bflag = %d, cvalue = %s\n", aflag, bflag, cvalue);
    if (eflag) {
        printf("evalue = %d\n", evalue);
    }
    return 0;
}
