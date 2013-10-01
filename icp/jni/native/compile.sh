g++ -o libicp.so -shared -fPIC \
  -I /usr/lib/jvm/java-1.7.0-openjdk-amd64/include \
  -I /usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux/ \
  -I /home/ubuntu/icp/bin \
  edu_gbc_jni_IcpHandler.cc \
  -L /home/ubuntu/icp/bin -literative_closest_point
