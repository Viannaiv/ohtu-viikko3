package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        String studentNr = "012345678";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        
        System.out.println("Opiskelijanumero: " + studentNr + "\n");
        int totex = 0;
        int tothours = 0;
        for (Submission s : subs) {
            System.out.println(s.getCourse() + ", viikko " + s.getWeek() + 
                    " tehtäviä tehty yhteensä " + s.totalExercises() +
                    ", aikaa kului " + s.getHours() + ", tehdyt tehtävät: " +
                    s.exercisesAsString());
            
            totex += s.totalExercises();
            tothours += s.getHours();
        }
        
        System.out.println("");
        System.out.println("Yhteensä: " + totex + " tehtävää ja " + tothours + " tuntia");
    }
}
