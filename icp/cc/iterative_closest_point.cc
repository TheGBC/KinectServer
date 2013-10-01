#include "iterative_closest_point.h"

#include <iostream>
#include <pcl/io/pcd_io.h>
#include <pcl/point_types.h>
#include <pcl/registration/icp.h>
#include <Eigen/Dense>


extern "C" void SetPointCloud(pcl::PointCloud<pcl::PointXYZ>::Ptr& ptr, PointCloud& cloud) {
  ptr->width = cloud.width;
  ptr->height = cloud.height;
  ptr->is_dense = false;
  ptr->points.resize(cloud.width * cloud.height);
  for (int i = 0; i < cloud.width * cloud.height; i++) {
    ptr->points[i].x = cloud.arr[i * 3]; 
    ptr->points[i].y = cloud.arr[i * 3 + 1];
    ptr->points[i].z = cloud.arr[i * 3 + 2];
  }
}

// Runs ICP
// @param clound_in Input point cloud, fed from mobile device
// @param cloud_out Target point cloud, fed from scanned model
// @return 4x4 float array representing transformation matrix in column major format
extern "C" float* RunICP(PointCloud& cloud_in, PointCloud& cloud_out) {
  pcl::PointCloud<pcl::PointXYZ>::Ptr in_ptr (new pcl::PointCloud<pcl::PointXYZ>);
  pcl::PointCloud<pcl::PointXYZ>::Ptr out_ptr (new pcl::PointCloud<pcl::PointXYZ>);
  
  SetPointCloud(in_ptr, cloud_in);
  SetPointCloud(out_ptr, cloud_out);

  pcl::IterativeClosestPoint<pcl::PointXYZ, pcl::PointXYZ>* icp =
      new pcl::IterativeClosestPoint<pcl::PointXYZ, pcl::PointXYZ>();
  icp->setInputSource(in_ptr);
  icp->setInputTarget(out_ptr);
  pcl::PointCloud<pcl::PointXYZ> Final;
  icp->align(Final);
  Eigen::Matrix4f mat = icp->getFinalTransformation();
  float* matrixValues = new float[18];
  matrixValues[0] = (float) icp->getFitnessScore();
  matrixValues[1] = icp->hasConverged() ? 1 : 0;
  for (int i = 0; i < 16; i ++) {
    matrixValues[i + 2] = *(mat.data() + i);
  }
  delete icp;
  return matrixValues;
}
