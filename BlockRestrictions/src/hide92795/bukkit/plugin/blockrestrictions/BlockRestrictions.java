package hide92795.bukkit.plugin.blockrestrictions;

import hide92795.bukkit.plugin.blockrestrictions.listener.BlockPlaceListener;
import hide92795.bukkit.plugin.blockrestrictions.listener.TNTListener;
import hide92795.bukkit.plugin.corelib.Localize;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockRestrictions extends JavaPlugin {
	public Localize localize;
	private Logger logger;

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
		logger = getLogger();
		localize = new Localize(this);
		try {
			reload();
		} catch (Exception e1) {
			logger.severe("Error has occured on loading config.");
		}
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockPlaceListener(this), this);
		pm.registerEvents(new TNTListener(this), this);
		logger.info("BlockRestrictions enabled!");
	}

	public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
		switch (command.getName().toLowerCase()) {
		case "blockrestrictions-reload":
			try {
				reload();
				sender.sendMessage(localize.getString(Type.RELOADED_SETTING));
				logger.info("Reloaded successfully.");
			} catch (Exception e) {
				sender.sendMessage(localize.getString(Type.ERROR_RELOAD_SETTING));
			}
			break;
		default:
			break;
		}
		return true;
	}

	private void reload() throws Exception {
		reloadConfig();
		try {
			localize.reload(getConfig().getString("Language"));
		} catch (Exception e1) {
			logger.severe("Can't load language file.");
			try {
				localize.reload("jp");
				logger.severe("Loaded default language file.");
			} catch (Exception e) {
				throw e;
			}
		}
	}
}
