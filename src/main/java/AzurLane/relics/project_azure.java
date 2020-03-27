package AzurLane.relics;

import AzurLane.cards.al_shipselect;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import AzurLane.AzurLane;
import AzurLane.util.TextureLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static AzurLane.AzurLane.*;

public class project_azure extends CustomRelic implements ClickableRelic {

    public static final String ID = AzurLane.makeID(project_azure.class.getSimpleName());
    public static Texture IMG = TextureLoader.getTexture(makeRelicPath("null.png"));
    public static Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("null.png"));
    public static final Logger logger = LogManager.getLogger(al_shipselect.class.getName());


    public project_azure(String ship_curr) {
        super(ID, fetchShipGirl(true, ship_curr), fetchShipGirl(false, ship_curr), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public project_azure() {
        super(ID, IMG, OUTLINE, RelicTier.DEPRECATED, LandingSound.MAGICAL);
    }

    @Override
    public void onRightClick() {

        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            flash();
            //addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractMonster m = AbstractDungeon.getRandomMonster();
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new StunMonsterAction(m, p, 1));
            setCounter(-2);

        }

    }

    public static void dummy(){

    }

    public static Texture fetchShipGirl(boolean relic_or_outline, String ship_curr){

        switch (ship_curr) {

            case "none":

                AzurLane.logger.info("AL : Null texture");
                if (relic_or_outline) { return TextureLoader.getTexture(makeRelicPath("null.png"));
                } else { return TextureLoader.getTexture(makeRelicOutlinePath("null.png")); }

            default: {

                switch (ship_currskin) {

                    case "default":

                        if (relic_or_outline) {
                            return TextureLoader.getTexture(makeRelicPath(ship_curr + ".png"));
                        } else {
                            return TextureLoader.getTexture(makeRelicOutlinePath(ship_curr + ".png"));
                        }

                    default:
                        if (relic_or_outline) {
                            return TextureLoader.getTexture(makeRelicPath(ship_curr + ship_currskin + ".png"));
                        } else {
                            return TextureLoader.getTexture(makeRelicOutlinePath(ship_curr + ship_currskin + ".png"));
                        }

                }

            }
        }
    }

    @Override
    public void onEquip() {

    }

    @Override
    public String getUpdatedDescription() {

        return DESCRIPTIONS[0];
    }

}