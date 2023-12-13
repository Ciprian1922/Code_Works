cmake_minimum_required(VERSION 3.27)
project(Lab11 C)

set(CMAKE_C_STANDARD 11)

add_executable(Lab11 main.c
        threads.c
        thread1.c
        Lab11.c
        Lab11-ABCDE.c)
