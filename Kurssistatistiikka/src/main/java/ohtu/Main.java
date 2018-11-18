package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            
            List<Integer> stats = getStatisticsOfCourse(c.getName());
            
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
            System.out.println("Kursilla yhteensä " + stats.get(0) + 
                    " palautusta, palautettuja tehtäviä " + stats.get(1) +
                    ", aikaa käytetty yhteensä tunteina " + stats.get(2) + "\n");
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
        
//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );

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
    
    private static List<Integer> getStatisticsOfCourse(String name) throws IOException {
        String url = "https://studies.cs.helsinki.fi/courses/" +  name + "/stats";
        String statsResponse = Request.Get(url).execute().returnContent().asString();

        JsonParser parser = new JsonParser();
        JsonObject parsedData = parser.parse(statsResponse).getAsJsonObject();
        
        int i = 1;
        int submissions = 0;
        int exercisetotal = 0;
        int totalhours = 0;
        List<Integer> stats = new ArrayList<>();
        
        while(true) {
            JsonObject x = parsedData.getAsJsonObject(String.valueOf(i));
            if (x == null) {
                break;
            }
            submissions += x.get("students").getAsInt();
            exercisetotal += x.get("exercise_total").getAsInt();
            totalhours += x.get("hour_total").getAsInt();
            i++;
        }
        stats.add(submissions);
        stats.add(exercisetotal);
        stats.add(totalhours);
        
        return stats;
    }
    
}
