package city.norain.slimefun4.compatibillty;

import city.norain.slimefun4.SlimefunExtended;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;

@UtilityClass
public class CompatibilityUtil {
    /**
     * 獲取玩家放置此方塊所使用的物品材質。
     * 對於大多數方塊，這與 getMaterial() 相同，但有些方塊有不同的材質用於放置它們。
     * 注意：此處沒有涵蓋所有可能不同的方塊數據類型。
     *
     * @param blockData
     * @return 放置此方塊所使用的材質
     */
    public Material getPlacementMaterial(BlockData blockData) {
        if (SlimefunExtended.isAtLeast(1, 19, 4)) {
            return blockData.getPlacementMaterial();
        } else {
            switch (blockData.getMaterial()) {
                case PLAYER_WALL_HEAD -> {
                    return Material.PLAYER_HEAD;
                }
                case REDSTONE_WIRE -> {
                    return Material.REDSTONE;
                }
                default -> {
                    var mat = blockData.getMaterial();
                    var enumName = blockData.getMaterial().name();

                    if (Ageable.class.equals(mat.data) && enumName.endsWith("S")) {
                        var itemMat = Material.getMaterial(enumName.substring(0, enumName.length() - 1));
                        return itemMat != null && itemMat.isItem() ? itemMat : mat;
                    }

                    if (WallSign.class.equals(mat.data) && enumName.contains("_WALL_")) {
                        Material itemMat = Material.getMaterial(enumName.replace("_WALL_", "_"));

                        if (itemMat != null && itemMat.isItem()) {
                            return mat;
                        }
                    }

                    // Fallback to original material
                    return blockData.getMaterial();
                }
            }
        }
    }

    /**
     * 檢查玩家是否處於連接狀態。
     * 在 1.20- 中不能保證玩家是否連接，僅返回在線狀態。
     *
     * @param player 離線玩家
     * @return 玩家連接或在線
     */
    public boolean isConnected(OfflinePlayer player) {
        if (SlimefunExtended.isAtLeast(1, 20) && Slimefun.instance().getServer().getOnlineMode()) {
            return player.isConnected();
        } else {
            return player.isOnline();
        }
    }
}
