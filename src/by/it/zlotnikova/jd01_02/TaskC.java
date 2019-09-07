package by.it.zlotnikova.jd01_02;

import java.util.Random;
import java.util.Scanner;

public class TaskC {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] matrix = step1(n);
        step2(matrix);
    }


    private static int[][] createRandomizedMatrix(int n) {
        Random random = new Random();
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(2 * n + 1) - n;
            }
        }
        return matrix;
    }

    public static int[][] step1(int n) {
        int[][] matrix = new int[n][n];
        boolean flag1 = false;
        boolean flag2 = false;
        while (!(flag1 && flag2)) {
            matrix = createRandomizedMatrix(n);
            flag1 = false;
            flag2 = false;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == -1 * n) {
                        flag1 = true;
                    } else if (matrix[i][j] == n) {
                        flag2 = true;
                    }
                }
            }
        }
        return matrix;
    }

    public static int step2(int[][] mas) {
        int sum = 0;
        for (int[] ma : mas) {
            int counter = 0;
            int[] index = new int[2];
            for (int j = 0; j < ma.length; j++) {
                if (counter < 2 && ma[j] > 0) {
                    index[counter] = j;
                    counter++;
                }
            }
            if (counter == 2) {
                if (index[1] - index[0] > 1)
                    for (int j = index[0] + 1; j <= index[1] - 1; j++) {
                        sum = sum + ma[j];
                    }
            }
        }
        System.out.println(sum);
        return sum;
    }
}


