package edu.gbc.jni;

public class IcpHandler {
  private IcpHandler() { }

  /**
   * Runs iterataive closest point on dataset.
   * @param inWidth The width of the input point cloud.
   * @param inHeight The height of the input point cloud.
   * @param inData The input point cloud data. Size should be
   *     3 * w * h, {x,y,z} values for the point cloud.
   * @param targetWidth The width of the target point cloud.
   * @param targetHeight The height of the target point cloud.
   * @param targetData the target ponti cloud data. Size should be
   *     3 * w * h, {x,y,z} values for the point cloud.
   * @return Matrix transformation and results of icp.
   */
  public static Matrix iterativeClosestPoint(
      int inWidth, int inHeight, float[] inData,
      int targetWidth, int targetHeight, float[] targetData) {
    return new Matrix(nativeIterativeClosestPoint(
      inWidth, inHeight, inData,
      targetWidth, targetHeight, targetData
    ));
  }

  private static native float[] nativeIterativeClosestPoint(
      int inWidth, int inHeight, float[] in,
      int targetWidth, int targetheight, float[] target);
  static { System.loadLibrary("icp"); }
}
