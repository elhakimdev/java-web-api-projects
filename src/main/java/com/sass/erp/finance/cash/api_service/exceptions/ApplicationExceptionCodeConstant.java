package com.sass.erp.finance.cash.api_service.exceptions;

public class ApplicationExceptionCodeConstant {
  // General Validation Errors (0x0001 - 0x0010)
  public static final int CONSTRAINT_VIOLATION= 0x0001;
  public static final int MISSING_REQUIRED_FIELD = 0x0002;
  public static final int INVALID_FORMAT= 0x0003;
  public static final int NUMBER_OUT_OF_RANGE= 0x0004;
  public static final int STRING_TOO_LONG= 0x0005;
  public static final int STRING_TOO_SHORT= 0x0006;
  public static final int INVALID_EMAIL= 0x0007;
  public static final int INVALID_PHONE= 0x0008;
  public static final int INVALID_DATE= 0x0009;
  public static final int INVALID_BOOLEAN= 0x000A;
  public static final int INVALID_REQUEST=0x000B;

  // Authentication Validation Errors (0x0011 - 0x0020)
  public static final int AUTHENTICATION_FAILED= 0x0011;
  public static final int UNAUTHORIZED_ACCESS= 0x0012;
  public static final int INVALID_CREDENTIALS= 0x0013;
  public static final int ACCOUNT_LOCKED= 0x0014;
  public static final int ACCOUNT_DISABLED= 0x0015;
  public static final int INVALID_SESSION= 0x0016;
  public static final int TOKEN_EXPIRED= 0x0017;
  public static final int RATE_LIMIT_EXCEEDED= 0x0018;

  // Business Rule Violations (0x0021 - 0x0030)
  public static final int DUPLICATE_ENTRY= 0x0021;
  public static final int RESOURCE_NOT_FOUND= 0x0022;
  public static final int OPERATION_NOT_ALLOWED= 0x0023;
  public static final int TRANSACTION_LIMIT_EXCEEDED= 0x0024;
  public static final int INVALID_STATE= 0x0025;

  // Custom Validation Rules (0x0031 - 0x03E8 reserved)
  public static final int CUSTOM_RULE_1= 0x0031;
  public static final int CUSTOM_RULE_2= 0x0032;
  public static final int CUSTOM_RULE_3= 0x0033;
}
