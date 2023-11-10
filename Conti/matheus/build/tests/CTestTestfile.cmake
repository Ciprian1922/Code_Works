# CMake generated Testfile for 
# Source directory: C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests
# Build directory: C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/build/tests
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
if(CTEST_CONFIGURATION_TYPE MATCHES "^([Dd][Ee][Bb][Uu][Gg])$")
  add_test(multiply_gtests "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/build/tests/Debug/multiply_test.exe")
  set_tests_properties(multiply_gtests PROPERTIES  _BACKTRACE_TRIPLES "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;58;add_test;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;0;;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;26;include;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;0;")
elseif(CTEST_CONFIGURATION_TYPE MATCHES "^([Rr][Ee][Ll][Ee][Aa][Ss][Ee])$")
  add_test(multiply_gtests "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/build/tests/Release/multiply_test.exe")
  set_tests_properties(multiply_gtests PROPERTIES  _BACKTRACE_TRIPLES "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;58;add_test;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;0;;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;26;include;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;0;")
elseif(CTEST_CONFIGURATION_TYPE MATCHES "^([Mm][Ii][Nn][Ss][Ii][Zz][Ee][Rr][Ee][Ll])$")
  add_test(multiply_gtests "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/build/tests/MinSizeRel/multiply_test.exe")
  set_tests_properties(multiply_gtests PROPERTIES  _BACKTRACE_TRIPLES "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;58;add_test;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;0;;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;26;include;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;0;")
elseif(CTEST_CONFIGURATION_TYPE MATCHES "^([Rr][Ee][Ll][Ww][Ii][Tt][Hh][Dd][Ee][Bb][Ii][Nn][Ff][Oo])$")
  add_test(multiply_gtests "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/build/tests/RelWithDebInfo/multiply_test.exe")
  set_tests_properties(multiply_gtests PROPERTIES  _BACKTRACE_TRIPLES "C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;58;add_test;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/cmake/gtest_common.cmake;0;;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;26;include;C:/Users/popa_/Desktop/GitHub_UVT/Code_Works/Conti/matheus/tests/CMakeLists.txt;0;")
else()
  add_test(multiply_gtests NOT_AVAILABLE)
endif()
subdirs("../_deps/googletest-build")
