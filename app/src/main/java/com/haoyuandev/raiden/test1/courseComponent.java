package com.haoyuandev.raiden.test1;

import android.support.annotation.NonNull;

class courseComponent implements Comparable<courseComponent>{
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

    @Override
    public int compareTo(@NonNull courseComponent o) {
        return name.compareTo(o.getName());
    }
}
