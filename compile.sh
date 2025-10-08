#!/bin/bash

# Script to compile the Java source files.
# It assumes:
# 1. Your Java source files are in a directory named 'src'.
# 2. The MySQL connector JAR file is in the 'lib' directory.

echo "Cleaning previous build..."
rm -rf bin
mkdir bin

echo "Compiling source code..."
javac -d bin -cp "src:lib/mysql-connector-j-8.2.0.jar" $(find src -name "*.java")

echo "✅ Compilation finished. Class files are in the 'bin' directory."
