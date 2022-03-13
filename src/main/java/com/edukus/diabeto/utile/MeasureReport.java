package com.edukus.diabeto.utile;

public class MeasureReport {

  String nominalMeasure;
  Integer preMorning;
  Integer postMorning;
  Integer preAfternoon;
  Integer postAfternoon;
  Integer preEvening;
  Integer postEvening;
  Integer preNight;
  Integer postNight;

  public MeasureReport(String nominalMeasure, Integer preMorning, Integer postMorning, Integer preAfternoon, Integer postAfternoon,Integer preEvening, Integer postEvening, Integer preNight, Integer postNight) {
    this.nominalMeasure = nominalMeasure;
    this.preMorning = preMorning;
    this.postMorning = postMorning;
    this.preAfternoon = preAfternoon;
    this.postAfternoon = postAfternoon;
    this.preEvening = preEvening;
    this.postEvening = postEvening;
    this.preNight = preNight;
    this.postNight = postNight;
  }

  public String getNominalMeasure() {
    return nominalMeasure;
  }

  public Integer getPreMorning() {
    return preMorning;
  }

  public Integer getPostMorning() {
    return postMorning;
  }

  public Integer getPreAfternoon() {
    return preAfternoon;
  }

  public Integer getPostAfternoon() {
    return postAfternoon;
  }

  public Integer getPreEvening() {
    return preEvening;
  }

  public Integer getPostEvening() {
    return postEvening;
  }

  public Integer getPreNight() {
    return preNight;
  }

  public Integer getPostNight() {
    return postNight;
  }
}
