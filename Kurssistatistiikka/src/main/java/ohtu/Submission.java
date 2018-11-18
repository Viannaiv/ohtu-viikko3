package ohtu;

public class Submission {
    private int week;
    private int hours;
    private String[] exercises;
    private String course;

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String[] getExercises() {
        return exercises;
    }

    public void setExercises(String[] exercises) {
        this.exercises = exercises;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
    
    public String exercisesAsString() {
        String ex = "";
        for (int i = 0; i < exercises.length; i++) {
            if (i != exercises.length -1) {
                ex += exercises[i] + ", ";
            } else {
                ex += exercises[i];
            }
        }
        
        return ex;
    }
    
    public int totalExercises() {
        return exercises.length;
    }

    @Override
    public String toString() {
        return week + ", " + hours + ", " + exercisesAsString() + ", " + course;
    }
    
}