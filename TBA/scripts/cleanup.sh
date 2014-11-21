#!/bin/sh

cd $1
rm -r .git/*
rmdir .git
rm .gitignore
rm .*
rm -r ./*
cd ..
rmdir $1
