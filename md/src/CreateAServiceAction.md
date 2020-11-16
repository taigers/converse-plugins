# Create a Service Action

Create a new Java source file called `CalculatorService.java` under the package `com.taiger.middleware.adaptor`. You may use any naming conventions for the class name or subpackages. But the class should be located within the `com.taiger.middleware.adaptor` base package.  Lets first build a service action that is able to do simple math.

```java
public ServiceActionResult addition(String num1, String num2) {
  int result = Integer.parseInt(num1) + Integer.parseInt(num2);
  String resultStr = String.valueOf(result);

  Map<String, Object> dataOutput = Map.of("result", resultStr);
  return ServiceActionResult.ofData(dataOutput);
}
```

Every service should return an object of type `ServiceActionResult`. Service actions can return outputs which is pure data, in this case it just adds the two numbers and returns the result. To create a `ServiceActionResult`, we can use some of the provided static constructors like `ServiceActionResult.ofData()` which takes a `Map<String, Object>` as its parameter.

> You may have noticed that the inputs and the outputs are of `String` types. This is intentional and necessary for the plugin to work.

Once we have the business logic ready, we have to annotate it. This is so that the Converse Connector can scan and register service actions when we upload the plugin. Any service action that we intend to be used from the Converse platform should be annotated like below.

```java
@ServiceAction(
    displayName = "Addition",        // A name to display in the ui
    id = "calculator::add",          // A unique string that can identify this action
    inputs = {
      @Input(name = "num1", description = "First number to add"),
      @Input(name = "num2", description = "Second number to add")
    },
    dataOutputs = @DataOutput(name = "result", description = "Sum of num1 and num2"))
public ServiceActionResult addition(String num1, String num2) {

  // Convert string inputs to integer for calculation
  int result = Integer.parseInt(num1) + Integer.parseInt(num2);
  
  // Convert integer back to string to send to converse
  String resultStr = String.valueOf(result);
  
  // Define a map which will hold the result
  // You can add additional key-value pairs as necessary
  Map<String, Object> dataOutput = Map.of("result", resultStr);
  
  // Return a data output with the map
  return ServiceActionResult.ofData(dataOutput);
}
```

> You are free to choose any package structure for your supporting classes. But any Service Action that should be scanned and recognized by the middleware should be located within the `com.taiger.middleware.adaptor` base package.

Using the provided annotations, we are providing metadata to the converse connector engine about this service action so that it can be rendered and used in the Converse platform while configuring a service action. 

Output names in the annotation should match the corresponding keys in the `Map` object we return.

We now have completely built a service action that adds two numbers. Lets build and upload it.
