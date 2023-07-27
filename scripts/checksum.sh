#!/usr/bin/env bash
source scripts/checksum-base.sh

add_files . build.gradle.kts
add_files ./gradle/libs.versions.toml
add_files ./gradle/wrapper gradle-wrapper.properties

generate_checksums
