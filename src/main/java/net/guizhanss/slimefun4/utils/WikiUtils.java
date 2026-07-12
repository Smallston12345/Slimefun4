package net.guizhanss.slimefun4.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.JsonUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.bukkit.plugin.Plugin;

/**
 * 提供Wiki相關實用方法
 *
 * @author ybw0014
 */
public final class WikiUtils {
    private WikiUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 讀取附屬的 wiki.json 並設置物品的 Wiki 按鈕
     *
     * @param addon 附屬 {@link SlimefunAddon} 實例
     */
    public static void setupJson(Plugin addon) {
        setupJson(addon, page -> page);
    }

    /**
     * 讀取附屬的 wiki.json 並設置物品的 Wiki 按鈕
     * 可對頁面地址進行更改
     *
     * @param plugin 附屬 {@link SlimefunAddon} 實例
     * @param formatter 對頁面地址進行更改
     */
    public static void setupJson(Plugin plugin, Function<String, String> formatter) {
        if (!(plugin instanceof SlimefunAddon)) {
            throw new IllegalArgumentException("該插件不是 Slimefun 附屬");
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(plugin.getClass().getResourceAsStream("/wiki.json"), StandardCharsets.UTF_8))) {
            JsonElement element = JsonUtils.parseString(reader.lines().collect(Collectors.joining("")));
            JsonObject json = element.getAsJsonObject();

            int count = 0;

            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                SlimefunItem item = SlimefunItem.getById(entry.getKey());

                if (item != null) {
                    String page = entry.getValue().getAsString();
                    page = formatter.apply(page);
                    item.addWikiPage(page);
                    count++;
                }
            }

            plugin.getLogger()
                    .log(Level.INFO, MessageFormat.format("加載了 {0} 中 {1} 個物品的 Wiki 頁面", plugin.getName(), count));
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, MessageFormat.format("無法加載 {0} 的 wiki.json", plugin.getName()), e);
        }
    }
}
