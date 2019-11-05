/**
* @author  Jaya Prakash Manne
* @version 1.0
* @since   01-Nov-2019 
*/
package com.abs.oec.util;

public class AuthCodeGenerator {

  private static AuthCodeGenerator instance = null;

  // ========================================================================

  private AuthCodeGenerator() {
  }

  // ========================================================================

  public static synchronized AuthCodeGenerator getInstance() {

    if (instance == null) {
      instance = new AuthCodeGenerator();
    }
    return instance;
  }

  // ========================================================================

  public String getGeneratedAuthCode() {
    String dCase = "abcdefghijklmnopqrstuvwxyz";
    String uCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String intChar = "0123456789";
    char[] authCode = new char[12];
    char c = 'A';
    for (int i = 0; i < 12; i++) {
      switch (i) {
      case 0:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 1:
        c = uCase.charAt((int) (Math.random() * 26));
        break;
      case 2:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 3:
        c = intChar.charAt((int) (Math.random() * 10));
        break;
      case 4:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 5:
        c = uCase.charAt((int) (Math.random() * 26));
        break;
      case 6:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 7:
        c = intChar.charAt((int) (Math.random() * 10));
        break;
      case 8:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 9:
        c = uCase.charAt((int) (Math.random() * 26));
        break;
      case 10:
        c = dCase.charAt((int) (Math.random() * 26));
        break;
      case 11:
        c = intChar.charAt((int) (Math.random() * 10));
        break;
      }
      authCode[i] = (char) c;
    }
    StringBuffer authCodeBuffer = new StringBuffer(new String(authCode));
    authCodeBuffer.append(System.currentTimeMillis());
    return authCodeBuffer.toString();
  }

  // ========================================================================
}