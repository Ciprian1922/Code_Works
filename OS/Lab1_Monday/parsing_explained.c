#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char **argv) {
    int aflag = 0;
    int bflag = 0;
    char *cvalue = NULL;
    int evalue = 0; // Initialize evalue to 0
    int index;
    int c;

    opterr = 0; // Disable getopt() from printing error messages to stderr

    // Loop through command-line options using getopt()
    while ((c = getopt(argc, argv, "abc:ge:")) != -1) {
        switch (c) {
            case 'a':
                aflag = 1; // Set aflag to 1 if -a is present
                break;
            case 'b':
                bflag = 1; // Set bflag to 1 if -b is present
                break;
            case 'c':
                cvalue = optarg; // Set cvalue to the argument of -c
                break;
            case 'g':
                // The -g option ORs the existing values of aflag, bflag, and cvalue (if it's an integer)
                if (cvalue != NULL) {
                    int cv = atoi(cvalue); // Convert cvalue to an integer
                    aflag = aflag | 1;
                    bflag = bflag | 1;
                    evalue = cv | evalue; // OR cv with existing evalue
                }
                break;
            case 'e':
                // The -e option ORs the existing values of aflag, bflag, cvalue, and evalue (if they are integers)
                if (cvalue != NULL) {
                    int cv = atoi(cvalue); // Convert cvalue to an integer
                    int ev = atoi(optarg); // Convert the -e argument to an integer
                    aflag = aflag | 1;
                    bflag = bflag | 1;
                    evalue = (ev | evalue); // OR ev with existing evalue
                }
                break;
            case '?':
                // Handle invalid options or missing arguments
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

    // Print the final values of flags and variables
    printf("aflag = %d, bflag = %d, cvalue = %s\n", aflag, bflag, cvalue);
    printf("aflag | bflag | cvalue = %d\n", aflag | bflag | atoi(cvalue)); // OR the integer value of cvalue
    printf("evalue = %d\n", evalue);

    // Print non-option arguments
    for (index = optind; index < argc; index++)
        printf("Non-option argument %s\n", argv[index]);

    return 0;
}
