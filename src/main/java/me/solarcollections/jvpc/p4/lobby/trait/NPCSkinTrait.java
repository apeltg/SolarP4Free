package me.solarcollections.jvpc.p4.lobby.trait;

import me.solarcollections.jvpc.libraries.npclib.api.npc.NPC;
import me.solarcollections.jvpc.libraries.npclib.npc.skin.Skin;
import me.solarcollections.jvpc.libraries.npclib.npc.skin.SkinnableEntity;
import me.solarcollections.jvpc.libraries.npclib.trait.NPCTrait;

public class NPCSkinTrait extends NPCTrait {

  private Skin skin;
  
  public NPCSkinTrait(NPC npc, String value, String signature) {
    super(npc);
    this.skin = Skin.fromData(value, signature);
  }
  
  @Override
  public void onSpawn() {
    this.skin.apply((SkinnableEntity) this.getNPC().getEntity());
  }
}
