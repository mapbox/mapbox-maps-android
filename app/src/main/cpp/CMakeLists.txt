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

# Use the same compile_options as in mapbox-gl, plus a local VLA guard
target_compile_options(
    example-cpp-custom-layer
    PRIVATE
        # Variable-length arrays are a non-standard extension sized from a driver-supplied value.
        -Werror=vla
        $<$<NOT:$<CONFIG:Debug>>:-Oz>
        $<$<NOT:$<CONFIG:Debug>>:-Qunused-arguments>
        $<$<NOT:$<CONFIG:Debug>>:-flto>
        $<$<NOT:$<CONFIG:Debug>>:-fvisibility=hidden>
        $<$<NOT:$<CONFIG:Debug>>:-fvisibility-inlines-hidden>)

# Use the same linker options as in mapbox-gl
target_link_libraries(
    example-cpp-custom-layer
    PRIVATE
        GLESv2
        log
        $<$<NOT:$<CONFIG:Debug>>:-Wl,--gc-sections>
        $<$<NOT:$<CONFIG:Debug>>:-Wl,--icf=all>
        $<$<NOT:$<CONFIG:Debug>>:-flto>
        $<$<NOT:$<CONFIG:Debug>>:-fuse-ld=lld>
        $<$<NOT:$<CONFIG:Debug>>:-Wl,--pack-dyn-relocs=android>
        $<$<NOT:$<CONFIG:Debug>>:-Wl,--lto-O3>
)