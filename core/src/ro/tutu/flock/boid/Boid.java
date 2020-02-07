package ro.tutu.flock.boid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Boid {
    private static final String BOID_IMAGE = "boid.png";
    private final Texture img;
    private final SpriteBatch batch;
    private final OrthographicCamera cam;
    private final Sprite sprite;

    public Boid(SpriteBatch batch, OrthographicCamera cam){
        this.img = new Texture(BOID_IMAGE);
        this.batch = batch;
        this.cam = cam;
        this.sprite = new Sprite(this.img);
        this.sprite.setSize(10,10);
    }

    public void draw(){
        batch.setProjectionMatrix(cam.combined);
        sprite.setPosition(0,0);
        this.sprite.draw(batch);
    }

    public void dispose(){
        img.dispose();
    }
}
