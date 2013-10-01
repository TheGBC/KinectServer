#include "edu_gbc_jni_IcpHandler.h"
#include "iterative_closest_point.h"

/*
 * Class:     edu_gbc_jni_IcpHandler
 * Method:    nativeIterativeClosestPoint
 * Signature: (II[FII[F)[F
 */
JNIEXPORT jfloatArray JNICALL Java_edu_gbc_jni_IcpHandler_nativeIterativeClosestPoint
    (JNIEnv * env, jclass obj,
        jint in_width, jint in_height, jfloatArray in_data,
        jint target_width, jint target_height, jfloatArray target_data) {
  jfloatArray out;
  out = env->NewFloatArray(18);

  PointCloud in;
  in.width = in_width;
  in.height = in_height;
  in.arr = env->GetFloatArrayElements(in_data, 0);

  PointCloud target;
  target.width = target_width;
  target.height = target_height;
  target.arr = env->GetFloatArrayElements(target_data, 0);

  float* transformation = RunICP(in, target);
  delete[] in.arr;
  delete[] target.arr;
  
  env->SetFloatArrayRegion(out, 0, 18, transformation);
  delete transformation;
  return out;
}
