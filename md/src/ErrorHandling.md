# Error Handling

It is important to handle errors so that we can provide meaningful errors to the users. We can handle errors using the same ServiceActionResult object like below:

```java
@ServiceAction(...)
public ServiceActionResult addition(String num1, String num2) {
  try {
    int result = Integer.parseInt(num1) + Integer.parseInt(num2);
    String resultStr = String.valueOf(result);
    Map<String, Object> dataOutput = Map.of("result", resultStr);
    return ServiceActionResult.ofData(dataOutput);
  } catch(NumberFormatException e) {
    // Define an error code and a descriptive error message
    String errorCode = "INVALID_ARGS";
    String errorMsg = String.format("Invalid arguments num1 = %s, num2 = %s", num1, num2);
    
    // Return an error as a ServiceActionResult
    return ServiceActionResult.ofError(errorCode, errorMsg);
  }
}
```

If we return an error with a meaningful error code and error message it will be helpful in debugging while testing it using Converse Admin quicktests.

[] TODO: Add screenshot of debug panel once its ready
