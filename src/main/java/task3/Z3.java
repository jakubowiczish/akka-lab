package task3;

import akka.actor.ActorSystem;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;

public class Z3 {

    public static void main(String[] argv) throws Exception {

        ActorSystem system = ActorSystem.create("stream_system");

        long start = System.currentTimeMillis();

        Source.range(1, 10)
                .buffer(3, OverflowStrategy.backpressure())
                .map(val -> {
                    Thread.sleep(500);
                    return val * 10;
                })
                .async()
                .map(val -> {
                    Thread.sleep(500);
                    return val * 2;
                })
                .async()
                .runWith(Sink.foreach(System.out::println), system)
        .thenRun(() -> System.out.println(System.currentTimeMillis() - start + " ms"));

        // TODO: final stream composition in task: source -> buffer -> map -> async -> map -> async -> runWith -> thenRun

    }

}
