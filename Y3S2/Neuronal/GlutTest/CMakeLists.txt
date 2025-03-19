cmake_minimum_required(VERSION 3.30)
project(glut)

set(CMAKE_CXX_STANDARD 20)

add_executable(glut main.cpp)
target_link_libraries(glut opengl32 glu32 freeglut)
