package hide92795.bukkit.plugin.blockrestrictions;

import hide92795.bukkit.plugin.corelib.Localizable;

public enum Type implements Localizable {
	RELOADED_SETTING("ReloadedSetting"), ERROR_RELOAD_SETTING("ErrorReloadSetting"), PLACE_BANED_BLOCK("PlaceBanedBlock"), DETONATE_TNT("DetonateTNT");
	private final String type;

	private Type(String type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return type;
	}
}
