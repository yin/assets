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
if [ $1 == "small" ]; then 
    convert blender/0001.png -size 320 output/pos-z.jpg
    convert blender/0002.png -size 320 output/neg-x.jpg
    convert blender/0003.png -size 320 output/neg-z.jpg
    convert blender/0004.png -size 320 output/pos-x.jpg
    convert blender/0005.png -size 320 output/neg-y.jpg
    convert blender/0006.png -size 320 output/pos-y.jpg
else
    mv blender/0001.png output/pos-z.png
    mv blender/0002.png output/neg-x.png
    mv blender/0003.png output/neg-z.png
    mv blender/0004.png output/pos-x.png
    mv blender/0005.png output/neg-y.png
    mv blender/0006.png output/pos-y.png
fi

rm -rf blender/
