package io.github.thebusybiscuit.slimefun4.implementation;

import io.github.thebusybiscuit.slimefun4.utils.NumberUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * This class stores some startup warnings we occasionally need to print.
 * If you setup your server the recommended way, you are never going to see
 * any of these messages.
 *
 * @author TheBusyBiscuit
 *
 */
final class StartupWarnings {

    private static final String BORDER = "****************************************************";
    private static final String PREFIX = "* ";

    private StartupWarnings() {}

    @ParametersAreNonnullByDefault
    static void discourageCSCoreLib(Logger logger) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "你好像安裝了 CS-CoreLib。");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "自 2021/01/30 起就不再強制依賴 CS-CoreLib 了");
        logger.log(Level.SEVERE, PREFIX + "你需要卸載 CS-CoreLib 才能讓 Slimefun 正常運行。");
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void invalidMinecraftVersion(Logger logger, String detectedVer, String slimefunVersion) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "Slimefun 加載失敗!");
        logger.log(Level.SEVERE, PREFIX + "你正在使用不支持的 Minecraft 版本!");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "你正在使用 Minecraft {0}", detectedVer);
        logger.log(Level.SEVERE, PREFIX + "但 Slimefun {0} 只支持以下版本:", slimefunVersion);
        logger.log(Level.SEVERE, PREFIX + "Minecraft {0}", String.join(" / ", Slimefun.getSupportedVersions()));
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void invalidServerSoftware(Logger logger) {
        logger.log(Level.SEVERE, BORDER);
        logger.log(Level.SEVERE, PREFIX + "Slimefun 加載失敗!");
        logger.log(Level.SEVERE, PREFIX + "我們不再支持 CraftBukkit 服務端了!");
        logger.log(Level.SEVERE, PREFIX);
        logger.log(Level.SEVERE, PREFIX + "你需要使用 Paper 或其分支的服務端");
        logger.log(Level.SEVERE, PREFIX + "(我們推薦 Paper)");
        logger.log(Level.SEVERE, BORDER);
    }

    @ParametersAreNonnullByDefault
    static void oldJavaVersion(Logger logger, int recommendedJavaVersion) {
        int javaVersion = NumberUtils.getJavaVersion();

        logger.log(Level.WARNING, BORDER);
        logger.log(Level.WARNING, PREFIX + "正在使用的 Java 版本 (Java {0}) 已過時.", javaVersion);
        logger.log(Level.WARNING, PREFIX);
        logger.log(Level.WARNING, PREFIX + "由於高版本 Minecraft 對 Java {0} 的強制依賴,", recommendedJavaVersion);
        logger.log(Level.WARNING, PREFIX + "我們推薦您儘快升級到 Java {0}.", recommendedJavaVersion);
        logger.log(Level.WARNING, PREFIX + "同時，為儘快使用到新版本 Java 帶來的特性,");
        logger.log(Level.WARNING, PREFIX + "Slimefun 也會在不久的將來依賴於 Java {0}.", recommendedJavaVersion);
        logger.log(Level.WARNING, PREFIX + "為了不影響您以後的正常使用，請儘快更新!");
        logger.log(Level.WARNING, BORDER);
    }
}
