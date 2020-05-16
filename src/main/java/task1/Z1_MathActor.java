package task1;

import akka.actor.AbstractActor;
import akka.actor.AllForOneStrategy;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.DeciderBuilder;
import scala.concurrent.duration.Duration;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class Z1_MathActor extends AbstractActor {

    // for logging
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // must be implemented -> creates initial behaviour
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    if (s.equals("hi")) {
                        System.out.println("hello");
                    } else if (s.startsWith("m")) {
                        context().child("multiplyWorker").get().tell(s, getSelf()); // send task to child
                    } else if (s.startsWith("d")) {
                        context().child("divideWorker").get().tell(s, getSelf()); // send task to child
                    } else if (s.startsWith("result")) {
                        System.out.println(s);              // result from child
                    }
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }

    // optional
    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(Z1_MultiplyWorker.class), "multiplyWorker");
        context().actorOf(Props.create(Z1_DivideWorker.class), "divideWorker");
    }

//    private static final SupervisorStrategy strategy
//            = new OneForOneStrategy(
//            10,
//            Duration.create("1 minute"),
//            DeciderBuilder.match(ArithmeticException.class,
//                    e -> (SupervisorStrategy.Directive) resume())
//                    .matchAny(o -> (SupervisorStrategy.Directive) restart())
//                    .build());

    private static final SupervisorStrategy strategy
            = new AllForOneStrategy(
            10,
            Duration.create("1 minute"),
            DeciderBuilder.match(ArithmeticException.class,
                    e -> (SupervisorStrategy.Directive) resume())
                    .matchAny(o -> (SupervisorStrategy.Directive) restart())
                    .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

}
