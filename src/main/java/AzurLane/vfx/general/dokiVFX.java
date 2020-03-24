package AzurLane.vfx.general;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import AzurLane.AzurLane;

public class dokiVFX extends AbstractGameEffect {

    public static String MKU;
    private static Texture text_region, MKUimage;

    private int MKUwidth, MKUheight;

    private float MKUX, MKUY;
    private float timer;
    private final float timePerPhase;
    private final float targetAlpha;

    private int step;

    public dokiVFX(int num) {

        MKU = AzurLane.makeEffectPath("doki" + num +".png");
        MKUimage = new Texture(MKU);
        this.scale *= 1.3F;

        MKUwidth = MKUimage.getWidth();
        MKUheight = MKUimage.getHeight();

        MKUX = Settings.WIDTH * 0.85F - MKUwidth / 2;
        MKUY = Settings.HEIGHT * -0.3F;

        this.color = Color.WHITE.cpy();
        this.color.a = 0F;

        targetAlpha = 0.75F;
        timePerPhase = 0.66F;

        step = 0;


    }

    @Override
    public void update() {
        MKUY += Gdx.graphics.getDeltaTime() * 66F * scale;


        switch(step) {
            case 0:
                this.color.a += Gdx.graphics.getDeltaTime() * targetAlpha * timePerPhase;
                if(this.color.a >= targetAlpha) {
                    step++;
                    timer = timePerPhase;
                }
                break;
            case 1:
                timer -= Gdx.graphics.getDeltaTime();
                if(timer <= 0F) {
                    step++;
                }
                break;
            case 2:
                this.color.a -= Gdx.graphics.getDeltaTime() * targetAlpha * timePerPhase;
                if(this.color.a <= 0F) {
                    this.isDone = true;
                }
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.draw(MKUimage, MKUX, MKUY, MKUwidth / 2F, 0, MKUwidth, MKUheight, this.scale, this.scale, 0, 0, 0, MKUwidth, MKUheight, true, false);

        sb.setColor(Color.WHITE);
    }

    @Override
    public void dispose() {}


}
