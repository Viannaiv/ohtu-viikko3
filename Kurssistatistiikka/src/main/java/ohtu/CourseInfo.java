
package ohtu;

public class CourseInfo {
    private String name;
    private String fullName;
    private String[] exercises;
    private String term;
    private String year;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String[] getExercises() {
        return exercises;
    }

    public void setExercises(String[] exercises) {
        this.exercises = exercises;
    }
    
    public String getWeekExerciseMax(int week) {
        return exercises[week];
    }
    
    public int getExerciseTotalOfCourse() {
        int ex = 0;
        for (String exercise : exercises) {
            ex += Integer.parseInt(exercise);
        }
        
        return ex;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return name + ", " + fullName;
    }
}
