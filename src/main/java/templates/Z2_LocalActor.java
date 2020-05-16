package templates;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Z2_LocalActor extends AbstractActor {

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                 // TODO: forward msg from AppLocal to remote actor
                 // TODO: print response
                 // TODO: need to distinguish between remote response and msg from AppLocal to avoid infinite msg loop
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
