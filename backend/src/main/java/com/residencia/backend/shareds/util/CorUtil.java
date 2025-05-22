package com.residencia.backend.shareds.util;

import java.util.Random;

public class CorUtil {
  public static String corAletoria(){
    Random rand = new Random();

    int r = rand.nextInt(256);
    int g = rand.nextInt(256);
    int b = rand.nextInt(256);

    return String.format("%02X%02X%02X", r, g, b);
  }
}
