package AzurLane.cards;

import AzurLane.AzurLane;

import basemod.AutoAdd;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static AzurLane.AzurLane.*;

@AutoAdd.Ignore
public class al_ship extends abs_al {

    public static final String ID = AzurLane.makeID(al_ship.class.getSimpleName());
    public static final String IMG = makeCardPath("null.png");
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = CardColor.COLORLESS;
    public static String Ship;

    public al_ship(String shipgirl) {
        super(ID, IMG, -2, TYPE, COLOR, RARITY, TARGET);
        Ship = shipgirl;
        this.name = shipgirl;
        loadCardImage(shipgirl +".png");

    }

    public void onChoseThisOption() {

        ship_curr = Ship;
        ship_currskin = "default";

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) { onChoseThisOption(); }


    @Override
    public void upgrade() {
    }


}