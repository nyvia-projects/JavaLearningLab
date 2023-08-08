package project.reactive.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class MonoTest {

    @Test
    void monoSubscriber() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe();

        log.info("-----------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();

    }

    @Test
    void monoSubscriberConsumer() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .log();

        mono.subscribe(s -> log.info("Value: {}", s));

        log.info("-----------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name)
                .verifyComplete();

    }

    @Test
    void monoSubscriberConsumerError() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .map(s -> {
                    throw new RuntimeException("Testing mono with error");
                });

        mono.subscribe(s -> log.info("Name {}", s), throwable -> log.error("Something unexpected happened"));
        mono.subscribe(s -> log.info("Name {}", s), Throwable::printStackTrace);

        log.info("-----------------------------------------");

        StepVerifier.create(mono)
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    void monoSubscriberConsumerComplete() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value: {}", s), Throwable::printStackTrace, () -> log.info("FINISHED!"));

        log.info("-----------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();

    }

    @Test
    void monoSubscriberConsumerSubscription() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase);

        mono.subscribe(s -> log.info("Value: {}", s), Throwable::printStackTrace, () -> log.info("FINISHED!"), subscription -> subscription.request(5));

        log.info("-----------------------------------------");

        StepVerifier.create(mono)
                .expectNext(name.toUpperCase())
                .verifyComplete();

    }

    @Test
    void monoDoOnMethods() {
        String name = "First Last";
        Mono<String> mono = Mono.just(name)
                .log()
                .map(String::toUpperCase)
                .doOnSubscribe(subscription -> log.info("Subscribed"))
                .doOnRequest(value -> log.info("Request Received, starting..."))
                .doOnNext(s -> log.info("Value received, executing doOnNext {}", s))
                .doOnSuccess(s -> log.info("doOnSuccess executed"));


        mono.subscribe(s -> log.info("Value: {}", s), Throwable::printStackTrace, () -> log.info("FINISHED!"));

    }

    @Test
    void monoDoOnError() {

        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal Argument Exception"))
                .doOnError(throwable -> log.error("Error message: {}", throwable.getMessage()))
                .doOnNext(s -> log.info("Executing current doOnNext"))
                .log();

        StepVerifier.create(error)
                .expectError(IllegalArgumentException.class)
                .verify();

    }

    @Test
    void monoOnErrorResume() {
        String name = "First Last";

        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal Argument Exception"))
                .doOnError(throwable -> log.error("Error message: {}", throwable.getMessage()))
                .onErrorResume(throwable -> {
                    log.info("Inside of onErrorResume");
                    return Mono.just(name);
                })
                .log();

        StepVerifier.create(error)
                .expectNext(name)
                .verifyComplete();

    }

    /**
     * doOnError can be used for logging and error (side effect)
     * onErrorReturn is used for static response to error
     * onErrorResume is used as fallback
     */

    @Test
    void monoOnErrorReturn() {
        String name = "First Last";

        Mono<Object> error = Mono.error(new IllegalArgumentException("Illegal Argument Exception"))
                .onErrorReturn("Empty")
                .onErrorResume(throwable -> { //won't execute
                    log.info("Inside of onErrorResume");
                    return Mono.just(name);
                })
                .doOnError(throwable -> log.error("Error message: {}", throwable.getMessage())) //won't execute
                .log();

        StepVerifier.create(error)
                .expectNext("Empty")
                .verifyComplete();

    }


}
