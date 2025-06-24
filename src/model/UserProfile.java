package model;

import java.time.LocalDate;

public class UserProfile {
    private int id;
    private String name;
    private String gender;
    private LocalDate dob;
    private int heightCm;
    private int weightKg;

    public UserProfile() {}

    public UserProfile(String name, String gender, LocalDate dob, int heightCm, int weightKg) {
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }

    public UserProfile(int id, String name, String gender, LocalDate dob, int heightCm, int weightKg) {
        this(name, gender, dob, heightCm, weightKg);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public int getHeightCm() { return heightCm; }
    public void setHeightCm(int heightCm) { this.heightCm = heightCm; }
    public int getWeightKg() { return weightKg; }
    public void setWeightKg(int weightKg) { this.weightKg = weightKg; }

    @Override
    public String toString() {
        return name == null ? "" : name;
    }
}
