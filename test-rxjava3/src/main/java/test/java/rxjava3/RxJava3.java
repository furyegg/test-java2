package test.java.rxjava3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RxJava3 {
    public static void main(String[] args) throws InterruptedException {
        Observable<Integer> observable = Observable.fromArray(1, 2, 3).mergeWith(Observable.fromArray(4, 5, 6));
        observable
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.computation())
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                log.info("onSubscribe");
            }
            
            @Override
            public void onNext(@NonNull Integer s) {
                log.info("onNext: " + s);
            }
            
            @Override
            public void onError(@NonNull Throwable e) {
                log.info("onError: " + e.getMessage());
            }
            
            @Override
            public void onComplete() {
                log.info("onComplete");
            }
        });
        Thread.sleep(1000);
    }
}