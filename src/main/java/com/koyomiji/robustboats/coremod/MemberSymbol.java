package com.koyomiji.robustboats.coremod;

public class MemberSymbol {
  public String owner;
  public String name;
  public String desc;

  public MemberSymbol(String owner, String name, String desc) {
    this.owner = owner;
    this.name = name;
    this.desc = desc;
  }

  public MemberSymbol(String name, String desc) {
    this.owner = owner;
    this.name = name;
    this.desc = desc;
  }
}
