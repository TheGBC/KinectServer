package edu.gbc.jni;

public class Matrix {
  private static final int WIDTH = 4;
  private static final int HEIGHT = 4;

  private float[] data;
  private float score;
  private boolean converged;

  Matrix(float[] data) {
    this.data = new float[16];
    this.score = data[0];
    this.converged = data[1] == 1;
    for (int i = 0; i < 16; i++) {
      this.data[i] = data[i + 2];
    }
  }

  public float get(int row, int col) {
    return data[col * 4 + row];
  }

  public float score() {
    return score;
  }

  public boolean converged() {
    return converged;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int y = 0; y < HEIGHT; y++) {
      builder.append("[");
      for (int x = 0; x < WIDTH; x++) {
        builder.append(" " + get(y, x));
      }
      builder.append("]\n");
    }
    return builder.toString();
  }

  /**
   * While toString returns a printable string, serializeForTransport returns
   * a string that can easily be parsed.
   * @return String suitable for sending back as a response
   */
  public String serializeForTransport() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < data.length; i++) {
      builder.append(data[i]);
      if (i < data.length - 1) {
        builder.append(',');
      }
    }
    return builder.toString();
  }
}
