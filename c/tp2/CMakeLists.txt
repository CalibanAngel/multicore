cmake_minimum_required(VERSION 3.10)
project(tp2 C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -pthread -fopenmp")

add_executable(main main.c)
add_executable(ex1 ex1.c)
add_executable(ex2 ex2.c)
add_executable(ex4 ex4.c)
add_executable(ex5 ex5.c)
add_executable(ex8 ex8.c)