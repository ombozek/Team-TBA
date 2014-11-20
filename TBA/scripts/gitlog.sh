#!/bin/sh

git --git-dir $1 log --name-status | grep ^[ADM] | grep .java
