# CMake for FS_AddressBus tests

set(TARGET_NAME "multiply_test")


set(PRODUCTIVE_SOURCES 
    ${PROJECT_SOURCE_DIR}/src/multiply/multiply.c
)

set(TEST_SOURCES
    multiply_test.cpp
    ${PROJECT_SOURCE_DIR}/tests/mock/mock.cpp
)

set(INCLUDE_PATHS
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${PROJECT_SOURCE_DIR}/tests/mock
    ${PROJECT_SOURCE_DIR}/src/include
 )   
  
set(COMPILE_DEFINITIONS
  MODULE_TEST
  REMOVE_STATIC
)

include(${PROJECT_SOURCE_DIR}/cmake/gtest_common.cmake)
