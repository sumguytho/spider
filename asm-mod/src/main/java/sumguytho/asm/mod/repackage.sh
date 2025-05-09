#!/usr/bin/env bash

# A simple script that subsitutes all occurrences of a text piece
# in every file of current directory with another text piece.

pkg_original="sumguytho.asm.mod"
pkg_target="sumguytho.asm.mod"
grep -rnil "$pkg_original" | xargs -n1 -I{} sed -i "s/$pkg_original/$pkg_target/g" {}
