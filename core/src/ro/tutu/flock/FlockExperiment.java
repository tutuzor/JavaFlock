package ro.tutu.flock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ro.tutu.flock.boid.Boid;

import java.util.ArrayList;
import java.util.List;

public class FlockExperiment extends ApplicationAdapter {
	private SpriteBatch batch;
	private List<Boid> boidList;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		this.boidList = new ArrayList<>();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		for(int i=0; i<10; i++){
			this.boidList.add(new Boid(i ,batch));
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		boidList.forEach(boid -> {
		    boid.edges();
		    boid.draw();
        });
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
