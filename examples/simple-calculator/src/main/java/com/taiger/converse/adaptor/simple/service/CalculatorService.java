package com.taiger.converse.adaptor.simple.service;

import com.taiger.converse.engine.common.annotations.DataOutput;
import com.taiger.converse.engine.common.annotations.Input;
import com.taiger.converse.engine.common.annotations.ServiceAction;
import com.taiger.converse.engine.common.dto.ServiceActionResult;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalculatorService {
  private static Logger log = LoggerFactory.getLogger(CalculatorService.class);

@ServiceAction(
    displayName = "Addition",
    id = "calculator::add",
    inputs = {
      @Input(name = "num1", description = "First number to add"),
      @Input(name = "num2", description = "Second number to add")
    },
    dataOutputs = @DataOutput(name = "result", description = "Sum of num1 and num2"))
  public ServiceActionResult add(String num1, String num2) {
    try {
      int sum = Integer.parseInt(num1) + Integer.parseInt(num2);
      return ServiceActionResult.ofData(Map.of("result", sum));
    } catch (NumberFormatException e) {
      log.error("Invalid numbers provided {}, {}", num1, num2, e);
      return ServiceActionResult.ofError("INVALID_ARGS", "Invalid arguments");
    }
  }

  @ServiceAction(
      displayName = "Multiplication",
      id = "calculator::multiply",
      inputs = {
        @Input(name = "num1", description = "First number to multiply"),
        @Input(name = "num2", description = "Second number to multiply")
      },
      dataOutputs = @DataOutput(name = "result", description = "Product of num1 and num2"))
  public ServiceActionResult multiply(String num1, String num2) {
    try {
      int product = Integer.parseInt(num1) * Integer.parseInt(num2);
      return ServiceActionResult.ofData(Map.of("result", product));
    } catch (NumberFormatException e) {
      log.error("Invalid numbers provided");
      return ServiceActionResult.ofError("INVALID_ARGS", "Invalid arguments");
    }
  }
}
