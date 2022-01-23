package me.solarcollections.jvpc.p4.API;

import java.util.List;

public interface P4EventHandler {

  int handleEvent(P4Event evt);

  List<Class<?>> getEventTypes();
}
