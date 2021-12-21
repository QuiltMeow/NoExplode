package ew.sr.x1c.quilt.meow.plugin.NoExplode.base;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ew.sci.calculator.parser.Expression;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.ArgumentUtil;

// 本功能需使用 QuiltCalculatorAPI
public class CalculateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.GREEN + "--- Quilt Fully Functional Calculator ---");
            sender.sendMessage(ChatColor.GREEN + "(其實只是棉被太懶得開小算盤的無聊產物)");
            sender.sendMessage(ChatColor.RED + "指令使用方法 : /calculate [任意四則計算式]");
            return false;
        }
        String eval = ArgumentUtil.joinStringFrom(args, 0);
        sender.sendMessage(ChatColor.BLUE + "輸入計算式 : " + ChatColor.GREEN + eval);
        Expression expression = new Expression(eval);

        double result = expression.calculate();
        if (Double.isNaN(result)) {
            sender.sendMessage(ChatColor.RED + "計算失敗 請檢查計算式是否符合數學規範");
            return false;
        }

        if (Double.isInfinite(result)) {
            sender.sendMessage(ChatColor.GREEN + "計算結果 : 無限大 (或者太大了)");
            return true;
        }

        DecimalFormat df = new DecimalFormat("0.000000");
        df.setRoundingMode(RoundingMode.HALF_UP);
        double format = Double.parseDouble(df.format(result));

        long convert = (long) format;
        if (format == convert) {
            sender.sendMessage(ChatColor.GREEN + "計算結果 : " + convert);
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "計算結果 : " + format);
        return true;
    }
}
