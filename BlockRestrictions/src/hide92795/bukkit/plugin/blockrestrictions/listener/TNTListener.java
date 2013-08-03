package hide92795.bukkit.plugin.blockrestrictions.listener;

import hide92795.bukkit.plugin.blockrestrictions.BlockRestrictions;
import hide92795.bukkit.plugin.blockrestrictions.Type;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
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
		if (plugin.isEnabled()) {
			if (event.getEntity() instanceof TNTPrimed) {
				Location location = event.getLocation();
				Player[] players = plugin.getServer().getOnlinePlayers();
				for (Player player : players) {
					if (player.isOp()) {
						continue;
					}
					if (isPlayerDetonate(player, location)) {
						if (!player.hasPermission("blockrestrictions.canexplode")) {
							player.sendMessage(plugin.localize.getString(Type.DETONATE_TNT));
							event.setCancelled(true);
						}
					}
				}
			}
		}
	}

	private boolean isPlayerDetonate(Player player, Location location) {
		if (player.getLocation().getX() > location.getX() + 20.0d) {
			return false;
		}
		if (player.getLocation().getY() > location.getY() + 20.0d) {
			return false;
		}
		if (player.getLocation().getY() > location.getZ() + 20.0d) {
			return false;
		}
		if (player.getLocation().getY() > location.getZ() + 20.0d) {
			return false;
		}
		return true;
	}
}