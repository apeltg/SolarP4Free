package me.solarcollections.jvpc.p4.hook.hotbar;

import me.solarcollections.jvpc.p4.menus.MenuLobbies;
import me.solarcollections.jvpc.p4.menus.MenuShop;
import me.solarcollections.jvpc.p4.menus.kits.MenuKits;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.hotbar.HotbarActionType;

public class P4HotbarActionType extends HotbarActionType {

  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    } else if (action.equalsIgnoreCase("loja")) {
      new MenuShop(profile);
    } else if (action.equalsIgnoreCase("kits")) {
      new MenuKits(profile);
    }
  }
}
