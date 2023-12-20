#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char **argv) {a
    int aflag = 0;
    int bflag = 0;
    char *cvalue = NULL;
    int evalue = 0; // Initialize evalue to 0
    int index;
    int c;

    opterr = 0;

    while ((c = getopt(argc, argv, "abc:ge:")) != -1) {
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
                // The -g option ORs the existing values of aflag, bflag, and cvalue (if it's an integer)
                if (cvalue != NULL) {
                    int cv = atoi(cvalue); // Convert cvalue to an integer
                    aflag = aflag | 1;
                    bflag = bflag | 1;
                    evalue = cv | evalue;
                }
                break;
            case 'e':
                // The -e option ORs the existing values of aflag, bflag, cvalue, and evalue (if they are integers)
                if (cvalue != NULL) {
                    int cv = atoi(cvalue); // Convert cvalue to an integer
                    int ev = atoi(optarg); // Convert the -e argument to an integer
                    aflag = aflag | 1;
                    bflag = bflag | 1;
                    evalue = (ev | evalue);
                }
                break;
            case '?':
                if (optopt == 'c')
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

    printf("aflag = %d, bflag = %d, cvalue = %s\n", aflag, bflag, cvalue);
    printf("aflag | bflag | cvalue = %d\n", aflag | bflag | atoi(cvalue)); // OR the integer value of cvalue
    printf("evalue = %d\n", evalue);

    for (index = optind; index < argc; index++)
        printf("Non-option argument %s\n", argv[index]);
    return 0;
}
