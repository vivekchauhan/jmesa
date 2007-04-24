#!/bin/sh
# Handle deployments for the JMesa project.

VERSION=$1
BUILD_DIR=build

# prompt the user to enter in a build number
if [ "$VERSION" = "" ]; then
    echo -n "Enter a build number:"
    read VERSION
fi

# make the distribution directory and clean out last build
if [ -e $BUILD_DIR ]; then
    rm -rf $BUILD_DIR
fi

# create the directory structure
mkdir $BUILD_DIR
mkdir $BUILD_DIR/jmesa-$VERSION
mkdir $BUILD_DIR/jmesa-$VERSION/dist
mkdir $BUILD_DIR/jmesa-$VERSION/images
mkdir $BUILD_DIR/jmesa-$VERSION/source

echo "Build the project with ANT ..."
export JAVA_HOME=/home/jeff/jdk1.5.0_11
export PATH=$JAVA_HOME/bin:$PATH
ant jar -Dversion=$VERSION
ant javadocs

echo "Copy files and zip up distribution ..."
cp -r src/org $BUILD_DIR/jmesa-$VERSION/source
cp src/LICENSE-2.0.txt $BUILD_DIR/jmesa-$VERSION/source
cp src/NOTICE.txt $BUILD_DIR/jmesa-$VERSION/source
cp $BUILD_DIR/jmesa-$VERSION.jar $BUILD_DIR/jmesa-$VERSION/dist
cp resources/jmesa.css $BUILD_DIR/jmesa-$VERSION/dist
cp resources/jmesa.js $BUILD_DIR/jmesa-$VERSION/dist
cp -r resources/images/* $BUILD_DIR/jmesa-$VERSION/images

echo "Remove CVS folders ..."
find $BUILD_DIR/jmesa-$VERSION/source/ -name .svn | xargs rm -rf 
find $BUILD_DIR/jmesa-$VERSION/images/ -name .svn | xargs rm -rf 

echo "Zip up distribution ..."
cd $BUILD_DIR
zip -rq jmesa-$VERSION.zip jmesa-$VERSION
cd ..

echo "Done building jmesa-$VERSION.zip in $BUILD_DIR directory."
