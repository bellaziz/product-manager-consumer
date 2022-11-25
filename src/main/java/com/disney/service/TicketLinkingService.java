package com.disney.service;

import com.disney.model.Ticket;
import com.disney.restclient.TicketProviderClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketLinkingService {

  private TicketProviderClient ticketProviderClient;

  @Autowired
  public TicketLinkingService(TicketProviderClient ticketProviderClient) {
    this.ticketProviderClient = ticketProviderClient;
  }

  public List<Ticket> fetchTickets() {
    return ticketProviderClient.retrieveAllTickets();
  }

  public Ticket fetchTicketByVId(String vid) {
    return ticketProviderClient.retrieveTicketByVId(vid);
  }
}
