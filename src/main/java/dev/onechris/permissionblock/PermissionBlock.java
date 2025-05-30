package dev.onechris.permissionblock;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.command.CommandSource;
import org.geysermc.geyser.api.event.lifecycle.GeyserPreInitializeEvent;
import org.geysermc.geyser.api.event.lifecycle.GeyserRegisterPermissionCheckersEvent;
import org.geysermc.geyser.api.extension.Extension;
import org.geysermc.geyser.api.permission.PermissionChecker;
import org.geysermc.geyser.api.util.TriState;

public class PermissionBlock implements Extension {

    @Subscribe
    public void onEnable(GeyserPreInitializeEvent ignored) {
        logger().info("PermissionBlock active: All Geyser / extension permissions will be set to false.");
    }

    public static final PermissionChecker PERM_CHECK_INSTANCE = new PermissionBlockChecker();

    @Subscribe
    public void onPermissionRegistration(GeyserRegisterPermissionCheckersEvent event) {
        event.register(PERM_CHECK_INSTANCE);
    }

    public static class PermissionBlockChecker implements PermissionChecker {

        @Override
        public @NonNull TriState hasPermission(@NonNull CommandSource source, @NonNull String permission) {
            // Fallback to other permission checkers
            if (source.hasPermission("permissionblock.exempt")) {
                return TriState.NOT_SET;
            }

            // Block all permissions. Console will never run into this check!
            return TriState.FALSE;
        }
    }
}
