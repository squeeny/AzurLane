package AzurLane;

import AzurLane.actions.utility.al_shipselectAction;
import AzurLane.relics.*;

import basemod.*;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import AzurLane.util.IDCheckDontTouchPls;
import AzurLane.util.TextureLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class AzurLane implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber,
        StartActSubscriber,
        PostUpdateSubscriber,
        AddAudioSubscriber{

    public static final Logger logger = LogManager.getLogger(AzurLane.class.getName());
    private static String modID;

    // AzurLane Saved Data

    // Ship Info
    public static String ship_curr = "none";
    public static boolean ship_locked = false;

    public static String ship_module = "none";

    public static boolean[]ship_skill = new boolean[]{false,false,false};
    public static int[]ship_skill_levels = new int[]{0,0,0};

    public static int ship_affinity = 0;
    public static boolean ship_oath = false;

    public static int ship_gold = 0;
    public static float ship_interest = 0;

    public static boolean[] ship_skins = new boolean[]{};
    public static String ship_currskin = "default";

    public static Properties AzurLaneDefaultSettings = new Properties();

    private static final String MODNAME = "Project Azur";
    private static final String AUTHOR = "squeeny";
    private static final String DESCRIPTION = "Your own personal shipgirl, please take good care of her!";
    public static final String BADGE_IMAGE = "AzurLaneResources/images/Badge.png";

    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }

    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }

    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeEffectPath(String resourcePath) {
        return getModID() + "Resources/images/effects/" + resourcePath;
    }

    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/" + resourcePath;
    }

    public AzurLane() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("AzurLane");
        logger.info("Done subscribing");

        logger.info("Done adding mod settings");
    }

    public static void setModID(String ID) {
        Gson coolG = new Gson();
        InputStream in = AzurLane.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }

    public static String getModID() {
        return modID;
    }

    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = AzurLane.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = AzurLane.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing AzurLane  =========================");
        AzurLane azurlane = new AzurLane();
        logger.info("========================= a fleeting dream =========================");

        try {

            int i;
            int j;

            logger.info("Setting default values.");

            AzurLaneDefaultSettings.setProperty("ship_curr", "none");
            AzurLaneDefaultSettings.setProperty("ship_locked", "false");
            AzurLaneDefaultSettings.setProperty("ship_module", "none");

            for (i = 0; i < ship_skill.length; i++) {
                AzurLaneDefaultSettings.setProperty("ship_skill" + i, "false");
            }
            for (i = 0; i < ship_skill_levels.length; i++) {
                AzurLaneDefaultSettings.setProperty("ship_skill_levels" + i, "0");
            }

            AzurLaneDefaultSettings.setProperty("ship_affinity", "0");
            AzurLaneDefaultSettings.setProperty("ship_oath", "false");

            AzurLaneDefaultSettings.setProperty("ship_gold", "0");
            AzurLaneDefaultSettings.setProperty("ship_interest", "0.0");

            try{
                for (i = 0; i < ship_skins.length; i++) {
                    AzurLaneDefaultSettings.setProperty("ship_skins" + i, "false");
                }
            }
            catch (Exception e)
            {

            }

            AzurLaneDefaultSettings.setProperty("ship_currskin", "default");

            SpireConfig config = new SpireConfig("azurlane", "AzurLaneConfig", AzurLaneDefaultSettings);

            ship_curr = config.getString("ship_curr");
            ship_locked = config.getBool("ship_locked");
            ship_module = config.getString("ship_module");

            for (j = 0; j < ship_skill.length; j++) {
                ship_skill[j] = config.getBool("ship_skill" + j);
            }
            for (j = 0; j < ship_skill_levels.length; j++) {
                ship_skill_levels[j] = config.getInt("ship_skill_levels" + j);
            }

            ship_affinity = config.getInt("ship_affinity");
            ship_oath = config.getBool("ship_oath");

            ship_gold = config.getInt("ship_gold");
            ship_interest = config.getFloat("ship_interest");

            try{
                for (j = 0; j < ship_skins.length; j++) {
                    ship_skins[j] = config.getBool("ship_skins" + j);
                }
            }
            catch (Exception e)
            {

            }
            ship_currskin = config.getString("ship_currskin");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void receivePostInitialize() {

        logger.info("Loading badge image and mod options");
        try {
            CreatePanel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            AzurLane.saveData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // oneshot
        BaseMod.addRelic(new project_azure(), RelicType.SHARED);

        logger.info("Done adding relics!");
    }

    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();
        logger.info("Add variabls");

        logger.info("Adding cards");
        new AutoAdd(modID)
                .packageFilter("AzurLane.cards")
                .setDefaultSeen(true)
                .cards();

        logger.info("Done adding cards!");
    }

    public static String fetchLanguage()
    {
        return Settings.language.name().toLowerCase();
    }

    @Override
    public void receiveEditStrings() {

        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        String language = fetchLanguage();

        try
        {
            BaseMod.loadCustomStringsFile(CardStrings.class,
                    getModID() + "Resources/localization/" + language + "/AzurLane-Card-Strings.json");

            BaseMod.loadCustomStringsFile(PowerStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Power-Strings.json");

            BaseMod.loadCustomStringsFile(RelicStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Relic-Strings.json");

            BaseMod.loadCustomStringsFile(EventStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Event-Strings.json");

            BaseMod.loadCustomStringsFile(UIStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Ui-Strings.json");
        }
        catch (Exception e)
        {
            logger.info("[AL] Couldn't find localization for language:" + language);
            language = "eng";
            logger.info("[AL] Defaulting to eng");

            BaseMod.loadCustomStringsFile(CardStrings.class,
                    getModID() + "Resources/localization/" + language + "/AzurLane-Card-Strings.json");

            BaseMod.loadCustomStringsFile(PowerStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Power-Strings.json");

            BaseMod.loadCustomStringsFile(RelicStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Relic-Strings.json");

            BaseMod.loadCustomStringsFile(EventStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Event-Strings.json");

            BaseMod.loadCustomStringsFile(UIStrings.class,
                    getModID() + "Resources/localization/"  + language + "/AzurLane-Ui-Strings.json");

        }

        logger.info("[AL] Strings are done.");
    }

    @Override
    public void receiveEditKeywords() {

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/AzurLane-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    @Override
    public void receivePostUpdate() {
        if (AbstractDungeon.player == null)
            return;
    }


    public void receiveOnBattleStart(AbstractRoom room) {
        if(ship_curr.equals("none")){
            logger.info("[AL] No Shipgirl");
            AbstractDungeon.actionManager.addToBottom(new al_shipselectAction());
        }
        else{
            logger.info("[AL] Has Shipgirl: " + ship_curr);
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new project_azure(ship_curr));
        }

    }


    private void CreatePanel() throws IOException {
        SpireConfig spireConfig = new SpireConfig("azurlane", "AzurLaneConfig");
        ModPanel settingsPanel = new ModPanel();
        UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("CreatePanelMokou"));
        String[] TEXT = uiStrings.TEXT;
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);


    }

    public static void saveData() throws IOException {
        logger.info("[AL] Saving a lot of data.... ");
        SpireConfig config = new SpireConfig("azurlane", "AzurLaneConfig");
        int j;

        config.setString("ship_curr", ship_curr);
        config.setBool("ship_locked", ship_locked);
        config.setString("ship_module", ship_module);

        for (j = 0; j < ship_skill.length; j++) {
            config.setBool("ship_skill" + j, ship_skill[j]);
        }
        for (j = 0; j < ship_skill_levels.length; j++) {
            config.setInt("ship_skill_levels" + j, ship_skill_levels[j]);
        }

        config.setInt("ship_affinity", ship_affinity);
        config.setBool("ship_oath", ship_oath);

        config.setInt("ship_gold", ship_gold);
        config.setFloat("ship_interest", ship_interest);

        try{
            for (j = 0; j < ship_skins.length; j++) {
                config.setBool("ship_skins" + j, ship_skins[j]);
            }
        }
        catch (Exception e)
        {

        }

        config.setString("ship_currskin", ship_currskin);

        config.save();
        logger.info("[AL] Done!");
    }

    public static void resetData() throws IOException {
        logger.info("[AL] resetting data.... ");

        try {

            int i;
            int j;

            logger.info("Setting default values.");

            AzurLaneDefaultSettings.setProperty("ship_curr", "none");
            AzurLaneDefaultSettings.setProperty("ship_locked", "false");
            AzurLaneDefaultSettings.setProperty("ship_module", "none");

            for (i = 0; i < ship_skill.length; i++) {
                AzurLaneDefaultSettings.setProperty("ship_skill" + i, "false");
            }
            for (i = 0; i < ship_skill_levels.length; i++) {
                AzurLaneDefaultSettings.setProperty("ship_skill_levels" + i, "0");
            }

            AzurLaneDefaultSettings.setProperty("ship_affinity", "0");
            AzurLaneDefaultSettings.setProperty("ship_oath", "false");

            AzurLaneDefaultSettings.setProperty("ship_gold", "0");
            AzurLaneDefaultSettings.setProperty("ship_interest", "0.0");

            try{
                for (i = 0; i < ship_skins.length; i++) {
                    AzurLaneDefaultSettings.setProperty("ship_skins" + i, "false");
                }
            }
            catch (Exception e)
            {

            }

            AzurLaneDefaultSettings.setProperty("ship_currskin", "default");

            SpireConfig config = new SpireConfig("azurlane", "AzurLaneConfig", AzurLaneDefaultSettings);

            ship_curr = config.getString("ship_curr");
            ship_locked = config.getBool("ship_locked");
            ship_module = config.getString("ship_module");

            for (j = 0; j < ship_skill.length; j++) {
                ship_skill[j] = config.getBool("ship_skill" + j);
            }
            for (j = 0; j < ship_skill_levels.length; j++) {
                ship_skill_levels[j] = config.getInt("ship_skill_levels" + j);
            }

            ship_affinity = config.getInt("ship_affinity");
            ship_oath = config.getBool("ship_oath");

            ship_gold = config.getInt("ship_gold");
            ship_interest = config.getFloat("ship_interest");

            try{
                for (j = 0; j < ship_skins.length; j++) {
                    ship_skins[j] = config.getBool("ship_skins" + j);
                }
            }
            catch (Exception e)
            {

            }
            ship_currskin = config.getString("ship_currskin");

            logger.info("[AL] Done!");



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void receiveStartAct() {

    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio("AzurLane:Poem", makeAudioPath("pageflip.ogg") );

    }
}
