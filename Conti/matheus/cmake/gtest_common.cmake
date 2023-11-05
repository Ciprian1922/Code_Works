# cmake for the common part of gtest build configuration
include(FetchContent)

FetchContent_Declare(
  googletest
  GIT_REPOSITORY https://github.com/google/googletest.git
  GIT_TAG        release-1.11.0
)
FetchContent_MakeAvailable(googletest)

add_library(GTest::GTest INTERFACE IMPORTED)
target_link_libraries(GTest::GTest INTERFACE gtest_main)

#add_executable(multiply_test multiply_test.cpp)


add_executable(${TARGET_NAME})

set(CTC_NO_INCLUDE
  ${TEST_SOURCES}
)

target_sources(${TARGET_NAME}
  PUBLIC
    ${PRODUCTIVE_SOURCES}
    ${TEST_SOURCES}
)

target_include_directories(${TARGET_NAME}
  PUBLIC
    ${INCLUDE_PATHS}
)

# link our test binary to Googletest library
#find_package(GTest REQUIRED)
target_link_libraries(${TARGET_NAME}
  PRIVATE
    gtest
    gtest_main
    gmock
    gmock_main
    ${LINK_LIBRARIES}
)

target_compile_definitions(${TARGET_NAME}
  PRIVATE 
    MODULE_TEST
    __IPL_CANTPP__
)

target_link_libraries(
  ${TARGET_NAME}
  PRIVATE
    GTest::GTest
	)


add_test(
  NAME multiply_gtests
  COMMAND ${TARGET_NAME}
)


