echo Building C++ library
mkdir -p bin
cd cc
cmake ./
make

echo Copying C++ library into bin
cp libiterative_closest_point.so ../bin
cp iterative_closest_point.h ../bin

echo Building Java library and copying into bin
cd ../jni
javac -d ../bin *.java
cd native

echo Building Jni library
./compile.sh

echo Copying Jni library into bin
cp libicp.so ../../bin
cd ../../bin
