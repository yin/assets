#!/bin/bash

if [ ! command -v convert ]; then
   echo "sudo apt install -y imagemagick"
   exit 1
fi

if [ ! command -v blender ]; then
    echo "sudo apt install -y blender"
    exit 1
fi

set -x

wget https://www.eso.org/public/archives/images/original/eso0932a.tif

unzip blender.zip
convert eso0932a.tif blender/environment.jpg
rm -f eso0932a.tif

blender -b blender/environment.blend -a
rm -f blender/environment.jpg

mkdir -p output/
mv blender/0001.png output/pos-z.png
mv blender/0002.png output/neg-x.png
mv blender/0003.png output/neg-z.png
mv blender/0004.png output/pos-x.png
mv blender/0005.png output/neg-y.png
mv blender/0006.png output/pos-y.png

rm -rf blender/
