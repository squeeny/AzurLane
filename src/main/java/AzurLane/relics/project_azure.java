package AzurLane.relics;

import AzurLane.actions.utility.azur_pickmenu;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import AzurLane.AzurLane;
import AzurLane.util.TextureLoader;

import static AzurLane.AzurLane.*;

public class project_azure extends CustomRelic implements ClickableRelic {

    public static final String ID = AzurLane.makeID(project_azure.class.getSimpleName());
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("project_azure.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("project_azure.png"));

    public project_azure() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    private boolean used = false;
    private boolean isPlayerTurn = false;

    @Override
    public void onRightClick() {

        if (this.counter == -2) {
            return; // Don't do anything.
        }

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            flash();
            //addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new StunMonsterAction(m, p, 1));
            setCounter(-2);

        }

    }

    @Override
    public void onEquip() {
        addToBot(new azur_pickmenu());

        //this.img = TextureLoader.getTexture(AzurLane.makeRelicPath(ship_curr + ship_currskin + ".png"));



    }
    public void setCounter(int setCounter) {

        if (setCounter == -2) {

            usedUp();
            this.counter = -2;
        }
    }



    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

}