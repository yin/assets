#!/bin/bash

if [ ! command -v http-server ]; then
    echo ""sudo npm install -g http-server""
fi

pushd output/
http-server
popd
