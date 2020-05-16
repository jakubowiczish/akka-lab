package task2;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_RemoteActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        System.out.println(getSelf().path());
        return receiveBuilder()
                .match(String.class, s -> {
                    System.out.println(s);
                    getSender().tell("message: " + s.toUpperCase(), getSelf());
                })
                .matchAny(o -> log.info("Unknown message received"))
                .build();
    }
}
