cmake_minimum_required(VERSION 3.9)
project(teavm_rhino_shell C)

set(CMAKE_C_STANDARD 11)
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/target/)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

if (GCC)
    set(CMAKE_C_FLAGS_DEBUG "${CMAKE_C_FLAGS_DEBUG} -g")
    set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -O3")
endif()
if (MSVC)
    add_compile_options(/MP /W3)
    set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} /GL /O2")
    set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} /SUBSYSTEM:CONSOLE")
    set(CMAKE_EXE_LINKER_FRAGS_RELEASE "${CMAKE_EXE_LINKER_FLAGS_RELEASE} /LTCG")
endif()

set(TEAVM_SOURCE_DIR ${PROJECT_SOURCE_DIR}/target/generated/c)
file(STRINGS ${TEAVM_SOURCE_DIR}/all.txt TEAVM_SOURCE_LIST)
list(TRANSFORM TEAVM_SOURCE_LIST PREPEND ${TEAVM_SOURCE_DIR}/)
add_executable(teavm_rhino_shell ${TEAVM_SOURCE_LIST})

if (NOT WIN32)
    target_link_libraries(teavm_rhino_shell m rt)
endif()