package ro.tutu.flock.utils;

import java.util.Random;

public class RandomUtils {
    public static float RandomFloat(){
        Random random = new Random();
        int sign = random.nextInt(2);
        if(sign < 1) sign = -1;
        else sign = 1;
        float result = random.nextFloat();
        return sign * result;
    }
}
