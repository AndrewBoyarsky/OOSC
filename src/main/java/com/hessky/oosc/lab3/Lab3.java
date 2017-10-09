package com.hessky.oosc.lab3;

import java.util.Objects;

public class Lab3 {
    public static String name = "pi23.3";
    public static String codeword = "\u009F";
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println(("User name: "));
//        String name = sc.next();
//        System.out.println("Enter code word: ");
//        String codeword = sc.next();
//        System.out.println();
        char[] nameChars = name.toCharArray();
        char[] codewordChars = codeword.toCharArray();
        int i = 0;
        int c1 = 0, c2 = 0;
        for (i = 0; i < nameChars.length; i++)
            c1 += (byte)nameChars[i] & 0xFF;
        for (i = 0; i < codewordChars.length; i++)
            c2 += ((byte) codewordChars[i]) & 0xFF;
        if (c1 % 256 != c2 % 256)
            System.out.println("Wrong code word");
        else {
            codeword = "bakbbkbckbbbambcbbmbcbambdtbetbbjbfu" + codeword;
            codewordChars = codeword.toCharArray();
            byte[] s = new byte[256];
            byte top = (byte) (s.length - 1);
            s[--top] = 0;
            Proposition[] proof = new Proposition[256];
            boolean cheat_mode = true;
            int pc = 0, goal = -1;
            for (i = 0; i < codewordChars.length; i++) {
                int code = codewordChars[i] - 'a';
                if (!cheat_mode) {
                    if ((codewordChars[i] >= '0' && codewordChars[i] <= '9') ||
                            (codewordChars[i] >= 'A' && codewordChars[i] <= 'Z'))
                        code = codewordChars[i] ^ c1;
                    else if (((code ^ c1) >= '0' && (code ^ c1) <= '9') ||
                            ((code ^ c1) >= 'A' && (code ^ c1) <= 'Z'))
                        break;
                }
                int A, B;
                if (i == 0) cheat_mode = true;
                switch (code) {
                    case 0:
                        i = codewordChars.length;
                        break;
                    case 1:
                        byte val = (byte) (codewordChars[++i] - 'a');
                        if (!cheat_mode) {
                            if ((codewordChars[i] >= '0' && codewordChars[i] <= '9') ||
                                    (codewordChars[i] >= 'A' && codewordChars[i] <= 'Z'))
                                val = (byte) (codewordChars[i] ^ c1);
                        }
                        s[--top] = val;
                        break;
                    case 2:
                        top++;
                        break;
                    case 3:
                        s[++top] += s[top - 1];
                        break;
                    case 4:
                        s[++top] -= s[top - 1];
                        break;
                    case 5:
                        s[++top] *= s[top - 1];
                        break;
                    case 6:
                        s[++top] /= s[top - 1];
                        break;
                    case 7:
                        s[--top] = s[top + 1];
                        break;
                    case 8:
                        if (goal < 0 || proof[goal].IsAssertion)
                            System.out.print((char) (s[top++] + 'A'));
                        break;
                    case 9:
                        int target = s[top++], value = s[top++];
                        if (target > 127) target = target - 256;
                        if (value != 0)
                            i += target;
                        break;
                    case 10:
                        proof[pc].IsAssertion = false;
                        proof[pc].Operation = Byte.toString(s[top++]);
                        pc++;
                        break;
                    case 11:
                        proof[pc].IsAssertion = false;
                        proof[pc].Operation = "NOT";
                        proof[pc].Arg1 = s[top++];
                        pc++;
                        break;
                    case 12:
                        proof[pc].IsAssertion = false;
                        proof[pc].Operation = "IMPLICATION";
                        proof[pc].Arg1 = s[top++];
                        proof[pc].Arg2 = s[top++];
                        pc++;
                        break;
                    case 13:
                    case 14:
                        A = s[top++];
                        B = s[top++];
                        int[] x = new int[256], y = new int[256];
                        x[0] = A;
                        y[0] = B;
                        int count = 1;
                        while (count > 0) {
                            count--;
                            if (!Objects.equals(proof[x[count]].Operation, proof[y[count]].Operation)) {
                                count = -1;
                                break;
                            } else if (Objects.equals(proof[x[count]].Operation, "NOT")) {
                                x[count] = proof[x[count]].Arg1;
                                y[count] = proof[y[count]].Arg1;
                                count++;
                            } else if (Objects.equals(proof[x[count]].Operation, "IMPLICATION")) {
                                int xc = x[count], yc = y[count];
                                x[count] = proof[xc].Arg1;
                                y[count] = proof[yc].Arg1;
                                count++;
                                x[count] = proof[xc].Arg2;
                                y[count] = proof[yc].Arg2;
                                count++;
                            }
                        }
                        if (count == 0) {
                            if (code == 13) {
                                proof[pc].IsAssertion = true;
                                proof[pc].Operation = "IMPLICATION";
                                proof[pc].Arg1 = A;
                                proof[pc].Arg2 = B;
                                pc++;
                            } else if (proof[A].IsAssertion || proof[B].IsAssertion) {
                                proof[A].IsAssertion = true;
                                proof[B].IsAssertion = true;
                            }
                        }
                        break;
                    case 15:
                        ;
                        A = s[top++];
                        B = s[top++];
                        if (Objects.equals(proof[B].Operation, "IMPLICATION") && proof[B].Arg2 == A) {
                            proof[pc].IsAssertion = true;
                            proof[pc].Operation = "IMPLICATION";
                            proof[pc].Arg1 = A;
                            proof[pc].Arg2 = B;
                            pc++;
                        }
                        break;
                    case 16:
                        A = s[top++];
                        B = s[top++];
                        if (Objects.equals(proof[A].Operation, "IMPLICATION") &&
                                Objects.equals(proof[B].Operation, "IMPLICATION") &&
                                Objects.equals(proof[proof[A].Arg2].Operation, "IMPLICATION") &&
                                Objects.equals(proof[proof[B].Arg1].Operation, "IMPLICATION") &&
                                proof[proof[B].Arg1].Arg1 == proof[A].Arg1 &&
                                proof[proof[B].Arg1].Arg2 == proof[proof[A].Arg2].Arg1 &&
                                Objects.equals(proof[proof[B].Arg2].Operation, "IMPLICATION") &&
                                proof[proof[B].Arg2].Arg1 == proof[A].Arg1 &&
                                proof[proof[B].Arg2].Arg2 == proof[proof[A].Arg2].Arg2) {
                            proof[pc].IsAssertion = true;
                            proof[pc].Operation = "IMPLICATION";
                            proof[pc].Arg1 = A;
                            proof[pc].Arg2 = B;
                            pc++;
                        }
                        break;
                    case 17:
                        A = s[top++];
                        B = s[top++];
                        if (Objects.equals(proof[A].Operation, "IMPLICATION") &&
                                Objects.equals(proof[B].Operation, "IMPLICATION") &&
                                Objects.equals(proof[proof[A].Arg1].Operation, "NOT") &&
                                Objects.equals(proof[proof[A].Arg2].Operation, "NOT") &&
                                proof[proof[A].Arg1].Arg1 == proof[B].Arg2 &&
                                proof[proof[A].Arg2].Arg1 == proof[B].Arg1) {
                            proof[pc].IsAssertion = true;
                            proof[pc].Operation = "IMPLICATION";
                            proof[pc].Arg1 = A;
                            proof[pc].Arg2 = B;
                            pc++;
                        }
                        break;
                    case 18:
                        A = s[top++];
                        if (Objects.equals(proof[A].Operation, "IMPLICATION") &&
                                proof[A].IsAssertion &&
                                proof[proof[A].Arg1].IsAssertion)
                            proof[proof[A].Arg2].IsAssertion = true;
                        break;
                    case 19:
                        if (cheat_mode)
                            proof[s[top++]].IsAssertion = true;
                        break;
                    case 20:
                        if (cheat_mode) goal = s[top++];
                        cheat_mode = false;
                        break;
                }
            }
        }
    }

    public static class Proposition {
        public boolean IsAssertion;
        public String Operation;
        public int Arg1;
        public int Arg2;
    }
}
