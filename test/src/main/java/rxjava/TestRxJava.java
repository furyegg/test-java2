package rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class TestRxJava {
    public static void main(String[] args) {
        Observable<Integer> observable = Observable.fromArray(1,2,3).mergeWith(Observable.fromArray(4,5,6));
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe");
            }
    
            @Override
            public void onNext(@NonNull Integer s) {
                System.out.println("onNext: " + s);
            }
    
            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }
    
            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });
    }
}