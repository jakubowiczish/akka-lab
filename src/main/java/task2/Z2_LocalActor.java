package task2;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private static final String PATH_TO_REMOTE_ACTOR = "akka://remote_system@127.0.0.1:2552/user/remote";

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.startsWith("message")) {
                        System.out.println(s);
                    } else {
                        getContext().actorSelection(PATH_TO_REMOTE_ACTOR).tell(s, getSelf());
                    }
                })
                .matchAny(o -> log.info("Unknown message received"))
                .build();
    }
}
