#!/bin/sh

cd $1
rm -r .git/*
rmdir .git
rm .gitignore
rm -r ./*
cd ..
rmdir $1
