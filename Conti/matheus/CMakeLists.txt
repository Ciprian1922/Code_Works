cmake_minimum_required(VERSION 3.16)
project(MultiplyTest LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_CXX_COMPILER "C:/Stuff/cmake-3.28.0-rc3-windows-x86_64/bin")

enable_testing()

add_subdirectory(src)
add_subdirectory(tests)



