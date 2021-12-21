package ew.sr.x1c.quilt.meow.plugin.NoExplode;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.EntityControlListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.PlayerJoinQuitListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.NoDeathListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.InventoryListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.ProjectileListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.ExtraEntityListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.ChickenLayEggListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.ExplodeListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.WeatherListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.DeathListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.PlayerDamageDecrease;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.N2OListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.CropListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.EntityRideListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.PlayerHeadListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.TridentListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.KillerBunnySpawn;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.CommandOverrideListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.BeehiveListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.VillagerListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.UUIDCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.RespawnCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.GlowCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.SignCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.SuicideCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.FreeCamCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.channel.AntiWorldDownloader;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.sign.TeleportSignListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.ChatSymbolListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.mspt.MSPTHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.api.PlaceHolderHook;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.api.QuiltPlugin;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.CalculateCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.CircleEffectCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.CrashCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.DiceRoll;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.FindCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.ForceChatCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.HereCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.LineEffectCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.MeowCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.OnlineEconomy;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.PeekCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.PlaceHolderAPICommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.ServerTotalTimeCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.ShutdownCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.TestModeCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.UptimeCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.WikiCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.say.SayConsoleCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.say.SayNoPrefixCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.ChatFormatListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.CallCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.chat.ChatTagHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.BlockCountCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.CommandForwardListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.ItemIdCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.command.NoRainCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.ZombiePigManFixer;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.database.DatabaseConnection;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.file.FileHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.ConcreteListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.FlyResetListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.MobFreezer;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.NoAICommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fix.ZombiePigManListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.Harassment;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.KissListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.LoveEffectCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.PlayerCutListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.limiter.CommandLimiterListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.fun.limiter.LimiterCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.info.InformationGUI;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.AntiVoidDeathListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.EnderDragonEggSpawn;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.FastLeafDecay;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.listener.NoRemoveEntityListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.log.ServerEventLogger;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.map.MapCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.map.MapListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.mspt.MSPTCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.music.MusicPlayerCommand;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.portal.NetherGatewayPortal;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.potion.BrewManager;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.potion.PotionListener;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.score.ScoreBoardHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.tab.TabHandler;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.util.EconomyManager;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/* Developer
 * 棉被 Quilt
 * 小棉被 SmallQuilt
 * 夜宇 Blue_Joe_R
 * UFO YumekuiiNight
 *
 * Server
 * mcp.quilt.idv.tw YucoHanMeowServer (mcp.quilt.idv.tw:8123) [Survival]
 * mc.quilt.idv.tw:25567 MeowCountryModServer [Survival]
 * mc.quilt.idv.tw:25566 NewStarBuildServer (mc.quilt.idv.tw:8123) [Creative]
 *
 * New Star Server Pre Test Plugin
 * Server Core Not Include In This Project
 */
public class Main extends JavaPlugin implements QuiltPlugin {

    public static final boolean SHOW_INFO_GUI = true;
    public static final String AUTHOR_UUID = "ebe2516f-6caf-420f-a671-cb118e776fb4";

    private static Main plugin;

    private boolean usePlaceHolder;
    private MSPTHandler mspt;

    @Override
    public void onEnable() {
        getLogger().info("No Explode Plugin 正在啟用 ...");
        plugin = this;

        DatabaseConnection.setConnectionInfo("127.0.0.1", 8092, "minecraft", "hzHZaeKhYRrwoLu_LjJr", "minecraft");
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ExplodeListener(), this);
        pm.registerEvents(new PlayerJoinQuitListener(), this);
        pm.registerEvents(new PlayerDamageDecrease(), this);
        pm.registerEvents(new ProjectileListener(), this);
        pm.registerEvents(new EntityControlListener(), this);
        pm.registerEvents(new ExtraEntityListener(), this);
        pm.registerEvents(new KillerBunnySpawn(), this);
        pm.registerEvents(new VillagerListener(), this);
        pm.registerEvents(new ChickenLayEggListener(), this);
        pm.registerEvents(new TridentListener(), this);
        pm.registerEvents(new PlayerHeadListener(), this);
        pm.registerEvents(new CropListener(), this);
        pm.registerEvents(new NoDeathListener(), this);
        pm.registerEvents(new TeleportSignListener(), this);
        pm.registerEvents(new BeehiveListener(), this);
        pm.registerEvents(new N2OListener(), this);
        pm.registerEvents(new EntityRideListener(), this);
        pm.registerEvents(new InventoryListener(), this);
        pm.registerEvents(new AntiVoidDeathListener(), this);
        pm.registerEvents(new NoRemoveEntityListener(), this);
        pm.registerEvents(new EnderDragonEggSpawn(), this);
        pm.registerEvents(new FastLeafDecay(), this);
        // pm.registerEvents(new SheepColorCutFix(), this);
        // pm.registerEvents(new LeashListener(), this);

        // 1.16
        pm.registerEvents(new NetherGatewayPortal(), this);
        // pm.registerEvents(new PiglinDuplicateFix(), this); // Paper 已修補

        ServerVersionChanger.registerServerVersion();

        pm.registerEvents(new CommandForwardListener(), this);
        pm.registerEvents(new CommandOverrideListener(), this);
        TabProtect.registerTabProtect();

        DeathListener.loadDeathProtectPlayer();
        DeathListener.queryFreeDeath();
        pm.registerEvents(new DeathListener(), this);

        getCommand("respawn").setExecutor(new RespawnCommand());
        getCommand("freecam").setExecutor(new FreeCamCommand());

        getCommand("uuid").setExecutor(new UUIDCommand());
        getCommand("forcechat").setExecutor(new ForceChatCommand());

        pm.registerEvents(new ZombiePigManListener(), this);
        getCommand("fixpig").setExecutor(new ZombiePigManFixer());
        pm.registerEvents(new ConcreteListener(), this);
        pm.registerEvents(new FlyResetListener(), this);

        getCommand("uptime").setExecutor(new UptimeCommand());
        getCommand("uptimefirst").setExecutor(new ServerTotalTimeCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("sign").setExecutor(new SignCommand());
        getCommand("meow").setExecutor(new MeowCommand());
        getCommand("here").setExecutor(new HereCommand());
        getCommand("find").setExecutor(new FindCommand());
        getCommand("wiki").setExecutor(new WikiCommand());
        getCommand("shutdown").setExecutor(new ShutdownCommand());
        getCommand("placeholder").setExecutor(new PlaceHolderAPICommand());
        getCommand("blockcount").setExecutor(new BlockCountCommand());
        getCommand("armorhide").setExecutor(new ArmorHidePacketHandler());
        getCommand("findid").setExecutor(new ItemIdCommand());
        getCommand("crash").setExecutor(new CrashCommand());

        getCommand("say").setExecutor(new SayNoPrefixCommand());
        getCommand("sayconsole").setExecutor(new SayConsoleCommand());

        pm.registerEvents(new CommandLimiterListener(), this);
        getCommand("limiter").setExecutor(new LimiterCommand());

        ChatSymbolListener chatSymbol = new ChatSymbolListener();
        pm.registerEvents(chatSymbol, this);
        getCommand("prefix").setExecutor(chatSymbol);
        getCommand("call").setExecutor(new CallCommand());

        ChatTagHandler chatTag = new ChatTagHandler();
        pm.registerEvents(chatTag, this);
        getCommand("tag").setExecutor(chatTag);

        getCommand("circle").setExecutor(new CircleEffectCommand());
        getCommand("line").setExecutor(new LineEffectCommand());

        FileHandler.init();

        MusicPlayerCommand player = new MusicPlayerCommand();
        pm.registerEvents(player, this);
        getCommand("music").setExecutor(player);

        pm.registerEvents(new MapListener(), this);
        // MapListener.loadMap();
        MapCommand icon = new MapCommand();
        getCommand("icon").setExecutor(icon);
        getCommand("icon").setTabCompleter(icon);

        GlowCommand.registerGlow();
        getCommand("glow").setExecutor(new GlowCommand());

        PeekCommand peek = new PeekCommand();
        pm.registerEvents(peek, this);
        getCommand("peek").setExecutor(peek);

        pm.registerEvents(new MobFreezer(), this);
        getCommand("noai").setExecutor(new NoAICommand());
        // PigZombieTowerLocation.registerAutomaticFix();

        pm.registerEvents(new WeatherListener(), this);
        WeatherListener.registerWeatherTimer();
        NoRainCommand noRain = new NoRainCommand();
        pm.registerEvents(noRain, this);
        getCommand("norain").setExecutor(noRain);

        RecipeManager.initRecipe();
        pm.registerEvents(new PotionListener(), this);
        BrewManager.loadRecipe();

        DiceRoll dice = new DiceRoll();
        pm.registerEvents(dice, this);
        getCommand("dice").setExecutor(dice);

        Harassment harassment = new Harassment();
        pm.registerEvents(harassment, this);
        getCommand("niko").setExecutor(harassment);
        pm.registerEvents(new PlayerCutListener(), this);
        pm.registerEvents(new KissListener(), this);
        getCommand("love").setExecutor(new LoveEffectCommand());

        mspt = new MSPTHandler();
        getCommand("mspt").setExecutor(new MSPTCommand());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new PlaceHolderHook().register();
            pm.registerEvents(new ChatFormatListener(), this);
            TabHandler.registerTabTimer();
            usePlaceHolder = true;
        } else {
            getLogger().info("Place Holder API 尚未安裝 不進行掛鉤");
        }

        getServer().getMessenger().registerIncomingPluginChannel(this, "wdl:init", new AntiWorldDownloader());
        getServer().getMessenger().registerOutgoingPluginChannel(this, "wdl:control");

        EconomyManager.setupEconomy();
        if (EconomyManager.getEconomy() != null) {
            OnlineEconomy.registerOnlineTimer();
        }

        if (Bukkit.getPluginManager().isPluginEnabled("QuiltCalculatorAPI")) {
            getCommand("calculate").setExecutor(new CalculateCommand());
        } else {
            getLogger().log(Level.INFO, "計算機功能無法載入 請檢查是否安裝對應函式庫");
        }

        /* [MSGCLR] 訊息收回功能 時間複雜度較大就沒有載入了 (其實就是刷掉後重送)
           MessageProtocol.registerMessagePacket();
           pm.registerEvents(new MessageClear(), this); */
        FireworkEffect.registerFirework();
        /* [Console Hook] 主控台訊息擷取
           LoggerHook.hookLogger();
           pm.registerEvents(new ConsoleListener(), this); */

        ServerEventLogger.registerIPChangeLog();
        ServerEventLogger.registerShutdownLog();
        // ServerEventLogger.registerServerStatusLogger();

        getCommand("notice").setExecutor(new NoticeBroadcast());
        NoticeBroadcast.registerNoticeTimer();

        if (SHOW_INFO_GUI && Bukkit.getPluginManager().isPluginEnabled("JFreeChartAPI")) {
            InformationGUI.initLookAndFeel();
            InformationGUI gui = new InformationGUI();
            gui.registerUpdateTask();
            gui.setVisible(true);
        } else {
            getLogger().log(Level.INFO, "伺服器狀態 GUI 未載入 請檢查是否安裝對應函式庫與是否開啟功能");
        }

        ScoreBoardHandler score = new ScoreBoardHandler();
        getCommand("death").setExecutor(score);
        score.registerScoreBoardTask();
        getCommand("testmode").setExecutor(new TestModeCommand());

        getLogger().info("喵嗚 :3 ~");
    }

    @Override
    public void onDisable() {
        getLogger().info("正在關閉插件 ...");
        getServer().resetRecipes();

        plugin = null;
        super.onDisable();
    }

    public static Main getPlugin() {
        return plugin;
    }

    public MSPTHandler getMSPT() {
        return mspt;
    }

    public boolean isUsePlaceHolder() {
        return usePlaceHolder;
    }
}
