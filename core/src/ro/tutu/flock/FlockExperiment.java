package ro.tutu.flock;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ro.tutu.flock.flock.Flock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FlockExperiment extends ApplicationAdapter {
	private SpriteBatch batch;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 360;

    private List<Flock> flockList;

	@Override
	public void create () {
		this.batch = new SpriteBatch();
		Gdx.gl.glClearColor(0, 0, 0, 0);
		flockList = new ArrayList<>();
		flockList.addAll(Arrays.asList(
            new Flock(100, Color.RED, batch),
            new Flock(100, Color.WHITE, batch),
            new Flock(30, Color.CYAN, batch)
        ));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		flockList.forEach(Flock::draw);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
