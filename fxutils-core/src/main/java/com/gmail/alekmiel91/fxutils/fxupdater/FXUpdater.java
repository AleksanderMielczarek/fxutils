package com.gmail.alekmiel91.fxutils.fxupdater;

import javafx.application.Platform;
import jfxtras.util.PlatformUtil;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public interface FXUpdater {
    public default <T> void update(Consumer<T> consumer, T value) {
        if (Platform.isFxApplicationThread()) {
            consumer.accept(value);
        } else {
            Platform.runLater(() -> consumer.accept(value));
        }
    }

    public default void update(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    public default <T> T updateWait(Callable<T> action) {
        return PlatformUtil.runAndWait(action);
    }

    public default void updateWait(Runnable action) {
        PlatformUtil.runAndWait(action);
    }

    public default <T> void updateWait(Consumer<T> consumer, T value) {
        PlatformUtil.runAndWait(() -> consumer.accept(value));
    }
}
