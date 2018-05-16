cmake_minimum_required(VERSION 3.10)
project(proj2 C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fopenmp -lpthread")

add_executable(prob1 prob1/prob1.c)
add_executable(prob2 prob2/prob2.c)