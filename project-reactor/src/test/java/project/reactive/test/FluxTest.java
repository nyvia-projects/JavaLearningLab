package project.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@Slf4j
public class FluxTest {

    @Test
    void fluxSubscriber() {

        Flux<String> fluxString = Flux.just("User One", "User Two", "User Three")
                .log();

        StepVerifier.create(fluxString)
                .expectNext("User One", "User Two", "User Three")
                .verifyComplete();

    }

    @Test
    void fluxSubscriberInteger() {
        Flux<Integer> fluxInt = Flux.range(1, 4)
                .log();

        fluxInt.subscribe(integer -> log.info("Int: {}", integer));

        StepVerifier.create(fluxInt)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();

    }

    @Test
    void fluxSubscriberFromList() {
        Flux<Integer> fluxInt = Flux.fromIterable(List.of(1, 2, 3, 4))
                .log();

        fluxInt.subscribe(integer -> log.info("Int: {}", integer));

        StepVerifier.create(fluxInt)
                .expectNext(1, 2, 3, 4)
                .verifyComplete();

    }

    @Test
    void fluxSubscriberNumbersError(){
        Flux<Integer> fluxInt = Flux.range(1, 4)
                .log()
                .map(integer -> {
                    if (integer == 4)  throw new IndexOutOfBoundsException("Index error");
                    return integer;
                }
                );

        fluxInt.subscribe(integer -> log.info("Int: {}", integer), Throwable::printStackTrace,
                () -> log.info("DONE!"));

        StepVerifier.create(fluxInt)
                .expectNext(1,2,3)
                .expectError(IndexOutOfBoundsException.class)
                .verify();

    }

    @Test
    void fluxSubscriberUglyBackpressure() {
        Flux<String> flux = Flux.just("A","B","C","D","E","F","G","H","I","J")
                .log();

        flux.subscribe(new Subscriber<>() {
            private int count = 0;
            private Subscription subscription;
            private final int requestCount = 2;

            @Override
            public void onSubscribe(Subscription subscription) {

                this.subscription = subscription;
                subscription.request(requestCount);
            }

            @Override
            public void onNext(String s) {
                count++;
                if (count >= 2) {
                    count = 0;
                    subscription.request(requestCount);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                log.info("DONE");
            }
        });
        log.info("--------------------------");
        StepVerifier.create(flux)
                .expectNext("A","B","C","D","E","F","G","H","I","J")
                .verifyComplete();

    }


}
