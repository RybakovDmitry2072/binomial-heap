package sem2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

public class Test {
    public static void main(String[] args) {
        Random random = new Random();
        Array arrayTest = new Array();
        List<Integer> array = arrayTest.createArray();
        BinomialHeap<Integer> binomialHeap = new BinomialHeap<>();

        List<List<Long>> p3 = performInsertion(10000, binomialHeap, i ->{
            binomialHeap.insert(array.get(i));
            return List.of(binomialHeap.getOperationCountInsert(), array.get(i));
        });

        List<List<Long>> p4=performInsertion(100,binomialHeap, i -> {
            BinomialHeap.Node<Integer> node = binomialHeap.search((array.get(random.nextInt(array.size()))));
            return List.of(binomialHeap.getOperationCountSearch(), node.key);
        });

        List<List<Long>> p5 = performInsertion(1000, binomialHeap, i
                -> {int randomInt=random.nextInt(array.size());
                    BinomialHeap.Node<Integer> node=binomialHeap.search(array.get(randomInt));
                    array.remove(randomInt);
                    if (node != null) {
                        binomialHeap.delete(node);
                        return List.of(binomialHeap.getOperationCountDelete(), node.key);
                    }
                    return null;
        });

        write(p3,"fileP3");//данные о добавлении(1 столбик - время, втрой - кол-во операций, третий - значение)
        write(p4,"fileP4");//данные о поиске(1 столбик - время, втрой - кол-во операций, третий - значение)
        write(p5,"fileP5");//данные о удалении(1 столбик - время, втрой - кол-во операций, третий - значение)

        System.out.println(Arrays.toString(getMidle(p3)));//среднее время времени и операций
        System.out.println(Arrays.toString(getMidle(p4)));//среднее время времени и операций
        System.out.println(Arrays.toString(getMidle(p5)));//среднее время времени и операций
    }

    public static List<List<Long>> performInsertion(int count, BinomialHeap<Integer> binomialHeap, Function<Integer, List<Integer>> insertionOperation) {
        List<List<Long>> p = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            long startTime = System.nanoTime();
            List<Integer> operationCountAndValue=insertionOperation.apply(i);
            long endTime = System.nanoTime();
            long Time = endTime - startTime;
            if(operationCountAndValue!=null){
                p.add(List.of(Time, (long)operationCountAndValue.get(0), (long)operationCountAndValue.get(1)));
            }
        }
        return p;
    }
    public static double[] getMidle(List<List<Long>> array){
        double sumX = 0;
        double sumY = 0;
        for (List<Long> pair : array) {
            sumX += pair.get(0);
            sumY += pair.get(1);
        }
        double midX = sumX / array.size();
        double midY = sumY / array.size();

        return new double[]{midX, midY};
    }
    public static void write(List<List<Long>> data, String file){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < data.size(); i++) {
                List<Long> pair=data.get(i);
                String line=pair.get(0)+","+ pair.get(1)+","+pair.get(2);
                writer.write(line+"\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
