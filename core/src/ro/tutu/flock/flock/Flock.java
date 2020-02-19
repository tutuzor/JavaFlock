package ro.tutu.flock.flock;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ro.tutu.flock.boid.Boid;

import java.util.ArrayList;
import java.util.List;

public class Flock {
    private List<Boid> boidList;

    public Flock(int numberOfBoids, Color color, SpriteBatch batch){
        boidList = new ArrayList<>();
        for(int i=0; i< numberOfBoids; i++){
            Boid boid = new Boid(i, color, batch);
            boid.setBoids(boidList);
            boidList.add(boid);
        }
    }

    public void draw(){
        boidList.forEach(Boid::draw);
    }

}
