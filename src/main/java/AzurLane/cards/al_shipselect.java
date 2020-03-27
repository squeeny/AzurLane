package AzurLane.cards;

import AzurLane.AzurLane;

import AzurLane.relics.project_azure;
import basemod.AutoAdd;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static AzurLane.AzurLane.*;

@AutoAdd.Ignore
public class al_shipselect extends abs_al_core {

    public static final String ID = AzurLane.makeID(al_shipselect.class.getSimpleName());
    public static final String IMG = makeCardPath("null.png");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;
    public String Ship;
    public static final Logger logger = LogManager.getLogger(al_shipselect.class.getName());


    public al_shipselect(String index, String name, String desc) {
        super(ID, IMG, -2, TYPE, COLOR, RARITY, TARGET);
        Ship = index;
        this.name = name;
        this.rawDescription = desc;
        this.getTitleFontSize();
        loadCardImage("AzurLaneResources/images/cards/" + index + ".png");
        initializeDescription();

    }

    @Override
    public float getTitleFontSize()
    {
        return 16;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        logger.info("SHIP SELECTED: " + Ship);
        ship_curr = Ship;
        ship_currskin = "default";

        try{
            AzurLane.saveData();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain((Settings.WIDTH / 2), (Settings.HEIGHT / 2), new project_azure(ship_curr));

    }


    @Override
    public void upgrade() {
    }


}