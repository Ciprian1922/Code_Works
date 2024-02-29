#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>

long long int ListFiles(const char *dirPath);
void displayFileDetails(const char *filePath);

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <directory>\n", argv[0]);
        return 1;
    }

    const char *dirPath = argv[1];

    struct stat st;
    if (stat(dirPath, &st) || !S_ISDIR(st.st_mode)) {
        fprintf(stderr, "Invalid folder path.\n");
        return 1;
    }

    long long int totalSize = ListFiles(dirPath);

    printf("Total size of regular files in directory '%s': %lld bytes\n", dirPath, totalSize);

    return 0;
}

void displayFileDetails(const char *filePath) {
    struct stat st;
    if (stat(filePath, &st) == 0) {
        if (S_ISREG(st.st_mode)) {
            printf("File Name: %s\n", filePath);
            printf("File Type: Regular File\n");
        } else if (S_ISDIR(st.st_mode)) {
            printf("File Name: %s\n", filePath);
            printf("File Type: Directory\n");
        } else {
            printf("File Name: %s\n", filePath);
            printf("File Type: Unknown\n");
        }

        // Print file permissions
        printf("File Permissions: ");
        printf((st.st_mode & S_IRUSR) ? "r" : "-");
        printf((st.st_mode & S_IWUSR) ? "w" : "-");
        printf((st.st_mode & S_IXUSR) ? "x" : "-");
        printf((st.st_mode & S_IRGRP) ? "r" : "-");
        printf((st.st_mode & S_IWGRP) ? "w" : "-");
        printf((st.st_mode & S_IXGRP) ? "x" : "-");
        printf((st.st_mode & S_IROTH) ? "r" : "-");
        printf((st.st_mode & S_IWOTH) ? "w" : "-");
        printf((st.st_mode & S_IXOTH) ? "x" : "-");
        printf("\n");
    }
}

long long int ListFiles(const char *dirPath) {
    struct dirent *dp;
    long long int totalSize = 0;

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
                totalSize += ListFiles(path);
            }
        } else {
            char path[512];
            snprintf(path, sizeof(path), "%s/%s", dirPath, dp->d_name);
            displayFileDetails(path);
        }
    }

    closedir(dir);

    return totalSize;
}
