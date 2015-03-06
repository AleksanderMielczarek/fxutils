package com.gmail.alekmiel91.fxutils.fxupdater;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class FXUpdaterUtils {
    private static final FXUpdater FX_UPDATER = new SimpleFXUpdater();

    private FXUpdaterUtils() {
    }

    public static <T> void update(Consumer<T> consumer, T value) {
        FX_UPDATER.update(consumer, value);
    }

    public static void update(Runnable action) {
        FX_UPDATER.update(action);
    }

    public static <T> T updateWait(Callable<T> action) {
        return FX_UPDATER.updateWait(action);
    }

    public static void updateWait(Runnable action) {
        FX_UPDATER.updateWait(action);
    }

    public static <T> void updateWait(Consumer<T> consumer, T value) {
        FX_UPDATER.updateWait(consumer, value);
    }

    public static FXUpdater getFxUpdater() {
        return FX_UPDATER;
    }

    private static class SimpleFXUpdater implements FXUpdater {
    }
}
