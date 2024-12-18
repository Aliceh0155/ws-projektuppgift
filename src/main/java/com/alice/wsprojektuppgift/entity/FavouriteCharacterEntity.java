package com.alice.wsprojektuppgift.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "favourite_character")
public class FavouriteCharacterEntity {

  @Id
  private String mongoId; // MongoDB ID

  private String apiId; // API ID
  private String name;
  private String species;
  private String gender;
  private String house;
  private String dateOfBirth;
  private int yearOfBirth;
  private boolean wizard;
  private String ancestry;
  private String eyeColour;
  private String hairColour;
  private WandEntity wand;
  private boolean hogwartsStudent;
  private boolean alive;
  private String image;

  public FavouriteCharacterEntity() {
  }

  public FavouriteCharacterEntity(String apiId, String name, String species, String gender, String house, String dateOfBirth, int yearOfBirth, boolean wizard, String ancestry, String eyeColour, String hairColour, WandEntity wand, boolean hogwartsStudent, boolean alive, String image) {
    this.apiId = apiId;
    this.name = name;
    this.species = species;
    this.gender = gender;
    this.house = house;
    this.dateOfBirth = dateOfBirth;
    this.yearOfBirth = yearOfBirth;
    this.wizard = wizard;
    this.ancestry = ancestry;
    this.eyeColour = eyeColour;
    this.hairColour = hairColour;
    this.wand = wand;
    this.hogwartsStudent = hogwartsStudent;
    this.alive = alive;
    this.image = image;
  }


  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpecies() {
    return species;
  }

  public void setSpecies(String species) {
    this.species = species;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getHouse() {
    return house;
  }

  public void setHouse(String house) {
    this.house = house;
  }

  public String getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(String dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public int getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(int yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public boolean isWizard() {
    return wizard;
  }

  public void setWizard(boolean wizard) {
    this.wizard = wizard;
  }

  public String getAncestry() {
    return ancestry;
  }

  public void setAncestry(String ancestry) {
    this.ancestry = ancestry;
  }

  public String getEyeColour() {
    return eyeColour;
  }

  public void setEyeColour(String eyeColour) {
    this.eyeColour = eyeColour;
  }

  public String getHairColour() {
    return hairColour;
  }

  public void setHairColour(String hairColour) {
    this.hairColour = hairColour;
  }

  public WandEntity getWand() {
    return wand;
  }

  public void setWand(WandEntity wand) {
    this.wand = wand;
  }

  public boolean isHogwartsStudent() {
    return hogwartsStudent;
  }

  public void setHogwartsStudent(boolean hogwartsStudent) {
    this.hogwartsStudent = hogwartsStudent;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}

