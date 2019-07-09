mkdir build
cmake ^
  -DCMAKE_BUILD_TYPE=Debug ^
  -A x64 ^
  -S . -B build

mkdir build-ninja
cmake ^
  -DCMAKE_BUILD_TYPE=Debug ^
  -GNinja ^
  -S . -B build-ninja