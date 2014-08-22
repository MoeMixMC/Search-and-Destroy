package minefuse.commands;

import minefuse.MineFuse;
import minefuse.utils.FileLoggingUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

	public MineFuse plugin;

	public ReportCommand(MineFuse plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("report")) {
				if (args.length < 2) {
					player.sendMessage(ChatColor.RED
							+ "/report <player> <reason>");
					return false;
				}
				Player targetplayer = plugin.getServer().getPlayer(args[0]);

				if (args.length == 1) {
					if (args[0].equalsIgnoreCase(args[0])) {
						if (targetplayer == null) {
							player.sendMessage(ChatColor.RED + "The Player "
									+ ChatColor.DARK_RED + args[0]
									+ ChatColor.RED + " is not online!");
							return false;
						}
					}
				}
				String reason = args[1];
				if (args.length == 2) {
					if (args[1].equalsIgnoreCase(args[1])) {
						if (reason == null) {
							player.sendMessage(ChatColor.RED
									+ "please provide a reason!");
							return false;
						}
					}
					if (targetplayer == null) {
						player.sendMessage(ChatColor.RED + "The Player "
								+ ChatColor.DARK_RED + args[0] + ChatColor.RED
								+ " is not online!");
						return false;
					}

					for (Player staff : Bukkit.getOnlinePlayers()) {
						if (staff.hasPermission("minefuse.staff.report.recieve")) {
							staff.sendMessage(ChatColor.AQUA.toString()
									+ ChatColor.BOLD + "[REPORT] "
									+ ChatColor.WHITE + player.getDisplayName()
									+ ChatColor.GOLD + " has reported "
									+ ChatColor.WHITE + args[0]
									+ ChatColor.GOLD + " for "
									+ ChatColor.WHITE + args[1]);
							return false;
						}

						if (!(player.isOp())) {
							player.sendMessage(ChatColor.YELLOW + "[REPORT] "
									+ ChatColor.GREEN
									+ "Player Successfully reported");
							FileLoggingUtils.savePunishments(targetplayer,
									player, " reported", reason);
						}
					}
				}
			}
		} else {
			sender.sendMessage(ChatColor.RED
					+ "You need to be a player to do this command!");
		}
		return false;
	}
}