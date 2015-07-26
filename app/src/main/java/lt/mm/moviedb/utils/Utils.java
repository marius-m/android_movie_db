package lt.mm.moviedb.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mariusmerkevicius on 7/26/15.
 */
public class Utils {
    /**
     * Generate a value suitable for use in setId(int).
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * http://stackoverflow.com/questions/1714297/android-view-setidint-id-programmatically-how-to-avoid-id-conflicts
     *
     * @return a generated ID value
     */
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}
