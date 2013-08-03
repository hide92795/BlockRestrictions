package hide92795.bukkit.plugin.blockrestrictions.listener;

import hide92795.bukkit.plugin.blockrestrictions.BlockRestrictions;
import hide92795.bukkit.plugin.blockrestrictions.Type;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
	private BlockRestrictions plugin;
	private Logger logger;

	public BlockPlaceListener(BlockRestrictions instance) {
		plugin = instance;
		logger = plugin.getLogger();
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (plugin.isEnabled()) {
			Player player = event.getPlayer();
			Block block = event.getBlockPlaced();
			if (player.isOp()) {
				return;
			}

			if (block.getType() == Material.TNT) {
				if (!player.hasPermission("blockrestrictions.place.tnt")) {
					String message = String.format(plugin.localize.getString(Type.PLACE_BANED_BLOCK), "TNT");
					player.sendMessage(message);
					StringBuilder consoleMessage = new StringBuilder();
					consoleMessage.append("Player ");
					consoleMessage.append(player.getName());
					consoleMessage.append("placed TNT at ");
					consoleMessage.append(block.getX());
					consoleMessage.append(", ");
					consoleMessage.append(block.getY());
					consoleMessage.append(", ");
					consoleMessage.append(block.getZ());
					consoleMessage.append(" in world ");
					consoleMessage.append(block.getWorld());
					consoleMessage.append("!");
					logger.warning(consoleMessage.toString());
					event.setCancelled(true);
				}
			}

			if (block.getType() == Material.BEDROCK)
				if (!player.hasPermission("blockrestrictions.place.bedrock")) {
					String message = String.format(plugin.localize.getString(Type.PLACE_BANED_BLOCK), "BedRock");
					player.sendMessage(message);
					StringBuilder consoleMessage = new StringBuilder();
					consoleMessage.append("Player ");
					consoleMessage.append(player.getName());
					consoleMessage.append("placed BedRock at ");
					consoleMessage.append(block.getX());
					consoleMessage.append(", ");
					consoleMessage.append(block.getY());
					consoleMessage.append(", ");
					consoleMessage.append(block.getZ());
					consoleMessage.append(" in world ");
					consoleMessage.append(block.getWorld());
					consoleMessage.append("!");
					logger.warning(consoleMessage.toString());
					event.setCancelled(true);
				}
		}
	}
}