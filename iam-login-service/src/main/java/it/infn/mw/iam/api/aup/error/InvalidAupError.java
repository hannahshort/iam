package it.infn.mw.iam.api.aup.error;

public class InvalidAupError extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public InvalidAupError(String message) {
    super(message);
  }

}