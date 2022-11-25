package com.disney.controller;

import com.disney.model.Ticket;
import com.disney.service.TicketLinkingService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TicketLinkingController {

  @Autowired TicketLinkingService TicketLinkingService;

  @ApiOperation(value = "Get all Tickets")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Ticket> retrieveAllTickets() {
    return TicketLinkingService.fetchTickets();
  }

  @ApiOperation(value = "Get Ticket by visualId")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "OK")})
  @GetMapping(value = "/tickets/{visualId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Ticket retrieveAllTickets(@PathVariable String visualId) {
    return TicketLinkingService.fetchTicketByVId(visualId);
  }
}
