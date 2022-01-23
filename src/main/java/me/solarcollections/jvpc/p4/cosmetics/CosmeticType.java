package me.solarcollections.jvpc.p4.cosmetics;


public enum CosmeticType {
  KIT("Kit"),
  PERK("Habilidade"),
  KILL_EFFECT("Efeitos de Abate"),
  FALL_EFFECT("Efeitos de Queda"),
  DEATH_CRY("Gritos de Morte"),
  DEATH_MESSAGE("Mensagens de Morte"),
  DEATH_HOLOGRAM("Hologramas de Morte"),
  CUSTOM_INPUT("Mensagens de Entrada");
  
  private final String[] names;
  
  CosmeticType(String... names) {
    this.names = names;
  }
  
  public String getName(long index) {
    return this.names[(int) (index - 1)];
  }
}
