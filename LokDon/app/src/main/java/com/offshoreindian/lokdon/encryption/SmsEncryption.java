package com.offshoreindian.lokdon.encryption;

import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;

/**
 * Created by praveshkumar on 15/01/17.
 */

public class SmsEncryption {
    private static SmsEncryption instance = null;
    private int KNIGHT_MATRIX_WIDTH = 16;
    private int KNIGHT_MATRIX_HEIGHT = 16;
    private int ST_SIZE = 256;//start from 0
    private int KNIGHT_MOVE_TOTAL = 5;
    private int[] knightMoveNumber = new int[KNIGHT_MOVE_TOTAL];

//    private int[] knightMoveNumber ={88,26,16,63,50};// = new int[KNIGHT_MOVE_TOTAL];
    private ArrayList<int[]> moveNumberList = null;
    private ArrayList<char[]> moveStringList = null;



    public static SmsEncryption getInstance()
    {
        if(instance == null)
            instance = new SmsEncryption();
        return instance;
    }

    private SmsEncryption()
    {

    }


    public String getEncryptSms(String msg, String key1)
    {


        String key2 = key1.replaceAll("-","");
        String key = key2.replaceAll(" ","");


        DebugUtil.printLog("Use phone no "+key);

        if(key.length() >10)
        {


            String newKey = key.substring(key.length()-10,key.length());
            DebugUtil.printLog(" new Key "+newKey.substring(0,2));
            DebugUtil.printLog(" new Key "+newKey.substring(2,4));
            DebugUtil.printLog(" new Key "+newKey.substring(4,6));
            DebugUtil.printLog(" new Key "+newKey.substring(6,8));
            DebugUtil.printLog(" new Key "+newKey.substring(8,10));

            knightMoveNumber[0]= Integer.parseInt(newKey.substring(0,2));
            knightMoveNumber[1]= Integer.parseInt(newKey.substring(2,4));
            knightMoveNumber[2]= Integer.parseInt(newKey.substring(4,6));
            knightMoveNumber[3]= Integer.parseInt(newKey.substring(6,8));
            knightMoveNumber[4]= Integer.parseInt(newKey.substring(8,10));

        }
        else
        {

        }
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


        char [] messageArray = msg.toCharArray();
        int []  messageIndex = new int[messageArray.length];

        System.out.print("Password   --  ");
        for (int i = 0;i<messageArray.length;i++)
        {
            for (int j = 0;j<AllCharacter.getInstance().getStandardCharacterSet().length;j++)
            {
                if(messageArray[i] == AllCharacter.getInstance().getStandardCharacterSet()[j])
                {
                    messageIndex[i] = j;
                    System.out.print(j+",");
                }
            }
        }

        System.out.println();
        System.out.println("printing new message");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0;i<messageIndex.length;i++)
        {

            System.out.print(moveStringList.get(0)[messageIndex[i]]+",");
            buffer.append(moveStringList.get(0)[messageIndex[i]]);
        }

        for (int i = 0;i<knightMoveNumber.length;i++)
            buffer.append(AllCharacter.getInstance().getCharacterReverseArray()[knightMoveNumber[i]]);

        buffer.append(AllCharacter.getInstance().getCharacterReverseArray()[knightMoveNumber.length]);
        System.out.println();
        System.out.println(" new Generated Message ::::: "+buffer.toString());
        return buffer.toString();
    }



    public String decryptSms(String message)
    {
        StringBuffer buffer = new StringBuffer();
        try
        {
            DebugUtil.printLog(" message  "+message);
            if(moveNumberList == null)
            {
                moveNumberList = new ArrayList<int[]>();
                moveStringList = new ArrayList<char[]>();
            }
            moveNumberList.clear();
            moveStringList.clear();
            int moveIndex = -1;
            for (int i = 0;i<AllCharacter.getInstance().getCharacterReverseArray().length;i++)
            {
                if (message.charAt(message.length()-1) == AllCharacter.getInstance().getCharacterReverseArray()[i])
                {
                    moveIndex = i;
                    System.out.printf("got the new index "+moveIndex);
                }
            }


            String moveChar = message.substring(message.length()-(moveIndex+1),message.length()-1);
            System.out.println(" Encrypt Data :  "+message.substring(0,message.length()-(moveIndex+1)));
            char chiperCharArray [] = message.substring(0,message.length()-(moveIndex+1)).toCharArray();

            System.out.println(" Move Character :::  "+moveChar);
            char moveArray[] = moveChar.toCharArray();
            int moveIndexArray [] = new int [moveArray.length];
            for (int j = 0;j<moveArray.length;j++){
                for (int i = 0;i<AllCharacter.getInstance().getCharacterReverseArray().length;i++)
                {

                    if(moveArray[j] == AllCharacter.getInstance().getCharacterReverseArray()[i]){
                        System.out.print(i+",");
                        moveIndexArray[j] = i;
                        break;
                    }
                }
            }
            System.out.println();

            // Generating knight move using random number
            for (int i = 0; i < moveIndexArray.length; i++) {
                KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
                solver.setPosition(moveIndexArray[i] % KNIGHT_MATRIX_WIDTH, moveIndexArray[i] / KNIGHT_MATRIX_HEIGHT);
                int temp[] = solver.loopSolver();
                moveNumberList.add(temp);
                solver.reset();
                solver = null;
            }


            // Creating new character array using knight moves
            for (int index = 0; index < moveNumberList.size(); index++)
            {
                char tempArr[] = new char[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
                System.out.print("M"+(index+1)+"  --  ");
                for (int i = 0; i < moveNumberList.get(index).length; i++) {
                    tempArr[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
                    System.out.print(AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]]+",");
                }

                System.out.println();
                moveStringList.add(tempArr);
                tempArr = null;
            }


            System.out.println();
            int chiperValueArray[] = new int [chiperCharArray.length];
            for (int i = 0;i<chiperCharArray.length;i++)
            {
                for (int j = 0;j<moveStringList.get(0).length;j++)
                {
                    if(chiperCharArray[i] == moveStringList.get(0)[j])
                    {
                        chiperValueArray[i] = j;
                        System.out.print(j+",");
                    }
                }
            }


            System.out.println(" Decrypt value ");
            for (int i = 0;i<chiperValueArray.length;i++)
            {
                System.out.print(AllCharacter.getInstance().getStandardCharacterSet()[chiperValueArray[i]]);
                buffer.append(AllCharacter.getInstance().getStandardCharacterSet()[chiperValueArray[i]]);
            }
            System.out.println("");
        }catch (Exception e)
        {
            buffer.append(message);
        }



//        int moveIndex = message.charAt(message.length()-1);


        return buffer.toString();
    }

}
