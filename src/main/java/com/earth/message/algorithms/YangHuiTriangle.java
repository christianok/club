package com.earth.message.algorithms;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class YangHuiTriangle {
    public List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();
        if (numRows < 1) return result;

        for (int i = 0; i < numRows; ++i) {
            //扩容
            List<Integer> list = Arrays.asList(new Integer[i + 1]);
            list.set(0, 1);
            list.set(i, 1);
            for (int j = 1; j < i; ++j) {
                //等于上一行的左右两个数字之和
                list.set(j, result.get(i - 1).get(j - 1) + result.get(i - 1).get(j));
            }
            result.add(list);
        }
        return result;

    }

    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>(rowIndex + 1);
        long index = 1;
        for (int i = 0; i <= rowIndex; i++) {
            res.add((int) index);
            index = index * (rowIndex - i) / (i + 1);
        }
        return res;
    }

    public static void main(String[] args) {
        YangHuiTriangle triangle = new YangHuiTriangle();
        List<List<Integer>> generate = triangle.generate(5);
        log.info(generate.toString());
    }
}
