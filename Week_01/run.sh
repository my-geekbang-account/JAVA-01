#!/bin/sh

java -version
javac code/HelloClassLoader.java
echo ""
java -cp code HelloClassLoader `dirname $0`/Hello.xlass
