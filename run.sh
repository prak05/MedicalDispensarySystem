#!/bin/bash

# Script to run the compiled Medical Dispensary System application.
# It assumes:
# 1. You have already run compile.sh and the 'bin' directory exists.
# 2. The MySQL connector JAR file is in the 'lib' directory.

echo "Running the application..."
java -cp "bin:lib/mysql-connector-j-8.2.0.jar" com.mds.MainApp
