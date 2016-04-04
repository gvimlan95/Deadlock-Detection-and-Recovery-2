package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        boolean safe = false, exec;
        int count = 0, numResource, numProcess, k = 1;

        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Enter the number of processes: ");
        numProcess = sc.nextInt();

        boolean running[] = new boolean[numProcess];

        count = numProcess;
        for (int i = 0; i < numProcess; i++) {
            running[i] = true;
        }

        System.out.println("Enter the number of resources: ");
        numResource = sc.nextInt();

        int maxResource[] = new int[numResource];
        int available[] = new int[numResource];
        int currentlyAllocated[][] = new int[numProcess][numResource];
        int maxClaim[][] = new int[numProcess][numResource];
        int totalAllocation[] = new int[numResource];

        System.out.println("Enter the max resources: ");

        int tempMaxRes = sc.nextInt();
        for (int i = 0; i < numResource; i++) {
            maxResource[i] = tempMaxRes;
//            maxResource[i] = sc.nextInt();
        }

//        System.out.println("Enter the allocated resources: ");

        for (int j = 0; j < numResource; j++) {
            int temp = maxResource[j];
            for (int i = 0; i < numProcess; i++) {
                currentlyAllocated[i][j] = random.nextInt(temp);
                temp -= currentlyAllocated[i][j];
//                currentlyAllocated[i][j] = sc.nextInt();
            }
        }

        System.out.println("Enter the max claim: ");
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                maxClaim[i][j] = random.nextInt(maxResource[j] - currentlyAllocated[i][j])+currentlyAllocated[i][j];
//                maxClaim[i][j] = sc.nextInt();
            }
        }

        System.out.println("The max resources: ");
        for (int i = 0; i < numResource; i++) {
            System.out.print("\t" + maxResource[i]);
        }

        System.out.println("\nThe Allocated Resource Table: ");
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                System.out.print("\t" + currentlyAllocated[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nThe Maximum Claim Table: ");
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                System.out.print("\t" + maxClaim[i][j]);
            }
            System.out.println();
        }

        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                totalAllocation[j] += currentlyAllocated[i][j];
            }
        }

        System.out.println("\nAllocated Resources: ");
        for (int i = 0; i < numResource; i++) {
            System.out.print("\t" + totalAllocation[i]);
        }

        for (int i = 0; i < numResource; i++) {
            available[i] = maxResource[i] - totalAllocation[i];
        }

        System.out.println("\nAvailable resources: ");
        for (int i = 0; i < numResource; i++) {
            System.out.print("\t" + available[i]);
        }

        while (count != 0) {
            safe = false;
            for (int i = 0; i < numProcess; i++) {
                if (running[i]) {
                    exec = true;
                    for (int j = 0; j < numResource; j++) {
                        if (maxClaim[i][j] - currentlyAllocated[i][j] > available[j]) {
                            exec = false;
                            break;
                        }
                    }
                    if (exec) {
                        System.out.println("\nProcess " + (i + 1) + " is executing");
                        running[i] = false;
                        count--;
                        safe = true;

                        for (int j = 0; j < numResource; j++) {
                            available[j] += currentlyAllocated[i][j];
                        }
                        break;
                    }
                }
            }
            if (!safe) {
                System.out.println("\nThe processes are in unsafe state");
                break;
            } else {
                System.out.println("\nThe process is in safe state");
                System.out.println("Available vector");

                for (int i = 0; i < numResource; i++) {
                    System.out.print("\t" + available[i]);
                }
            }
        }
    }
}
