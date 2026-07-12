package city.norain.slimefun4;

import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;

class EnvironmentChecker {
    private static final List<String> UNSUPPORTED_PLUGINS = List.of(
            "BedrockTechnology", "SlimefunFix", "SlimefunBugFixer", "Slimefunbookfix", "PlaceItemsOnGroundRebuilt");

    static boolean checkIncompatiblePlugins(@Nonnull Logger logger) {
        List<String> plugins = UNSUPPORTED_PLUGINS.stream()
                .filter(name -> Bukkit.getServer().getPluginManager().isPluginEnabled(name))
                .toList();

        if (plugins.isEmpty()) {
            return false;
        }

        printBorder(logger);
        logger.log(Level.WARNING, "");
        logger.log(Level.WARNING, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.log(Level.WARNING, "檢測到不兼容的插件, 已自動禁用 Slimefun!");
        logger.log(Level.WARNING, "不兼容插件列表: ", String.join(", ", plugins));
        logger.log(Level.WARNING, "這些插件出現在這裏是因為它們已不兼容現有");
        logger.log(Level.WARNING, "Slimefun 版本或是與 Slimefun 衝突.");
        logger.log(Level.WARNING, "如果你覺得這些插件能夠與 Slimefun 並存,");
        logger.log(Level.WARNING, "請聯繫我們修改.");
        logger.log(Level.WARNING, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        logger.log(Level.WARNING, "");
        printBorder(logger);

        return true;
    }

    static boolean checkHybridServer() {

        try {
            Class.forName("cpw.mods.modlauncher.Launcher", false, ClassLoader.getSystemClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
        }

        try {
            Class.forName(
                    "net.minecraftforge.server.console.TerminalHandler", false, ClassLoader.getSystemClassLoader());

            return true;
        } catch (ClassNotFoundException ignored) {
        }

        try {
            Class.forName("org.cardboardpowered.mixin.CardboardMixinPlugin", false, ClassLoader.getSystemClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
        }

        try {
            Class.forName("net.fabricmc.loader.impl.FabricLoaderImpl", false, ClassLoader.getSystemClassLoader());
            return true;
        } catch (ClassNotFoundException ignored) {
        }

        if (Bukkit.getPluginCommand("mohist") != null) {
            return true;
        }

        var serverVer = Bukkit.getVersion().toLowerCase();

        return serverVer.contains("arclight") || serverVer.contains("mohist");
    }

    static void scheduleSlimeGlueCheck(@Nonnull Slimefun sf) {
        Bukkit.getScheduler()
                .runTaskLater(
                        sf,
                        () -> {
                            if (Bukkit.getPluginManager().getPlugin("SlimeGlue") == null) {
                                sf.getLogger().log(Level.WARNING, "檢測到沒有安裝 SlimeGlue (粘液膠), 你將缺失對一些插件的額外保護檢查!");
                                sf.getLogger().log(Level.WARNING, "下載: https://github.com/Xzavier0722/SlimeGlue");
                            }
                        },
                        300); // 15s
    }

    private static void printBorder(@Nonnull Logger logger) {
        logger.log(Level.WARNING, "#######################################################");
    }
}
