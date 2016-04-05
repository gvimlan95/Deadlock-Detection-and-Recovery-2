package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        System.out.println("Enter the number of processes: ");
        int numProcess = sc.nextInt();

        boolean hasRun[] = new boolean[numProcess];

        for (int i = 0; i < numProcess; i++) {
            hasRun[i] = true;
        }

        System.out.println("Enter the number of resources: ");
        int numResource = sc.nextInt();

        int currentlyAllocated[][] = new int[numProcess][numResource];
        int maxClaim[][] = new int[numProcess][numResource];
        int neededResources[][] = new int[numProcess][numResource];

        int maxResource[] = new int[numResource];
        int available[] = new int[numResource];
        int totalAllocation[] = new int[numResource];

        System.out.println("Enter the max resources: ");

        //ALLOCATING RANDOM VALUES TO MAX RESOURCE BASED ON USER INPUT
        int tempMaxRes = sc.nextInt();
        for (int i = 0; i < numResource; i++) {
            maxResource[i] = tempMaxRes;
        }


        System.out.println("Please choose one of the option below to prepare the resources details: ");
        System.out.println("(1) Auto-generate the resources");
        System.out.println("(2) Input the resources details manually");
        int input = sc.nextInt();

        if(input == 1){

            System.out.println("Enter the resources allocated for each process: ");

            //ALLOCATING RANDOM VALUES TO CURRENTLY ALLOCATED ARRAY BY SETTING THE
            //RANGE BETWEEN 0-MAXRESOURCES
            for (int j = 0; j < numResource; j++) {
                int temp = maxResource[j];
                for (int i = 0; i < numProcess; i++) {
                    int temp2 = random.nextInt(temp);
                    currentlyAllocated[i][j] = (int) (temp2-(temp2/1.5));
                    temp -= currentlyAllocated[i][j];
                }
            }

            System.out.println("Enter the Maximum Claim for each process: ");
            //ALLOCATE MAX RESOURCE CLAIM FOR EACH PROCESS WITH RANDOM VALUES
            //BY SETTING THE RANGE BETWEEN (MAXRESOURCE- CURRENTLYALLOCATED) + CURRENTLYALLOCATED
            //TO ENSURE THAT THE VALUE HIGHER THAN CURRENTLYALLOCATED BUT LOWER THAN MAX RESOURCES
            for (int i = 0; i < numProcess; i++) {
                for (int j = 0; j < numResource; j++) {
                    maxClaim[i][j] = random.nextInt(maxResource[j] - currentlyAllocated[i][j])+currentlyAllocated[i][j];
                }
            }

        }else if(input == 2){

            System.out.println("Enter the resources allocated for each process: ");

            //ALLOCATING RANDOM VALUES TO CURRENTLY ALLOCATED ARRAY BY SETTING THE
            //RANGE BETWEEN 0-MAXRESOURCES
            for (int j = 0; j < numResource; j++) {
                int temp = maxResource[j];
                for (int i = 0; i < numProcess; i++) {
                    currentlyAllocated[i][j] = sc.nextInt();
                }
            }

            System.out.println("Enter the Maximum Claim for each process: ");
            //ALLOCATE MAX RESOURCE CLAIM FOR EACH PROCESS WITH RANDOM VALUES
            //BY SETTING THE RANGE BETWEEN (MAXRESOURCE- CURRENTLYALLOCATED) + CURRENTLYALLOCATED
            //TO ENSURE THAT THE VALUE HIGHER THAN CURRENTLYALLOCATED BUT LOWER THAN MAX RESOURCES
            for (int i = 0; i < numProcess; i++) {
                for (int j = 0; j < numResource; j++) {
                    maxClaim[i][j] = sc.nextInt();
                }
            }

        }

        System.out.println("The Maximum Resources: ");
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

        //ACCUMULATE ALL THE RESOURCE ALLOCATED FOR EACH PROCESS BASED ON RESOURCE ID
        for (int i = 0; i < numProcess; i++) {
            for (int j = 0; j < numResource; j++) {
                totalAllocation[j] += currentlyAllocated[i][j];
            }
        }

        System.out.println("\nTotal Allocated Resources: ");
        for (int i = 0; i < numResource; i++) {
            System.out.print("\t" + totalAllocation[i]);
        }

        //CALCULATE THE AVAILABLE RESOURCE FOR EACH RESOURCE BY (MAXRESOURCE - TOTALALLOCATION)
        for (int i = 0; i < numResource; i++) {
            available[i] = maxResource[i] - totalAllocation[i];
        }

        //CALCULATE THE NECESSARY RESOURCES FOR EACH PROCESS TO COMPLETE THEIR TASK
        for(int i =0;i<numProcess;i++){
            for(int j=0;j<numResource;j++){
                neededResources[i][j] = maxClaim[i][j] - currentlyAllocated[i][j];
            }
        }

        System.out.println("\nTotal Available Resources: ");
        for (int i = 0; i < numResource; i++) {
            System.out.print("\t" + available[i]);
        }
        System.out.println();

        boolean[] isDone = new boolean[numProcess];
        int[] dead = new int[numProcess];
        boolean flag=true;
        for(int i=0;i<numProcess;i++){
            isDone[i]=false;
        }

        while(flag){
            flag=false;
            for(int i=0;i<numProcess;i++){
                int count2=0;
                for(int j=0;j<numResource;j++){
                    //CHECKS IF ALL THE RESOURCES IN A PROCESS IS NOT PROCESSED YET AND CHECKS
                    // IF THE NEEDED RESOURCES IS LESSER THAN AVAILABLE RESOURCES
                    if((isDone[i]==false)&&(neededResources[i][j]<=available[j])){
                        count2++;
                        //IF THE LOOP HAS REACH THE FINAL RESOURCE FOR A PROCESS
                        if(count2==numResource){
                            for(int k=0;k<numResource;k++){
                                available[k]+=currentlyAllocated[i][j];
                                isDone[i]=true;
                                flag=true;
                            }
                            if(isDone[i]==true){
                                i=numProcess;
                            }
                        }
                    }
                }
            }
        }
        int j=0;
        flag=false;
        for(int i=0;i<numProcess;i++){
            //CHECKS IF THE ARRAY CONTAINS FALSE BOOLEAN TO INDICATE THAT THE SPECIFIC PROCESS CONTAINS
            //RESOURCE THAT COULD NOT BE PROCESSED
            if(isDone[i]==false){
                dead[j]=i;
                j++;
                flag=true;
            }
        }
        if(flag==true){
            System.out.print("\n\nSystem is in Deadlock and the Deadlock process are\n");
            for(int i=0;i<numProcess;i++) {
                System.out.print("P"+dead[i]+"\t");
            }
            System.out.println("\n**********DEADLOCK OCCURED**********");
        }
        else {
            System.out.println("\n**********No Deadlock Occur**********");
        }
    }
    
}
