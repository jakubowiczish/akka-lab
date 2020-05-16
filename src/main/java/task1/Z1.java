package task1;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Z1 {

    public static void main(String[] args) throws Exception {

        // create actor system & actors
        File configFile = new File("local_app.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("local_system", config);
        final ActorRef actor = system.actorOf(Props.create(Z1_MathActor.class), "math");
        System.out.println("Started. Commands: 'hi', 'm [nb1] [nb2]', 'd [nb1] [nb2]', 'q'");

        // read line & send to actor
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            actor.tell(line, null);     // send message to actor
        }

        // finish
        system.terminate();
    }
}