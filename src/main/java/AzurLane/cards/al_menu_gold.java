package AzurLane.cards;

import AzurLane.AzurLane;
import AzurLane.relics.project_azure;
import basemod.AutoAdd;
import com.megacrit.cardcrawl.blights.TimeMaze;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.VelvetChoker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static AzurLane.AzurLane.*;
import static AzurLane.AzurLane.ship_curr;

@AutoAdd.Ignore
public class al_menu_gold extends abs_al_core{

    public static final String ID = AzurLane.makeID(al_shipselect.class.getSimpleName());
    public static final String IMG = makeCardPath("null.png");
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.SPECIAL;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.NONE;
    private static final AbstractCard.CardType TYPE = CardType.STATUS;
    public static final AbstractCard.CardColor COLOR = AbstractCard.CardColor.COLORLESS;

    public static final Logger logger = LogManager.getLogger(al_shipselect.class.getName());

    public String state;
    public int amount;


    public al_menu_gold(String state, int amount) {
        super(ID, IMG, -2, TYPE, COLOR, RARITY, TARGET);

        this.name = this.state + " " + this.amount;

        switch (state){

            case "Deposit":
                this.name = this.state + " " + this.amount;
                this.rawDescription = state + " " + amount + "% of your total gold. NL azurlane:investable";
                break;

            case "Withdraw":
                this.name = this.state + " " + this.amount;
                this.rawDescription = this.state + " " + this.amount + "% of your total gold.";
                break;

            case "Interest":
                break;

            default:

        }

        initializeDescription();


    }

    @Override
    public float getTitleFontSize()
    {
        return 12;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  }


    @Override
    public void upgrade() {
    }


}
