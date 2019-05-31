package com.simulation;


import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.paukov.combinatorics.CombinatoricsFactory.*;

public class Test {


    public static final int HITS_TO_KILL = 8;

    public static final int CURRENT_TURN = 6;
    public static final int HP = 480;
    public static final int DAMAGE = 50;

    public  static HashMap<String, Integer> DAMAGE_MAP = new HashMap<String, Integer>(){
        {
            put("0", 0);
            put("1", DAMAGE);
            put("2", DAMAGE * 2);
        }
    };


    public static void main(String[] args) {


        ICombinatoricsVector<String> vector = createVector("0", "1", "2");
        Generator<String> gen = createPermutationWithRepetitionGenerator(vector, CURRENT_TURN);

        List<List<String>> resultList = new ArrayList<>();

        HashMap<String, Double> map = new HashMap<>();
        {
            map.put("0", 0.16d);
            map.put("1", 0.48d);
            map.put("2", 0.36d);
        }



        for (ICombinatoricsVector<String> perm : gen) {
            int sum = 0;
            boolean print = false;

            for (int i = 0; i < perm.getVector().size(); i++) {

                if (i == (perm.getVector().size() - 1) && sum < Test.HP)
                    print = true;

                sum += DAMAGE_MAP.get(perm.getVector().get(i));
                // print = true;

            }
            // if (sum >= Test.hits) System.out.println(perm.getVector());
            if (print && sum >= Test.HP) {
                   System.out.println(perm.getVector()+" "+ sum);
                resultList.add(perm.getVector());
            }

        }


        BigDecimal sum = new BigDecimal(0);
       // double sum = 0;
        for (List<String> l : resultList) {
            BigDecimal a =new BigDecimal(1d);
           // double a = 1d;
            for (String ll : l) {
               // a *= map.get(ll);
                a=a.multiply(new BigDecimal(map.get(ll)));
            }
         //   System.out.println(l + " "+ a);
           // sum+=a;
            sum=sum.add(a);

        }
        System.out.println((sum + "").replaceAll("\\.",","));

    }

}