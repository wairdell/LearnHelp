cmake_minimum_required(VERSION 3.4.1)

add_library(sabridge
        SHARED
        src/main/jni/SaForamter.cpp
        src/main/jni/md5.cpp
        )

find_library(log-lib
        log
        )

target_link_libraries(sabridge
        ${log-lib}
        )