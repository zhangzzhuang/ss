package com.example.lenovo.myapplication.event;



import com.example.lenovo.myapplication.utils.RxUtil;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * @User Jian.Wang
 * @Date 2018/8/19
 * @Time 下午11:11
 * @Version 1.0
 * @Description 响应式事件总线
 */
public class RxBus {
    private final FlowableProcessor<Object> bus;

    private RxBus() {
        bus = PublishProcessor.create().toSerialized();
    }

    public static RxBus getDefault() {
        return RxBusHolder.SINSTANCE;
    }

    private static class RxBusHolder {
        private static final RxBus SINSTANCE = new RxBus();
    }


    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public <T> Disposable toDefaultFlowable(Class<T> eventType, Consumer<T> act) {
        return bus.ofType(eventType).compose(RxUtil.<T>rxSchedulerHelper()).subscribe(act);
    }

    public void unregister(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public interface RxbusCallback {
        void onSuccess(RxEvent messageEvent);
    }

    public <T> Flowable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public Disposable register(final RxbusCallback callback) {
        return toObservable(RxEvent.class)
                .subscribe(new Consumer<RxEvent>() {
                    @Override
                    public void accept(RxEvent messageEvent) throws Exception {
                        callback.onSuccess(messageEvent);
                    }
                });
    }
}
