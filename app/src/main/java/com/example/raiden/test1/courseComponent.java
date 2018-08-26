package com.example.raiden.test1;

class courseComponent {
    private String name;
    private double weight;
    private double score;

    public courseComponent(String name, double weight, double score) {
        this.name = name;
        this.weight = weight;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
