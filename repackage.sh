#!/usr/bin/env bash

if [ ! "$#" -eq 1 ]; then
    echo "usage: $0 <target_dir>"
    echo "Replaces all occurrences of org.objectweb.asm with sumguytho.asm.mod in specified directory."
    exit 1
fi

target_dir=$1
pkg_original="org.objectweb.asm"
pkg_target="sumguytho.asm.mod"

pushd $(pwd)
cd $target_dir
grep -rnil "$pkg_original" | xargs -n1 -I{} sed -i "s/$pkg_original/$pkg_target/g" {}
popd
