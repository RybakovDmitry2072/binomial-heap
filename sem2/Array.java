package sem2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Array {
    public List<Integer> createArray(){
        List<Integer> array=new ArrayList<>();
        Random random=new Random();
        for (int i = 0; i < 10000; i++) {
            array.add(random.nextInt(10000)+1);
        }
        return array;
    }
}