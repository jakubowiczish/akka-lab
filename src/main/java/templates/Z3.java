package templates;

import akka.actor.ActorSystem;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import java.util.Arrays;

public class Z3 {

    public static void main(String[] argv) throws Exception {

        ActorSystem system = ActorSystem.create("stream_system");

        Source.range(1, 10)
                .map(val -> val * 2)
                .runWith(Sink.foreach(val -> System.out.println(val)), system);

        Source.from(Arrays.asList("hello", "hola", "hallo", "ola", "ahlan", "ni hao", "shalom", "salut", "ciao", "hug", "yasou"))
                .map(val -> val.toUpperCase())
                .runWith(Sink.foreach(val -> System.out.println(val)), system);

        Source.range(-5, -1)
                .map(val -> val * 2)
                .runWith(Sink.last(), system)
                .thenAccept(System.out::println);

        // TODO: final stream composition in task: source -> buffer -> map -> async -> map -> async -> runWith -> thenRun

    }

}
