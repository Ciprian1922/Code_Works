#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>

long long int ListFiles(const char *dirPath);
void DisplayFileSize(const char *filePath);

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <director>\n", argv[0]);
        return 1;
    }

    const char *dirPath = argv[1];

    struct stat st;
    if (stat(dirPath, &st) || !S_ISDIR(st.st_mode)) {
        fprintf(stderr, "Invalid folder path.\n");
        return 1;
    }

   ListFiles(dirPath);

    return 0;
}

void displayFileSize(const char* file) {
    struct stat st;
    if (stat(file, &st) == 0) {
        if (S_ISREG(st.st_mode)) {
            printf("File: %s  Size: %lld bytes\n", file, (long long)st.st_size);
        }
    }
}


long long int ListFiles(const char *dirPath) {
    struct dirent *dp;

    DIR *dir = opendir(dirPath);


    if (!dir) {
        perror("Can't open the directory");
        return 0;
    }

    while ((dp = readdir(dir)) != NULL) {
        if (dp->d_type == DT_DIR) {
            if (strcmp(dp->d_name, ".") != 0 && strcmp(dp->d_name, "..") != 0) {
                char path[512];
                snprintf(path, sizeof(path), "%s/%s", dirPath, dp->d_name);
                ListFiles(path);
                }
            } else {
                char path[512];
                snprintf(path, sizeof(path), "%s/%s", dirPath, dp->d_name);
                displayFileSize(path);
            }

        }

    closedir(dir);

}
