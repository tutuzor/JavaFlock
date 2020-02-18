package ro.tutu.flock.boid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ro.tutu.flock.FlockExperiment;
import ro.tutu.flock.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boid extends Sprite{
    private static final String BOID_IMAGE = "boid.png";
    private static final int BOID_HEIGHT = 10;
    private static final int BOID_WIDTH = 10;
    private final SpriteBatch batch;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int index;
    private List<Boid> boids;

    public Boid(int index, SpriteBatch batch){
        super(new Texture(BOID_IMAGE));
        this.index = index;
        this.batch = batch;
        this.setPosition(
                new Random().nextInt(FlockExperiment.WIDTH - BOID_WIDTH),
                new Random().nextInt(FlockExperiment.HEIGHT - BOID_HEIGHT)
        );
        this.velocity = new Vector2(RandomUtils.RandomFloat(), RandomUtils.RandomFloat());
        this.acceleration = new Vector2();
        boids = new ArrayList<>();
    }

    private void edges(){
        if(this.getX() > FlockExperiment.WIDTH - BOID_WIDTH) this.setPosition(BOID_WIDTH, this.getY());
        else if(this.getX() < 0) this.setPosition(FlockExperiment.WIDTH - BOID_WIDTH, this.getY());

        if(this.getY() > FlockExperiment.HEIGHT + BOID_HEIGHT) this.setPosition(this.getX(), BOID_HEIGHT);
        else if(this.getY() < 0) this.setPosition(this.getX(), FlockExperiment.HEIGHT - BOID_HEIGHT);
    }

    private void align(){
        Vector2 result = new Vector2();
        int perception = 10;
        int noBoids = 0;
        for(Boid boid : boids){
            if(this.index != boid.getIndex() &&
                    Vector2.dst(this.getX(), this.getY(), boid.getX(), boid.getY()) < perception) {
                result.add(boid.getVelocity());
                noBoids++;
            }
        }
        if(0 < noBoids) {
            result.scl(1f/noBoids);
            result.sub(this.velocity);
            this.acceleration = result;
        }
    }

    public void draw(){
        this.edges();
        this.align();
        this.setRotation(this.velocity.angle() - 90);
        this.setPosition(this.getX() + this.velocity.x, this.getY() + this.velocity.y);
        this.velocity.add(this.acceleration);
        this.acceleration = Vector2.Zero;
        this.draw(batch);
    }

    public void setBoids(List<Boid> boids) {
        this.boids = boids;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getIndex() {
        return index;
    }
}
