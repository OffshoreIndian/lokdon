package com.offshoreindian.lokdon.encryption;

import com.offshoreindian.lokdon.utils.Constants;
import com.offshoreindian.lokdon.utils.DebugUtil;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by praveshkumar on 09/11/16.
 */

public class PassKeyController {


    private int KNIGHT_MATRIX_WIDTH = 16;
    private int KNIGHT_MATRIX_HEIGHT = 16;

    private int KNIGHT_MOVE_TOTAL = 5;
    public static PassKeyController instance = null;
    private int[] knightMoveNumber = new int[KNIGHT_MOVE_TOTAL];
    private ArrayList<int[]> moveNumberList = null;
    private ArrayList<String[]> moveStringList = null;

    private PassKeyController() {

//            DebugUtil.printLog("standardCharacterSet Length :: "+  AllCharacter.getInstance().getStandardCharacterSet().length);
//        for (int i = 0; i<AllCharacter.getInstance().getStandardCharacterSet().length;i++)
//        {
//            System.out.print(AllCharacter.getInstance().getStandardCharacterSet()[i]);
////            if(AllCharacter.getInstance().getStandardCharacterSet()[i].equals("A"))
////                    DebugUtil.printLog("=======A MAtche :  "+AllCharacter.getInstance().getStandardCharacterSet()[i]);
////                 DebugUtil.printLog(AllCharacter.getInstance().getStandardCharacterSet()[i]+"  "+i);
//        }
//        System.out.println();

    }

    /**
     * Single Ton class
     *
     * @return
     */
    public static PassKeyController getInstance() {
        if (instance == null)
            instance = new PassKeyController();
        return instance;
    }

//
//    /**
//     * this will generate random number for knight move's
//     *
//     * @return
//     */
//    private int[] generateKnightMoveNumber() {
//        //    System.out.print("Knight Move Number :  " );
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            knightMoveNumber[i] = new Random().nextInt((KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT) - 1);
//            //System.out.print("Knight Move Number :  " +knightMoveNumber[i]+",");
//        }
//
////        int random[] = {13, 1, 4, 3, 2};
////        knightMoveNumber = random;
//        return knightMoveNumber;
//
//        /// remove that code after discussion with JOE
//        //    System.out.print("Knight Move Number :  " );
////        for (int j = 0;j<500;j++) {
////            System.out.print( "Knight Move Number index : "+j+"   :  ");
////
////            for (int i = 0; i < knightMoveNumber.length; i++) {
////                knightMoveNumber[i] = new Random().nextInt(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT);
////
////               // if(knightMoveNumber[i] % 2 == 0)
////                    System.out.print(+ knightMoveNumber[i] + ",");
////            }
////            System.out.print(" Even Number found : ");
////            for (int i = 0; i < knightMoveNumber.length; i++) {
////               // knightMoveNumber[i] = new Random().nextInt(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT);
////
////                  if(knightMoveNumber[i] % 2 == 0)
////                 System.out.print(+ knightMoveNumber[i] + ",");
////            }
////
////            System.out.println();
////        }
////        //int random[]={193,1,2,3,4};
////        //knightMoveNumber = random;
////        return knightMoveNumber;
//
//
//    }
//
//    /**
//     * Provide first even number to generate silent password
//     *
//     * @return
//     */
//    private int getFirstEvenNumberFromKnightMoveIndex() {
//        // as discussed with JOE there is always one even number
//        int tempNumber  = -1;
//         while (tempNumber %2 != 0)
//         {
//             tempNumber =  new Random().nextInt((KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT) - 1);
//         }
//        DebugUtil.printLog("tempNumber  "+tempNumber);
//
//            return tempNumber;
//       // return new Random().nextInt((KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT) - 1);
//    }
//
//    /**
//     * Provide the Knight move generated number
//     *
//     * @return
//     */
//    public String getKnightMoveNumberValue() {
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            buffer.append(knightMoveNumber[i] + ",");
//        }
//        return buffer.toString();
//    }
//
//
//    public String generatePassKey(char[] key) {
//
//        generateKnightMoveNumber();
//        moveNumberList = new ArrayList<int[]>();
//        moveStringList = new ArrayList<String[]>();
//
//        // Generating knight move using random number
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//
//            solver.setPosition(knightMoveNumber[i] % KNIGHT_MATRIX_WIDTH, knightMoveNumber[i] / KNIGHT_MATRIX_HEIGHT);
//            int temp[] = solver.loopSolver();
//
//            // check log
////            if(i == 4) {
////                System.out.println("===============" + knightMoveNumber[i]);
////                for (int ii = 0; ii < temp.length; ii++) {
////                    System.out.print(temp[ii] + ", ");
////                }
////                System.out.println("");
////            }
//            moveNumberList.add(temp);
//            solver.reset();
//            solver = null;
//        }
//
//        System.out.println();
//
//
//        //initialize temp array
////        String tempArr[] = new String[ KNIGHT_MATRIX_WIDTH* KNIGHT_MATRIX_HEIGHT];
////        for (int i = 0;i<tempArr.length;i++){
////            tempArr[i] = new String();
////        }
//
//        // Creating new character array using knight moves
//        System.out.println();
//        for (int index = 0; index < moveNumberList.size(); index++) {
//            String tempArr[] = new String[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
//            for (int i = 0; i < tempArr.length; i++) {
//                tempArr[i] = new String();
//            }
//
//            if (index == 0) {
//
//                for (int i = 0; i < moveNumberList.get(index).length; i++) {
//                    tempArr[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
//                }
//            } else {
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr[i] = moveStringList.get(moveStringList.size() - 1)[moveNumberList.get(index)[i]];
//            }
//
//            moveStringList.add(tempArr);
//
//
//            System.out.println("tempArr  ----"+index);
//            for (int j= 0;j<tempArr.length;j++)
//            {
//                System.out.print(tempArr[j]);
//            }
//
//            tempArr = null;
//        }
//
//        // creating key index array
//        int orgKeyIndexArray[] = new int[key.length];
//        for (int i = 0; i < orgKeyIndexArray.length; i++) {
//            for (int j = 0; j < moveStringList.get(0).length; j++) {
//                if (moveStringList.get(0)[j].equals("" + key[i])) {
//                    orgKeyIndexArray[i] = j;
//                    // DebugUtil.printLog(key[i] +"======="+j);
//                    System.out.print(orgKeyIndexArray[i]+",");
//                }
//            }
//        }
//        System.out.println();
//        StringBuffer keyBuffer = new StringBuffer();
//        for (int i = 0; i < orgKeyIndexArray.length; i++) {
//            //System.out.println();
//            //System.out.println(" moveStringList.get(moveStringList.size()-1)[i]  "+moveStringList.get(moveStringList.size()-1)[orgKeyIndexArray[i]]);
//
//            keyBuffer.append(moveStringList.get(moveStringList.size() - 1)[orgKeyIndexArray[i]]);
//        }
//        String newGeneratedKey = keyBuffer.toString();
//        System.out.println();
//         DebugUtil.printLog("Generated Key "+newGeneratedKey);
////        keyBuffer.append("^");//maping character
////        keyBuffer.append(getKnightMoveNumberValue());
//
//
//        // removing all object
////        moveNumberList.clear();
////        moveNumberList = null;
////        moveStringList.clear();
////        moveStringList = null;
//
//
//        // now generate knight move for silent password
//        KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//        int silentMoveIndex = getFirstEvenNumberFromKnightMoveIndex();
//        // System.out.println("silent move enc "+silentMoveIndex);
//        solver.setPosition(silentMoveIndex % KNIGHT_MATRIX_WIDTH, silentMoveIndex / KNIGHT_MATRIX_HEIGHT);
//        int temp[] = solver.loopSolver();
//        // init array
//        String tempArr[] = new String[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
//        for (int i = 0; i < tempArr.length; i++) {
//            tempArr[i] = new String();
//        }
//
//
//        // create new character array
//        // System.out.println("Silent Move Array");
//        for (int i = 0; i < temp.length; i++) {
//            tempArr[i] = AllCharacter.getInstance().getCharacterReverseArray()[temp[i]];
//            // System.out.print(tempArr[i]+", ");
//
//        }
//        // System.out.println();
//
//        // generate new random character with orignal string length
//        // System.out.println("newGeneratedKey "+newGeneratedKey);
//        char[] generatedArray = newGeneratedKey.toCharArray();
//        // init silent array
//        String rendomSilentArray[] = new String[newGeneratedKey.length()];
//        for (int i = 0; i < rendomSilentArray.length; i++) {
//            rendomSilentArray[i] = new String();
//        }
//        int[] randomNumArray = new int[newGeneratedKey.length()];
//        int index = 0;
//        DebugUtil.printLog(" ============================ ");
//
//        while (index < newGeneratedKey.length()) {
//            boolean isSame = false;
//            int random = new Random().nextInt((KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT) - 1);
//            // check new char is not same as generated password
//            for (int i = 0; i < generatedArray.length; i++) {
//                if (tempArr[random].equals(generatedArray[i] + "")) {
//                    isSame = true;
//                    break;
//                }
//
//            }
//            // check new char is not same as orignal password
//            for (int i = 0; i < key.length; i++) {
//                if (tempArr[random].equals(key[i] + "")) {
//                    isSame = true;
//                    break;
//                }
//
//            }
//
//            if (!isSame) {
//                randomNumArray[index] = random;
//                rendomSilentArray[index] = tempArr[random];
//               // System.out.println(tempArr[random]+" val "+index);
//                index++;
//            }
//
//        }
//
//        System.out.println(" ");
//        System.out.println(" Random Number");
//        for (int i = 0;i<randomNumArray.length;i++)
//        {
//            System.out.print(randomNumArray[i]+",");
//        }
//        System.out.println();
//        System.out.println(" ");
//        System.out.println(" Orignal Number Number");
//        for (int i = 0;i<orgKeyIndexArray.length;i++)
//        {
//            System.out.print(orgKeyIndexArray[i]+",");
//        }
//        System.out.println();
//        //DebugUtil.printLog(" rendomSilentArray "+rendomSilentArray.length);
//
//        // generate silent kye index
////        int[] silentKeyIndex = new int[orgKeyIndexArray.length];
////        for (int i = 0; i < silentKeyIndex.length; i++) {
////            //   System.out.println(silentKeyIndex[i]+" enc value "+randomNumArray[i]);
////            int value = randomNumArray[i] + orgKeyIndexArray[i];
////            //  System.out.println("value value"+value);
////            if (value > 255) {
////                value -= 255;
////            }
////            //System.out.println("  new value"+value);
////            silentKeyIndex[i] = value;
////        }
//////moveStringList.get(moveStringList.size() - 1)
////        StringBuffer silentKey = new StringBuffer();
////       // System.out.println(" key with SL ");
////        for (int i = 0; i < silentKeyIndex.length; i++) {
////            //  DebugUtil.printLog(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]+" new pass key "+silentKeyIndex[i]);
////            silentKey.append(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]);
////         //   System.out.print(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]);
////            silentKey.append(rendomSilentArray[i]);
////        }
//
//        int[] silentKeyIndex = new int[orgKeyIndexArray.length];
//        for (int i = 0; i < silentKeyIndex.length; i++) {
//            int value = randomNumArray[i] + orgKeyIndexArray[i];
//            if (value > 255) {
//                value -= 255;
//            }
//            silentKeyIndex[i] = value;
//        }
//
//        silentKeyIndex = orgKeyIndexArray;
//        System.out.println(" OsilentKeyIndex ber");
//        for (int i = 0;i<silentKeyIndex.length;i++)
//        {
//            System.out.print(silentKeyIndex[i]+",");
//        }
//        System.out.println();
//
//        String keyWithRandom[] = new String[orgKeyIndexArray.length];
//        for (int i = 0; i < keyWithRandom.length; i++) {
//            keyWithRandom[i] = new String();
//        }
//
//         for (int j = 0;j<moveStringList.size();j++)
//        {
//
//           {
//               System.out.print(" Key Value M"+(j+1) +"  ");
//               for (int i = 0;i<silentKeyIndex.length;i++){
//                    keyWithRandom[i] = moveStringList.get(j)[silentKeyIndex[i]];
//
//               }
//               int[] silentKeyIndex1 = new int[orgKeyIndexArray.length];
//               for (int i = 0; i < silentKeyIndex1.length; i++) {
//                   int value = randomNumArray[i] + silentKeyIndex[i];
//                   if (value > 255) {
//                       value -= 255;
//                   }
//                   silentKeyIndex1[i] = value;
//                   System.out.print(silentKeyIndex1[i]+",");
//               }
//               silentKeyIndex = null;
//               silentKeyIndex = silentKeyIndex1;
//               System.out.println();
//           }
//
//        }
//
//
//        StringBuffer encrptKey = new StringBuffer();
//        for (int i = 0;i<silentKeyIndex.length;i++)
//        {
//            encrptKey.append(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]);
//            encrptKey.append(rendomSilentArray[i]);
//        }
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            encrptKey.append(AllCharacter.getInstance().getCharacterReverseArray()[knightMoveNumber[i]]);
//
//        }
//        encrptKey.append(AllCharacter.getInstance().getCharacterReverseArray()[silentMoveIndex]);
//
//        //System.out.println();
//        //DebugUtil.printLog(randomNumArray.length+" orignal  "+silentKeyIndex.length);
//
//        //System.out.println(" Silent key :"+rendomSilentArray.length);
////        for (int i = 0;i<rendomSilentArray.length;i++)
////            System.out.print(rendomSilentArray[i]);
////        System.out.println();
////        System.out.println();
//       // silentKey.append("^");//maping character
////        for (int i = 0; i < rendomSilentArray.length; i++) {
////            //  DebugUtil.printLog(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]+" new pass key "+silentKeyIndex[i]);
////            //silentKey.append(moveStringList.get(moveStringList.size() - 1)[silentKeyIndex[i]]);
////            silentKey.append(rendomSilentArray[i]);
////        }
//      //  silentKey.append("^");//maping character
////        silentKey.append("^");//maping character
////        for (int i = 0; i < knightMoveNumber.length; i++) {
////            silentKey.append(AllCharacter.getInstance().getCharacterReverseArray()[knightMoveNumber[i]]);
////
////        }
////
////        //silentKey.append("^");
////        silentKey.append(AllCharacter.getInstance().getCharacterReverseArray()[silentMoveIndex]);
////        silentKey.append("^");
//        //System.out.println( " random string ");
////        for (int i = 0;i<rendomSilentArray.length;i++)
////        {
////            silentKey.append(rendomSilentArray[i]);
////            //System.out.print(rendomSilentArray[i]+",");
////        }
//
//
//        // silentKey.append(silentMoveIndex);
//
//        // DebugUtil.printLog(" New Slient Password ::  "+silentKey.toString());
//
//       // getPasswordArray(silentKey.toString());
//
//       // char [] gen = new char[(randomNumArray.length*2)+6];
//
//        return encrptKey.toString();//keyBuffer.toString();
//
//
//        //qĉĭĺô^T­7''Ð^ô
//    }
//
//    /**
//     * break the password in array
//     * array 0 contain encrypt pass
//     * array 1 contain silent pass
//     * array 2 contain Knight tour moves
//     * array 3 contain silent index
//     * @param encryptPass
//     * @return
//     */
//    private String [] getPasswordArray(String encryptPass)
//    {
//        String []passArray = new String[4];
//        for (int i = 0;i<passArray.length;i++)
//        {
//            passArray[i] = new String ();
//        }
//       // DebugUtil.printLog(" Pass length "+encryptPass.length());
//        String encryptKey = encryptPass.substring(0,encryptPass.length()-(KNIGHT_MOVE_TOTAL+1));
//        //System.out.println(" enc length "+encryptKey.length());
//
//        StringBuffer pass = new StringBuffer();
//        StringBuffer silentPass = new StringBuffer();
//        for (int i = 0;i<encryptKey.length();i++)
//        {
//            if(i % 2 == 0) {
//                pass.append(encryptKey.charAt(i));
//            }
//            else {
//                silentPass.append(encryptKey.charAt(i));
//            }
//        }
////        DebugUtil.printLog("  encryptKey  " +encryptKey);
////        DebugUtil.printLog("  pass" +pass.toString());
////        DebugUtil.printLog("  silentPass" +silentPass.toString());
//        passArray[0] = pass.toString();
//        passArray[1] = silentPass.toString();
//        passArray[2] = encryptPass.substring(encryptPass.length()-(KNIGHT_MOVE_TOTAL+1),encryptPass.length()-1);
//        passArray[3] = encryptPass.substring(encryptPass.length()-1,encryptPass.length());
//
////        DebugUtil.printLog("  encryptKey  " +encryptKey);
////        DebugUtil.printLog("  pass   " +passArray[0]);
////        DebugUtil.printLog("  silentPass   " +passArray[1]);
////        DebugUtil.printLog("  index  " +passArray[2]);
////        DebugUtil.printLog("  silent index  " +passArray[3]);
//        return passArray;
//    }
//    /**
//     * Decrypt The orignal value
//     *
//     * @param pass
//     * @return
//     */
//    public String decryptSilentPassKey(String pass) {
//        String keyArr[] = getPasswordArray(pass);
//        int silentMove = -1;
//       // DebugUtil.printLog(" keyArr[3]  "+keyArr[3]);
//        for (int i = 0; i < AllCharacter.getInstance().getCharacterReverseArray().length; i++) {
//            if (keyArr[3].equals(AllCharacter.getInstance().getCharacterReverseArray()[i])) {
//                silentMove = i;
//              //   DebugUtil.printLog("silentMove "+silentMove);
//            }
//        }
//
//        KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//
//        solver.setPosition(silentMove % KNIGHT_MATRIX_WIDTH, silentMove / KNIGHT_MATRIX_HEIGHT);
//        int temp[] = solver.loopSolver();
//        String tempArr[] = new String[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
//        for (int i = 0; i < tempArr.length; i++) {
//            tempArr[i] = new String();
//        }
//
//
//        // create new character array
//        // System.out.println("Silent Move Array");
//        for (int i = 0; i < temp.length; i++) {
//            tempArr[i] = AllCharacter.getInstance().getCharacterReverseArray()[temp[i]];
//            // System.out.print(tempArr[i]+", ");
//
//        }
//
//        char[] silentKey = keyArr[1].toCharArray();
//        int[] silentKeyIndex = new int[silentKey.length];
//        for (int i = 0; i < silentKey.length; i++) {
//            for (int j = 0; j < tempArr.length; j++) {
//                if (tempArr[j].equals(silentKey[i] + "")) {
//                    // System.out.println("value :: "+j);
//                    silentKeyIndex[i] = j;
//                }
//
//            }
//        }
//
//        int[] knighMove = new int[keyArr[2].length()];
//        char[] knighMoveChar = keyArr[2].toCharArray();
//        for (int i = 0; i < knighMove.length; i++) {
//            for (int j = 0; j < AllCharacter.getInstance().getCharacterReverseArray().length; j++) {
//                if (AllCharacter.getInstance().getCharacterReverseArray()[j].equals("" + knighMoveChar[i])) {
//                    knighMove[i] = j;
//                    DebugUtil.printLog("ppppppppp");
//                    // DebugUtil.printLog(AllCharacter.getInstance().getCharacterReverseArray()[j]+ "  knighMove "+j);
//
//                }
//            }
//        }
//
//        // run knight tour for 5 time
//        moveNumberList = new ArrayList<int[]>();
//        moveStringList = new ArrayList<String[]>();
//
//        // Generating knight move using random number
//        for (int i = 0; i < knighMove.length; i++) {
//            KnightSolver knightSolver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//
//            knightSolver.setPosition(knighMove[i] % KNIGHT_MATRIX_WIDTH, knighMove[i] / KNIGHT_MATRIX_HEIGHT);
//            int temp1[] = knightSolver.loopSolver();
//
////           if(i ==4) {
////               System.out.println("===============" + knighMove[i]);
////               for (int ii = 0; ii < temp1.length; ii++) {
////                   System.out.print(temp1[ii] + ", ");
////               }
////               System.out.println("");
////           }
//            moveNumberList.add(temp1);
//            knightSolver.reset();
//            knightSolver = null;
//        }
//
//
//        //initialize temp array
////        String tempArr[] = new String[ KNIGHT_MATRIX_WIDTH* KNIGHT_MATRIX_HEIGHT];
////        for (int i = 0;i<tempArr.length;i++){
////            tempArr[i] = new String();
////        }
//
//        // Creating new character array using knight moves
//        for (int index = 0; index < moveNumberList.size(); index++) {
//            String tempArr1[] = new String[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
//            for (int i = 0; i < tempArr1.length; i++) {
//                tempArr1[i] = new String();
//            }
//
//            if (index == 0) {
//
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr1[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
//            } else {
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr1[i] = moveStringList.get(moveStringList.size() - 1)[moveNumberList.get(index)[i]];
//            }
//
//            moveStringList.add(tempArr1);
//            tempArr = null;
//        }
//
//        int[] passCharIndex = new int[keyArr[0].length()];
//        char[] passChar = keyArr[0].toCharArray();
//        int[] orignalPassIndex =  new int[keyArr[0].length()];
//
//
//        String [] encrptArray = new String[keyArr[0].length()];
//        for (int k = 0;k<encrptArray.length;k++ )
//            encrptArray[k] = new String();
//
//
//        for (int i = moveStringList.size()-1;i>-1;i--)
//        {
//
//            if( i == moveStringList.size()-1) {
//                System.out.println(" ENC key index");
//                for (int k = 0; k < passChar.length; k++) {
//                    for (int j = 0; j < moveStringList.get(i).length; j++) {
//                        if (moveStringList.get(i)[j].equals("" + passChar[k])) {
//                            passCharIndex[k] = j;
//                            System.out.print(j+",");
//                        }
//                    }
//                }
//            }
//
//
//            System.out.println();
//            System.out.println(" Index after sub ");
//            for (int k = 0; k < passCharIndex.length; k++) {
//                //   System.out.println(silentKeyIndex[i]+" enc value "+randomNumArray[i]);
//                int value = passCharIndex[k] - silentKeyIndex[k];
////            System.out.println();
////            System.out.println(" i :: "+i+ "  ,");
////            System.out.print( "value value"+value);
//                if (value > -1) {
//
//                } else {
//                    value += 255;
//                }
//                try
//                {
//                    //Thread.sleep(2);
//                }catch (Exception e)
//                {
//
//                }
//                //System.out.print( "  change value"+value);
//                //System.out.println("  new value"+value);
//                orignalPassIndex[k] = value;
//                System.out.print(value+",");
//            }
//            System.out.println();
//            passCharIndex = orignalPassIndex;
//        }
//
//        StringBuffer passBuffer = new StringBuffer();
//        System.out.println("printing password ");
//        for (int i = 0; i < passCharIndex.length; i++) {
//            System.out.print( passCharIndex[i]+",");
//            passBuffer.append(moveStringList.get(0)[passCharIndex[i]]);
//        }
//        System.out.println();
//        return passBuffer.toString();
//
//    }
//
//
//    /**
//     * Decrypt The orignal value
//     *
//     * @param pass
//     * @return
//     */
//    public String decryptPassKey(String pass) {
//        String move = pass.substring(pass.lastIndexOf("^") + 1, pass.length());
//        String passKey = pass.substring(0, pass.lastIndexOf("^"));
//        String[] tempArrMove = move.split(",");
//        int moveArray[] = new int[tempArrMove.length];
//        for (int i = 0; i < moveArray.length; i++) {
//            moveArray[i] = Integer.parseInt(tempArrMove[i]);
//
//        }
//        moveNumberList = new ArrayList<int[]>();
//        moveStringList = new ArrayList<String[]>();
//
//        // Generating knight move using random number
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//
//            solver.setPosition(moveArray[i] % KNIGHT_MATRIX_WIDTH, moveArray[i] / KNIGHT_MATRIX_HEIGHT);
//            int temp[] = solver.loopSolver();
//
//            // check log
////            System.out.println("==============="+knightMoveNumber[i]);
////            for (int ii = 0; ii < temp.length; ii++) {
////                System.out.print(temp[ii] + ", ");
////            }
////            System.out.println("");
//
//            moveNumberList.add(temp);
//            solver.reset();
//            solver = null;
//        }
//
//
//        // Creating new character array using knight moves
//        for (int index = 0; index < moveNumberList.size(); index++) {
//            String tempArr[] = new String[KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT];
//            for (int i = 0; i < tempArr.length; i++) {
//                tempArr[i] = new String();
//            }
//
//            if (index == 0) {
//
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
//            } else {
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr[i] = moveStringList.get(moveStringList.size() - 1)[moveNumberList.get(index)[i]];
//            }
//
//            moveStringList.add(tempArr);
////            System.out.println("tempArr  "+index);
////            for (int j= 0;j<tempArr.length;j++)
////            {
////                System.out.print(tempArr[j]+",");
////            }
//
//            tempArr = null;
//        }
//
//
//        char[] key = passKey.toCharArray();
//        int charIndexArray[] = new int[key.length];
//        StringBuffer tempBuf = new StringBuffer();
//
//
//        for (int i = 0; i < key.length; i++) {
//            for (int index = 0; index < moveStringList.get(moveStringList.size() - 1).length; index++) {
//                if (moveStringList.get(moveStringList.size() - 1)[index].equals(key[i] + "")) {
//                    charIndexArray[i] = index;
//                    tempBuf.append(AllCharacter.getInstance().getStandardCharacterSet()[index]);
//                    //  DebugUtil.printLog(tempPassArray[i]+"  ===  "+index);
//                }
//            }
//        }
//
//
//        // DebugUtil.printLog("tempBuf "+tempBuf.toString());
//        // keyBuffer.append("^");//maping character
//        // keyBuffer.append(getKnightMoveNumberValue());
//
//
//        // removing all object
//        moveNumberList.clear();
//        moveNumberList = null;
//        moveStringList.clear();
//        moveStringList = null;
//
//
//        return tempBuf.toString();
//
//    }
//
//
//    public String[] splitUsingTokenizer(String subject, String delimiters) {
//        try {
//
//            StringTokenizer strTkn = new StringTokenizer(subject, delimiters);
//            ArrayList<String> arrLis = new ArrayList<String>(subject.length());
//
//            while (strTkn.hasMoreTokens())
//                arrLis.add(strTkn.nextToken());
//
//            return arrLis.toArray(new String[0]);
//        } catch (Exception e) {
//            e.printStackTrace();
//            ArrayList<String> arrLis = new ArrayList<String>();
//            arrLis.add(subject);
//            return arrLis.toArray(new String[0]);
//        }
//
//    }
//
//
//    public String generateWithOutSilentLogicPassKey(char[] key) {
//
//        generateKnightMoveNumber();
//        moveNumberList = new ArrayList<int[]>();
//        moveStringList = new ArrayList<String[]>();
//
//        // Generating knight move using random number
//        for (int i = 0; i < knightMoveNumber.length; i++) {
//            KnightSolver solver = new KnightSolver(KNIGHT_MATRIX_WIDTH, KNIGHT_MATRIX_HEIGHT);
//
//            solver.setPosition(knightMoveNumber[i] % KNIGHT_MATRIX_WIDTH, knightMoveNumber[i] / KNIGHT_MATRIX_HEIGHT);
//            int temp[] = solver.loopSolver();
//
//            // check log
////            System.out.println("==============="+knightMoveNumber[i]);
////            for (int ii = 0; ii < temp.length; ii++) {
////                System.out.print(temp[ii] + ", ");
////            }
//            //System.out.println("");
//
//            moveNumberList.add(temp);
//            solver.reset();
//            solver = null;
//        }
//
//        // creating key index array
//        int orgKeyIndexArray[] = new int[key.length];
//        for (int i = 0; i < orgKeyIndexArray.length; i++) {
//            for (int j = 0; j < AllCharacter.getInstance().getStandardCharacterSet().length; j++) {
//                if (AllCharacter.getInstance().getStandardCharacterSet()[j].equals("" + key[i])) {
//                    orgKeyIndexArray[i] = j;
//                    //DebugUtil.printLog(key[i] +"======="+j);
//                }
//            }
//        }
//
//        //initialize temp array
////        String tempArr[] = new String[ KNIGHT_MATRIX_WIDTH* KNIGHT_MATRIX_HEIGHT];
////        for (int i = 0;i<tempArr.length;i++){
////            tempArr[i] = new String();
////        }
//
//        // Creating new character array using knight moves
//        for (int index = 0; index < moveNumberList.size(); index++) {
//            String tempArr[] = new String[(KNIGHT_MATRIX_WIDTH * KNIGHT_MATRIX_HEIGHT)];
//            for (int i = 0; i < tempArr.length; i++) {
//                tempArr[i] = new String();
//            }
//
//            if (index == 0) {
//
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr[i] = AllCharacter.getInstance().getStandardCharacterSet()[moveNumberList.get(index)[i]];
//            } else {
//                for (int i = 0; i < moveNumberList.get(index).length; i++)
//                    tempArr[i] = moveStringList.get(moveStringList.size() - 1)[moveNumberList.get(index)[i]];
//            }
//
//            moveStringList.add(tempArr);
//
//
////            System.out.println("tempArr  ----"+index);
////            for (int j= 0;j<tempArr.length;j++)
////            {
////                System.out.print(tempArr[j]+",");
////            }
//
//            tempArr = null;
//        }
//
//        StringBuffer keyBuffer = new StringBuffer();
//        for (int i = 0; i < orgKeyIndexArray.length; i++) {
//            //System.out.println();
//            //System.out.println(" moveStringList.get(moveStringList.size()-1)[i]  "+moveStringList.get(moveStringList.size()-1)[orgKeyIndexArray[i]]);
//
//            keyBuffer.append(moveStringList.get(moveStringList.size() - 1)[orgKeyIndexArray[i]]);
//        }
//        String newGeneratedKey = keyBuffer.toString();
//       // DebugUtil.printLog("Generated Key "+newGeneratedKey);
//        keyBuffer.append("^");//maping character
//        keyBuffer.append(getKnightMoveNumberValue());
//
//
//        // removing all object
////        moveNumberList.clear();
////        moveNumberList = null;
////        moveStringList.clear();
////        moveStringList = null;
//
//
//
//        return keyBuffer.toString();//keyBuffer.toString();
//
//
//        //qĉĭĺô^T­7''Ð^ô
//    }

}
