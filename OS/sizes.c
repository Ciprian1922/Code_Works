#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dirent.h>
#include <sys/stat.h>

// Function that computes the size of a file
long long calculateFileSize(const char *filename) {
    struct stat st;
    if (stat(filename, &st) == 0) {
        return (long long)st.st_size;
    }
    return -1;
}

int main() {
    char *currentPath = "."; // Current directory path

    printf("Sizes of the files in this directory:\n");

    struct dirent *dp;
    DIR *dir = opendir(currentPath);

    if (!dir) {
        perror("opendir");
        return 1;
    }

    while ((dp = readdir(dir)) != NULL) {
        if (strcmp(dp->d_name, ".") != 0 && strcmp(dp->d_name, "..") != 0) {
            char path[1000];
            snprintf(path, sizeof(path), "%s/%s", currentPath, dp->d_name);
            long long fileSize = calculateFileSize(path);
            if (fileSize >= 0) {
                printf("%s - Size: %lld bytes\n", path, fileSize);
            }
        }
    }

    closedir(dir);

    return 0;
}
