package ro.tutu.flock.boid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ro.tutu.flock.FlockExperiment;
import ro.tutu.flock.utils.RandomUtils;

import java.util.List;
import java.util.Random;

public class Boid extends Sprite{
    private static final String BOID_IMAGE = "boid.png";
    private static final int BOID_HEIGHT = 10;
    private static final int BOID_WIDTH = 10;
    private final SpriteBatch batch;
    private Vector2 velocity;
    private int index;

    public Boid(int index, SpriteBatch batch){
        super(new Texture(BOID_IMAGE));
        this.index = index;
        this.batch = batch;
        this.setSize(BOID_WIDTH, BOID_HEIGHT);
        this.setScale(BOID_WIDTH, BOID_HEIGHT);
        this.setPosition(
                new Random().nextInt(FlockExperiment.WIDTH - BOID_WIDTH),
                new Random().nextInt(FlockExperiment.HEIGHT - BOID_HEIGHT)
        );
        this.velocity = new Vector2(RandomUtils.RandomFloat(), RandomUtils.RandomFloat());
        System.out.println("Size:  --- "+ this.getHeight());
    }

    public void edges(){
        if(this.getX() > FlockExperiment.WIDTH - BOID_WIDTH) this.setPosition(BOID_WIDTH, this.getY());
        else if(this.getX() < 0) this.setPosition(FlockExperiment.WIDTH - BOID_WIDTH, this.getY());

        if(this.getY() > FlockExperiment.HEIGHT + BOID_HEIGHT) this.setPosition(this.getX(), BOID_HEIGHT);
        else if(this.getY() < 0) this.setPosition(this.getX(), FlockExperiment.HEIGHT - BOID_HEIGHT);
    }

    public void draw(){
        this.setRotation(this.velocity.angle());
        this.setPosition(this.getX() + this.velocity.x, this.getY() + this.velocity.y);
        this.draw(batch);
        //batch.draw(this, this.getX(), this.getY(), BOID_WIDTH, BOID_HEIGHT);
    }

}
