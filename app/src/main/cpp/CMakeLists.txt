cmake_minimum_required(VERSION 3.10)

if (NOT ANDROID_NDK_TOOLCHAIN_INCLUDED)
    message(FATAL_ERROR "-- Toolchain file not included, see https://developer.android.com/ndk/guides/cmake")
endif ()

add_library(
    example-cpp-custom-layer MODULE
    ${PROJECT_SOURCE_DIR}/example_custom_layer.cpp
)

target_include_directories(
    example-cpp-custom-layer
    PRIVATE
        ${PROJECT_SOURCE_DIR}
)

# Use the same compile_options as in mapbox-gl
target_compile_options(
    example-cpp-custom-layer
    PRIVATE
        $<$<CONFIG:Release>:-Oz>
        $<$<CONFIG:Release>:-Qunused-arguments>
        $<$<CONFIG:Release>:-flto>
        $<$<CONFIG:Release>:-fvisibility=hidden>
        $<$<CONFIG:Release>:-fvisibility-inlines-hidden>)

# Use the same linker options as in mapbox-gl
target_link_libraries(
    example-cpp-custom-layer
    PRIVATE
        GLESv2
        log
        $<$<CONFIG:Release>:-Wl,--gc-sections>
        $<$<CONFIG:Release>:-Wl,--icf=all>
        $<$<CONFIG:Release>:-flto>
        $<$<CONFIG:Release>:-fuse-ld=lld>
        $<$<CONFIG:Release>:-Wl,--lto-new-pass-manager>
        $<$<CONFIG:Release>:-Wl,--pack-dyn-relocs=android>
        $<$<CONFIG:Release>:-Wl,--lto-O3>
)