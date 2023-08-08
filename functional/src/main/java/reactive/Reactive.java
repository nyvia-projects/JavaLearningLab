package reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

import static shared.Utils.sleep;

public class Reactive {
    public static void main(String[] args) {

        SubmissionPublisher<Integer> feed = new SubmissionPublisher<>();

        feed.subscribe(new Flow.Subscriber<Integer>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(2);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(item);
                subscription.request(1);

                if (item == 12) subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("ERR: " + throwable);
            }

            @Override
            public void onComplete() {
                System.out.println("DONE");
            }
        });

        for (int i = 1; i <= 15; i++) {
            feed.submit(i);
        }

        sleep(10000);
        System.out.println("--------");

    }


}
