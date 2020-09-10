package com.taiger.converse.adaptor.demo.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiger.converse.engine.common.annotations.DataOutput;
import com.taiger.converse.engine.common.annotations.DisplayOutput;
import com.taiger.converse.engine.common.annotations.Input;
import com.taiger.converse.engine.common.annotations.ServiceAction;
import com.taiger.converse.engine.common.component.MessageComponent;
import com.taiger.converse.engine.common.component.PayloadType;
import com.taiger.converse.engine.common.component.payload.ButtonListPayload;
import com.taiger.converse.engine.common.component.payload.CarouselPayload;
import com.taiger.converse.engine.common.component.ui.Button;
import com.taiger.converse.engine.common.component.ui.Button.WebViewHeight;
import com.taiger.converse.engine.common.component.ui.Card;
import com.taiger.converse.engine.common.dto.ServiceActionResult;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {
  private final ObjectMapper mapper = new ObjectMapper();

  @ServiceAction(
      displayName = "Get Flights by City (error test case)",
      id = "booking::flightsbycityerror",
      inputs = {
        @Input(name = "location", description = "Location to search flights to"),
        @Input(name = "arrivalDate", description = "Date of arrival at destination"),
        @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "numHits", description = "Number of flights found"),
      displayOutputs =
          @DisplayOutput(
              name = "flightsButtonList",
              description = "Available flights in a button list",
              payloadType = PayloadType.BUTTON_LIST))
  public ServiceActionResult getFlightsError(
      String location, String arrivalDate, String returnDate) {
    return ServiceActionResult.ofError("ERR_INVALID_LOCATION", "Unrecognized location");
  }

  @ServiceAction(
      displayName = "Get Flights by City (timeout test case)",
      id = "booking::flightsbycitytimeout",
      inputs = {
        @Input(name = "location", description = "Location to search flights to"),
        @Input(name = "arrivalDate", description = "Date of arrival at destination"),
        @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "numHits", description = "Number of flights found"),
      displayOutputs =
          @DisplayOutput(
              name = "flightsButtonList",
              description = "Available flights in a button list",
              payloadType = PayloadType.BUTTON_LIST))
  public ServiceActionResult getFlightsTimeout(
      String location, String arrivalDate, String returnDate) {
    try {
        Thread.sleep(10000);
    } catch(Exception e) {
    }
    return ServiceActionResult.ofError("ERR_TIMEOUT", "Request timed out");
  }

  @ServiceAction(
      displayName = "Get Flights by City",
      id = "booking::flightsbycity",
      inputs = {
        @Input(name = "location", description = "Location to search flights to"),
        @Input(name = "arrivalDate", description = "Date of arrival at destination"),
        @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "numHits", description = "Number of flights found"),
      displayOutputs =
          @DisplayOutput(
              name = "flightsButtonList",
              description = "Available flights in a button list",
              payloadType = PayloadType.BUTTON_LIST))
  public ServiceActionResult getFlightsByCity(
      String location, String arrivalDate, String returnDate) {
    List<Map<String, String>> result =
        List.of(
            Map.of("airlines", "Emirates", "flightWindow", "7:25am - 2:20 pm"),
            Map.of("airlines", "British Airways", "flightWindow", "12:20am - 6:20pm"),
            Map.of("airlines", "Singapore Airlines", "flightWindow", "1:00pm - 7:00pm"));

    List<Button> buttons =
        result.stream()
            .map(
                flight ->
                    Button.postbackButton(
                        flight.get("airlines") + " :: " + flight.get("flightWindow"),
                        toJson(flight)))
            .collect(Collectors.toList());
    ButtonListPayload payload =
        new ButtonListPayload("Please select one of the following departure flights:", buttons);
    MessageComponent messageComponent =
        MessageComponent.uiPayloadMessage(payload, PayloadType.BUTTON_LIST);

    return ServiceActionResult.of(
        Map.of("numHits", String.valueOf(result.size())),
        Map.of("flightsButtonList", messageComponent));
  }

  @ServiceAction(
      displayName = "Get Hotels by City",
      id = "booking::hotelsbycity",
      inputs = {
        @Input(name = "location", description = "Location to search hotels"),
        @Input(name = "arrivalDate", description = "Date of arrival at destination"),
        @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "numHits", description = "Number of hotels found"),
      displayOutputs = {
        @DisplayOutput(
            name = "hotelsButtonList",
            description = "Available hotels in a button list",
            payloadType = PayloadType.BUTTON_LIST),
        @DisplayOutput(
            name = "hotelsCarousel",
            description = "Available hotels in a carousel",
            payloadType = PayloadType.CAROUSEL)
      })
  public ServiceActionResult getHotelsByCity(
      String location, String arrivalDate, String returnDate) {
    List<Map<String, String>> result =
        List.of(
            Map.of("name", "The Harbourview", "price", "500/night", "imageUrl", "https://www.swissotel.com/assets/0/92/3686/3768/3770/6442451433/ae87da19-9f23-450a-8927-6f4c700aa104.jpg"),
            Map.of("name", "Marriot International", "price", "499/night", "imageUrl", "https://cache.marriott.com/marriottassets/marriott/BOMSA/bomsa-exterior-0023-hor-feat.jpg"),
            Map.of("name", "Hotel 99", "price", "300/night", "imageUrl", "https://www.gannett-cdn.com/-mm-/05b227ad5b8ad4e9dcb53af4f31d7fbdb7fa901b/c=0-64-2119-1259/local/-/media/USATODAY/USATODAY/2014/08/13/1407953244000-177513283.jpg"));

    Button.webViewButton("Option C", "http://example.com", WebViewHeight.COMPACT);
    List<Button> buttons =
        result.stream()
            .map(hotel -> Button.postbackButton(hotel.get("name"), toJson(hotel)))
            .collect(Collectors.toList());

    ButtonListPayload payload = new ButtonListPayload("", buttons);
    MessageComponent buttonListComponent =
        MessageComponent.uiPayloadMessage(payload, PayloadType.BUTTON_LIST);

    List<Card> cards = result.stream()
        .map(
            hotel ->
                new Card(
                    hotel.get("name"),
                    hotel.get("imageUrl"),
                    hotel.get("price"),
                    Set.of(Button.postbackButton("Book " + hotel.get("name"), ""))))
        .collect(Collectors.toList());
    CarouselPayload carouselPayload = new CarouselPayload(cards);
    MessageComponent carouselComponent =
        MessageComponent.uiPayloadMessage(carouselPayload, PayloadType.CAROUSEL);

    return ServiceActionResult.of(
        Map.of("numHits", String.valueOf(result.size())),
        Map.of("hotelsButtonList", buttonListComponent, "hotelsCarousel", carouselComponent));
  }

  @ServiceAction(
      displayName = "Get Hotels by City (error test case)",
      id = "booking::hotelsbycityerror",
      inputs = {
          @Input(name = "location", description = "Location to search hotels"),
          @Input(name = "arrivalDate", description = "Date of arrival at destination"),
          @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "numHits", description = "Number of hotels found"),
      displayOutputs = {
          @DisplayOutput(
              name = "hotelsButtonList",
              description = "Available hotels in a button list",
              payloadType = PayloadType.BUTTON_LIST),
          @DisplayOutput(
              name = "hotelsCarousel",
              description = "Available hotels in a carousel",
              payloadType = PayloadType.CAROUSEL)
      })
  public ServiceActionResult getHotelsByCityError(
      String location, String arrivalDate, String returnDate) {
    return ServiceActionResult.ofError("UNKNOWN_ENTITY", "Unknown hotel specified");
  }

  @ServiceAction(
      displayName = "Get Average Price by Hotel",
      id = "booking::avgPriceByHotel",
      inputs = @Input(name = "hotelName", description = "Name of selected hotel"),
      dataOutputs =
          @DataOutput(name = "avgPrice", description = "Average price per night of the hotel"))
  public ServiceActionResult getAvgPriceByHotel(String hotelName) {
    return ServiceActionResult.ofData(Map.of("avgPrice", "USD 300.00"));
  }

  @ServiceAction(
      displayName = "Submit Travel Request",
      id = "booking::submitTravelRequest",
      inputs = {
        @Input(name = "flightName", description = "Name of selected flight"),
        @Input(name = "hotelName", description = "Name of selected hotel"),
        @Input(name = "location", description = "Travel location"),
        @Input(name = "arrivalDate", description = "Date of arrival at destination"),
        @Input(name = "returnDate", description = "Date of return"),
      },
      dataOutputs = @DataOutput(name = "requestId", description = "Travel request id"))
  public ServiceActionResult submitApprovalRequest(
      String flightName, String hotelName, String location, String arrivalDate, String returnDate) {
    return ServiceActionResult.ofData(Map.of("requestId", UUID.randomUUID()));
  }

  private String toJson(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }
}
