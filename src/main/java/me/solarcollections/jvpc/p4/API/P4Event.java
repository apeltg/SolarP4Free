package me.solarcollections.jvpc.p4.API;

import java.util.ArrayList;
import java.util.List;

public class P4Event {

  private static final List<P4EventHandler> HANDLERS = new ArrayList<>();

  public static void registerHandler(P4EventHandler handler) {
    HANDLERS.add(handler);
  }

  public static void callEvent(P4Event evt) {
    HANDLERS.stream().filter(handler -> handler.getEventTypes().contains(evt.getClass())).forEach(handler -> handler.handleEvent(evt));
  }
}
