package hide92795.bukkit.plugin.blockrestrictions.listener;

import hide92795.bukkit.plugin.blockrestrictions.BlockRestrictions;
import hide92795.bukkit.plugin.blockrestrictions.Type;
import java.util.ArrayList;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNTListener implements Listener {
	public BlockRestrictions plugin;

	public TNTListener(BlockRestrictions instance) {
		plugin = instance;
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.getEntity() instanceof TNTPrimed || event.getEntity() instanceof ExplosiveMinecart) {
			Location location = event.getLocation();
			Player[] players = plugin.getServer().getOnlinePlayers();
			boolean hasAllowedPlayer = false;
			ArrayList<Player> inRangePlayers = new ArrayList<>();

			for (Player player : players) {
				if (isPlayerDetonate(player, location)) {
					if (player.hasPermission("blockrestrictions.detonate.tnt") || player.isOp()) {
						hasAllowedPlayer = true;
						break;
					}
					inRangePlayers.add(player);
				}
			}

			if (!hasAllowedPlayer) {
				for (Player player : inRangePlayers) {
					player.sendMessage(plugin.localize.getString(Type.DETONATE_TNT));
				}
				event.setCancelled(true);
			}
		}
	}

	private boolean isInRange(int x, int i, int j) {
		if (i >= j) {
			if (i >= x && x >= j) {
				return true;
			}
		} else {
			if (i < x && x < j) {
				return true;
			}
		}
		return false;
	}

	private boolean isPlayerInArea(Player player, int[] pos1ia, int[] pos2ia) {
		int x = player.getLocation().getBlockX();
		int y = player.getLocation().getBlockY();
		int z = player.getLocation().getBlockZ();

		if (!isInRange(x, pos1ia[0], pos2ia[0])) {
			return false;
		}
		if (!isInRange(y, pos1ia[1], pos2ia[1])) {
			return false;
		}
		if (!isInRange(z, pos1ia[2], pos2ia[2])) {
			return false;
		}
		return true;
	}


	private boolean isPlayerDetonate(Player player, Location location) {
		if (player.getWorld().getName().equals(location.getWorld().getName())) {
			int[] pos1 = { location.getBlockX() + 10, location.getBlockY() + 10, location.getBlockZ() + 10 };
			int[] pos2 = { location.getBlockX() - 10, location.getBlockY() - 10, location.getBlockZ() - 10 };
			return isPlayerInArea(player, pos1, pos2);
		}
		return false;
	}
}