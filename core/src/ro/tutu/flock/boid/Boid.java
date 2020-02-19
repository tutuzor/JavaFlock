package ro.tutu.flock.boid;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ro.tutu.flock.FlockExperiment;
import ro.tutu.flock.utils.RandomUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Boid extends Sprite{
    private static final String BOID_IMAGE = "boid.png";
    private static final int BOID_HEIGHT = 10;
    private static final int BOID_WIDTH = 10;
    private static final float MAX_FORCE = 0.2f;
    private static final float MAX_SPEED = 2f;

    private static final int ALIGNMENT_PERCEPTION = 25;
    private static final int COHESION_PERCEPTION = 30;
    private static final int SEPARATION_PERCEPTION = 50;

    private static final float ALIGNMENT_PERCENT = 0.5f;
    private static final float COHESION_PERCENT = 0.5f;
    private static final float SEPARATION_PERCENT = 2f;

    private final SpriteBatch batch;
    private Vector2 velocity;
    private Vector2 acceleration;
    private int index;
    private List<Boid> boids;
    private List<Boid> nearBoids;

    public Boid(int index, Color color, SpriteBatch batch){
        super(new Texture(BOID_IMAGE));
        this.setColor(color);
        this.index = index;
        this.batch = batch;
        double randomDoubleX = Math.random();
        double randomDoubleY = Math.random();
        this.setPosition(
            new Random().nextInt(FlockExperiment.WIDTH - BOID_WIDTH) * ((randomDoubleX<0.5)?-1:1),
            new Random().nextInt(FlockExperiment.HEIGHT - BOID_HEIGHT) * ((randomDoubleY<0.5)?-1:1)
        );
        this.velocity = new Vector2(RandomUtils.RandomFloat(), RandomUtils.RandomFloat());
        this.velocity.limit(MAX_SPEED);
        this.acceleration = new Vector2();
        boids = new ArrayList<>();
    }

    private void setNearBoids(int perception){
        nearBoids = boids.stream().filter(boid -> boid.getIndex() != this.getIndex() && Vector2.dst(this.getX(),
                this.getY(), boid.getX(), boid.getY()) < perception).collect(Collectors.toList());
    }

    private void edges(){
        if(this.getX() > FlockExperiment.WIDTH - BOID_WIDTH) this.setPosition(BOID_WIDTH, this.getY());
        else if(this.getX() < 0) this.setPosition(FlockExperiment.WIDTH - BOID_WIDTH, this.getY());

        if(this.getY() > FlockExperiment.HEIGHT + BOID_HEIGHT) this.setPosition(this.getX(), BOID_HEIGHT);
        else if(this.getY() < 0) this.setPosition(this.getX(), FlockExperiment.HEIGHT - BOID_HEIGHT);
    }

    private Vector2 alignment(){
        Vector2 steering = new Vector2();
        nearBoids.forEach(boid -> steering.add(boid.getVelocity()));
        if(!nearBoids.isEmpty()) {
            steering.scl(1f / nearBoids.size());
            steering.setLength(MAX_SPEED);
            steering.sub(this.velocity);
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    private Vector2 cohesion(){
        Vector2 steering = new Vector2();
        nearBoids.forEach(boid -> steering.add(boid.getX(), boid.getY()));
        if(!nearBoids.isEmpty()) {
            steering.scl(1f / nearBoids.size());
            steering.sub(this.getX(), this.getY());
            steering.setLength(MAX_SPEED);
            steering.sub(this.getVelocity());
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    private Vector2 separation(){
        Vector2 steering = new Vector2(1,1);
        int noBoids = 0;
        for(Boid boid : boids) {
            Vector2 diff = new Vector2(this.getX(), this.getY());
            diff.sub(boid.getX(), boid.getY());
            diff.scl(diff);
            diff.nor();
            steering.add(diff);
            noBoids++;
        }
        if(0 < noBoids) {
            steering.scl(1f/noBoids);
            steering.setLength(MAX_SPEED);
            steering.sub(this.velocity);
            steering.limit(MAX_FORCE);
        }
        return steering;
    }

    public void draw(){
        this.edges();
        this.setNearBoids(ALIGNMENT_PERCEPTION);
        Vector2 alignment = this.alignment().scl(ALIGNMENT_PERCENT);
        this.setNearBoids(COHESION_PERCEPTION);
        Vector2 cohesion = this.cohesion().scl(COHESION_PERCENT);
        this.setNearBoids(SEPARATION_PERCEPTION);
        Vector2 separation = this.separation().scl(SEPARATION_PERCENT);

        this.acceleration.add(alignment);
        this.acceleration.add(cohesion);
        this.acceleration.add(separation);

        this.velocity.add(this.acceleration);
        this.velocity.limit(MAX_SPEED);

        this.setRotation(this.velocity.angle() - 90);
        this.setPosition(this.getX() + this.velocity.x, this.getY() + this.velocity.y);
        this.acceleration.setZero();
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
