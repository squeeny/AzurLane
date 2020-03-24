package AzurLane.actions.utility;

import AzurLane.AzurLane;
import AzurLane.cards.al_ship;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static AzurLane.AzurLane.makeCardPath;

public class azur_pickmenu extends AbstractGameAction {

    public ArrayList<AbstractCard> stanceChoices = new ArrayList<>();

    public azur_pickmenu() {
        this.actionType = ActionType.WAIT;
    }

    public void update() {

        File uri = null;
        String current_shipgirl;
        uri = Gdx.files.internal("/AzurLaneResources/images/cards/shipgirls.txt").file();

        try {
            BufferedReader shipgirlscanner = new BufferedReader(new FileReader(uri));
            while((current_shipgirl = shipgirlscanner.readLine()) != null){
                stanceChoices.add(new al_ship(current_shipgirl));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        addToTop(new ChooseOneAction(stanceChoices));

        this.isDone = true;
    }


}