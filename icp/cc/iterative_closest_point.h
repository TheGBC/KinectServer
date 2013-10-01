#ifndef ITERATIVE_CLOSEST_POINT_KINECT
#define ITERATIVE_CLOSEST_POINT_KINECT

#ifdef __cplusplus
extern "C" {
#endif

struct PointCloud {
  int width;
  int height;
  float* arr;
};

// Runs ICP
// @param clound_in Input point cloud, fed from mobile device
// @param cloud_out Target point cloud, fed from scanned model
// @return 4x4 float array representing transformation matrix
extern "C" float* RunICP(PointCloud& cloud_in, PointCloud& cloud_out);

#ifdef __cplusplus
}
#endif

#endif
