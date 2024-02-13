package com.Atipera.MichalLisekTestTask.exception;

public class ExceptionHandlerRepository extends Exception{
    public ExceptionHandlerRepository(String message) {
        super(message);
    }
    public static ExceptionHandlerRepository handleError(int responseCode, String message){
        if(responseCode != 404){
            return new ExceptionHandlerRepository("Unexpected error with status code: " + responseCode);
        } else {
            return new ExceptionHandlerRepository(
                    "\n" +
                    "{\n" +
                    "\t\"status\": " + responseCode +
                    "\n" +
                    "\t\"message\": " + message +
                    "\n" +
                    "}");
        }
    }
}
