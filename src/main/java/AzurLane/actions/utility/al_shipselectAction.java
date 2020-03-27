package AzurLane.actions.utility;

import AzurLane.AzurLane;
import AzurLane.cards.al_shipselect;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

import static AzurLane.AzurLane.getModID;
import static com.megacrit.cardcrawl.events.city.TheLibrary.OPTIONS;

public class al_shipselectAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(al_shipselectAction.class.getName());
    //public ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
    public CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    public boolean once = true;

    // indexing

    public al_shipselectAction() {
        this.actionType = ActionType.WAIT;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    public void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect(String a, String b, String c) throws IOException {
        BufferedWriter jsonmaker = new BufferedWriter(Gdx.files.internal(getModID() + "Resources/localization/uni/sgindex.txt").writer(true));
        jsonmaker.write(a + " " + b + " " + c );
    }

    public void update() {

        if (once) {
            String current_sg_index;
            String current_sg_name;
            String current_sg_desc;
            String language = AzurLane.fetchLanguage();

            BufferedReader sg_indexes;
            BufferedReader sg_names;
            BufferedReader sg_descs;

            sg_indexes = new BufferedReader(Gdx.files.internal(getModID() + "Resources/localization/uni/sgindex.txt").reader());
            try {
                sg_names = new BufferedReader(Gdx.files.internal(getModID() + "Resources/localization/" + language + "/sgname.txt").reader());
                sg_descs = new BufferedReader(Gdx.files.internal(getModID() + "Resources/localization/" + language + "/sgdesc.txt").reader());
            } catch (Exception e) {
                logger.info("[AL] Couldn't find localization [Ship Files] for language:" + language);
                language = "eng";
                logger.info("[AL] Defaulting to eng");
                sg_names = new BufferedReader(Gdx.files.internal(getModID() + "Resources/localization/" + language + "/sgname.txt").reader());
                sg_descs = new BufferedReader(Gdx.files.internal(getModID() + "Resources/localization/" + language + "/sgdesc.txt").reader());
            }

            try {
                while ((current_sg_index = sg_indexes.readLine()) != null && (current_sg_name = sg_names.readLine()) != null && (current_sg_desc = sg_descs.readLine()) != null) {
                    logger.info(current_sg_index + " " + current_sg_name + " " + current_sg_desc);
                    AbstractCard c = new al_shipselect(current_sg_index, current_sg_name, current_sg_desc);
                    group.addToTop(c);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            AbstractDungeon.gridSelectScreen.open(group, 1, OPTIONS[4], false);
            once = false;
        }


        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {

                c.freeToPlayOnce = true;
                c.purgeOnUse = true;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(c, null));
            }

            this.isDone = true;

        }

        tickDuration();
    }


}