package ew.sr.x1c.quilt.meow.plugin.NoExplode.api;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderHook extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (identifier.equals("server_mspt")) {
            return Main.getPlugin().getMSPT().getMSPTPlaceHolder();
        }

        if (player != null) {
            if (identifier.equals("player_world_time_12")) { // 原版的莫名其妙有 BUG
                String raw = PlaceholderAPI.setPlaceholders(player, "%player_world_time_24%");
                StringBuilder sb = new StringBuilder();

                String[] split = raw.split(":");
                int hour = Integer.parseInt(split[0]);
                if (hour < 12) {
                    sb.append("AM ");
                } else {
                    sb.append("PM ");
                }

                if (hour == 0) {
                    hour = 12;
                } else if (hour > 12) {
                    hour -= 12;
                }

                if (hour < 10) {
                    sb.append("0");
                }
                sb.append(hour).append(raw.substring(2));
                return sb.toString();
            }
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return "quilt";
    }

    @Override
    public String getAuthor() {
        return "EW_Quilt";
    }

    @Override
    public String getVersion() {
        return "0.02";
    }
}
