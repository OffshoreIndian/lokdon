package com.offshoreindian.lokdon.encryption;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by praveshkumar on 27/05/17.
 */

public class MpinEncryption {
     int rightBorder[] =  {15, 31, 47, 63, 79, 95, 111, 127, 143, 159, 175, 191, 207, 223, 239, 255};
      int leftBorder [] ={0, 16, 32, 48, 64, 80, 96, 112, 128, 144, 160, 176, 192, 208, 224, 240};
    public static MpinEncryption instance = null;
    private ArrayList<int[]> moveNumberList = null;
    private ArrayList<char[]> moveStringList = null;
    private int KNIGHT_MATRIX_WIDTH = 16;
    private int KNIGHT_MATRIX_HEIGHT = 16;
    private int ST_SIZE = 256;//start from 0
    private int KNIGHT_MOVE_TOTAL = 2;
    private int[] knightMoveNumber = new int[KNIGHT_MOVE_TOTAL];
   // private int[] knightMoveNumber ={88,26};
    private MpinEncryption()
    {

    }

    public static MpinEncryption getInstance()
    {
        if(instance == null)
            instance = new MpinEncryption();
        return instance;
    }


    private int[] generateKnightMoveNumber(int move) {
        KNIGHT_MOVE_TOTAL = move;
        knightMoveNumber = new int[KNIGHT_MOVE_TOTAL];
        for (int i = 0; i < knightMoveNumber.length; i++) {
            knightMoveNumber[i] = new Random().nextInt((KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT) - 1);
        }
        return knightMoveNumber;


    }
    public String getMpinEncrypetData(String mpin)
    {
        generateKnightMoveNumber(KNIGHT_MOVE_TOTAL);
        char [] mpinCharArray = mpin.toCharArray();

        moveNumberList = new ArrayList<int[]>();
        moveStringList = new ArrayList<char[]>();

        // Generating knight move using random number
        for (int i = 0; i < knightMoveNumber.length; i++) {
            KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
            solver.setPosition(knightMoveNumber[i] % KNIGHT_MATRIX_WIDTH, knightMoveNumber[i] / KNIGHT_MATRIX_HEIGHT);
            int temp[] = solver.loopSolver();
            moveNumberList.add(temp);
            solver.reset();
            solver = null;
        }

        // Creating new character array using knight moves
        for (int index = 0; index < moveNumberList.size(); index++)
        {
            char tempArr[] = new char[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
            System.out.print( knightMoveNumber[index]+"   M"+(index+1)+"  --  ");
            for (int i = 0; i < moveNumberList.get(index).length; i++) {
                tempArr[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
                System.out.print(AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]]+",");
            }

            System.out.println();
            moveStringList.add(tempArr);
            tempArr = null;
        }



        char knightArray[] = moveStringList.get(moveStringList.size()-1);
        StringBuffer buffer = new StringBuffer();
            for (int j = 0;j<mpinCharArray.length;j++)
            {
                for (int i = 0;i<knightArray.length;i++)
                {

                    if(knightArray[i] == mpinCharArray[j])
                    {
                        boolean isMatch = false;
                        for (int k = 0;k<leftBorder.length;k++)
                        {
                            if(i == leftBorder[k])
                            {
                                isMatch = true;
                                int index = i+1;
                                buffer.append(knightArray[index]);
                                break;
                            }
                        }
                        for (int k = 0;k<rightBorder.length;k++)
                        {
                            if(i == rightBorder[k])
                            {
                                isMatch = true;
                                int index = i-1;
                                buffer.append(knightArray[index]);
                                break;
                            }
                        }
                        if( !isMatch)
                        {
                            int index = i+1;
                            buffer.append(knightArray[index]);
                        }
                        System.out.println(knightArray[i]+"    value "+i+ "     buffer  "+buffer.toString());
                    }
                }
            }

            for (int i = 0;i<knightMoveNumber.length;i++)
                buffer.append(AllCharacter.getInstance().getCharacterReverseArray()[knightMoveNumber[i]]);

        return buffer.toString();
    }
}
