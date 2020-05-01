package com.earth.message.algorithms;

import java.util.*;

public class BinaryTree {
    static class Node {
        int val;
        Node left;
        Node right;
    }

    private boolean isBalanced = true;

    public static void treelevel(Node root) {
        if (root == null) {
            return;
        }
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        Node cur;
        while (!q.isEmpty()) {
            cur = q.peek();
            if (cur.left != null) {
                q.add(cur.left);
            }
            if (cur.right != null) {
                q.add(cur.right);
            }
        }
    }


    public int getDepth(Node root) {
        if (root == null)
            return 0;
        int left = getDepth(root.left);
        int right = getDepth(root.right);
        if (Math.abs(left - right) > 1) {
            isBalanced = false;
        }
        return right > left ? right + 1 : left + 1;
    }

    public boolean findTwoArray(int[][] array, int target) {
        boolean res = false;
        int row = array.length;
        int col = array[0].length;

        for (int i = 0, j = col - 1; (i >= 0 && i < row) && (j >= 0 && j < col); ) {
            if (target == array[i][j]) {
                return true;
            } else if (target < array[i][j]) {
                j--;
            } else {
                i++;
            }
        }

        return false;
    }

    /**
     * @param root: The root of binary tree.
     * @return: A list of lists of integer include
     * the zigzag level order traversal of its nodes' values
     */
    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(Node root) {
        if (root == null)
            return new ArrayList<>();

        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        Queue<Node> list = new LinkedList<>();

        list.offer(root);
        boolean normalorder = true;

        while (!list.isEmpty()) {
            ArrayList<Integer> level = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                Node cur = list.poll();
                level.add(cur.val);
                if (cur.left != null)
                    list.offer(cur.left);
                if (cur.right != null)
                    list.offer(cur.right);
            }

            if (normalorder)
                res.add(level);
            else {
                Collections.reverse(level);
                res.add(level);
            }
            normalorder = !normalorder;
        }


        return res;
    }
}
