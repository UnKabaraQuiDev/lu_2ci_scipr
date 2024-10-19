#!/bin/bash

# Loop through all directories in the current directory
for dir in */; do
    # Remove the trailing slash from directory name
    dir_name="${dir%/}"
    
    # Replace spaces with underscores
    new_dir_name="${dir_name// /_}"
    
    # Rename the directory if the names are different
    if [[ "$dir_name" != "$new_dir_name" ]]; then
        mv "$dir_name" "$new_dir_name"
        echo "Renamed '$dir_name' to '$new_dir_name'"
    fi
done
