package com.haoyuandev.raiden.test1;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class CourseInfo implements Comparable<CourseInfo> {
    private String courseName;
    private List<courseComponent> courseData;

    public CourseInfo(String courseName) {
        this.courseName = courseName;
        this.courseData = new ArrayList<>();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
      if(courseData.isEmpty()) {
          return 0;
      } else {
          double netgrade = 0;
          for (int i = 0; i < courseData.size(); i++) {
              netgrade += courseData.get(i).getWeight() * courseData.get(i).getScore() * 0.01;
          }
          netgrade = Math.round(netgrade);
          return (int)netgrade;
      }
    };

    public double getWeight() {
        double netweight = 0;
        for(courseComponent i: courseData) {
            netweight += i.getWeight();
        }
        return netweight;
    }

    public void addCourseData(courseComponent newdata) {
        courseData.add(newdata);
    }

    public List<courseComponent> getCourseData() {
        return courseData;
    }

    public String toString() {
        return courseName + ": " + getGrade();
    }


    @Override
    public int compareTo(@NonNull CourseInfo o) {
        return courseName.compareTo(o.getCourseName());
    }
}
