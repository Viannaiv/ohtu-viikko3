package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException{
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        Submission[] subs = getSubmissions(studentNr);
        CourseInfo[] coursei = getCourseInfo();

        System.out.println("Opiskelijanumero: " + studentNr + "\n");
        
        for(CourseInfo c : coursei) {
            List<Submission> submissions = getSubmissionsOfACourse(c.getName(), subs);
            
            if (submissions.isEmpty()) {
                continue;
            }
            
            System.out.println(c.getFullName() + " " + c.getTerm() +" " + c.getYear() + "\n");
            
            int totex = 0;
            int tothours = 0;
            
            for (Submission s : submissions) {
                System.out.println("Viikko " + s.getWeek() + ":");
                System.out.println("-Tehtäviä tehty yhteensä " + s.totalExercises() 
                        + "/" + c.getWeekExerciseMax(s.getWeek()) +
                        ", aikaa kului " + s.getHours() + ", tehdyt tehtävät: " +
                        s.exercisesAsString());

                totex += s.totalExercises();
                tothours += s.getHours();
            }

            System.out.println("");
            System.out.println("Yhteensä: " + totex + "/" + c.getExerciseTotalOfCourse() +
                    " tehtävää ja " + tothours + " tuntia \n");
        }
    }
    
    private static Submission[] getSubmissions(String studentnumber) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentnumber+"/submissions";
        String bodyText = Request.Get(url).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        return subs;
    }
    
    private static CourseInfo[] getCourseInfo() throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String bodyText = Request.Get(url).execute().returnContent().asString();
        
        Gson mapper = new Gson();
        CourseInfo[] coursei = mapper.fromJson(bodyText, CourseInfo[].class);
        
        System.out.println("json-muotoinen data:");
        System.out.println( bodyText );

        return coursei;
    }
    
    private static List<Submission> getSubmissionsOfACourse(String name, Submission[] subs) {
        List<Submission> submissions = new ArrayList<>();
        for (Submission sub: subs) {
            if (sub.getCourse().equals(name)) {
                submissions.add(sub);
            }
        }
        return submissions;
    }
}
