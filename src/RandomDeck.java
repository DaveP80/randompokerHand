import java.util.*;
public class RandomDeck {
    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        List<Integer> list = new ArrayList<>();
        String [] playingdeck =
                {"As", "2s", "3s", "4s", "5s", "6s", "7s", "8s", "9s", "Ts", "Js", "Qs", "Ks",
                        "Ac", "2c", "3c", "4c", "5c", "6c", "7c", "8c", "9c", "Tc", "Jc", "Qc", "Kc",
                        "Ah", "2h", "3h", "4h", "5h", "6h", "7h", "8h", "9h", "Th", "Jh", "Qh", "Kh",
                        "Ad", "2d", "3d", "4d", "5d", "6d", "7d", "8d", "9d", "Td", "Jd", "Qd", "Kd"};

        while (true) {
            System.out.println("input number of card players");
            while (!scan.hasNextInt()) {
                scan.nextLine();
            }
            int i = scan.nextInt();

            while (i > 7 || i < 1) {
                i = scan.nextInt();
            }

            for (int a = 0; a < 52; a++) {
                list.add(a);
            }
            Collections.shuffle(list);
            Object[] cut = list.toArray();
            int cuthelper = 26;
            for (int d = 0; d<26; d++) {
                int ch = (int) cut[d];
                cut[d]=cut[cuthelper];
                cut[cuthelper]=ch;
                cuthelper ++;
            }

            Set<Integer> deck = new HashSet<>();

            while (deck.size() < i * 2) {

                deck.add((Integer) cut[(int) (Math.random() * 52)]);

            }
            List<Integer> cutlistdeck = new ArrayList<>();

            for (Object z: cut) {
                cutlistdeck.add((Integer) z);
            }

            System.out.println("Shuffled and cut" + cutlistdeck);
            System.out.println("hole cards: " + deck);

            List<String> pairs = new ArrayList<>();
            int count = 0;
            int count2 = 0;
            for (int a = 0; a < deck.size()/2; a++) {
                count++;
                int[] arr = new int[2];
                Object[] arr2 = deck.toArray();
                arr[0] = (int) arr2[count2];
                arr[1] = (int) arr2[count2 + 1];

                System.out.println("Player " + count + ": " + playingdeck[arr[0]] + ", " + playingdeck[arr[1]] + "\n");
                pairs.add(playingdeck[arr[0]].substring(0, playingdeck[arr[0]].length() - 1));
                pairs.add(playingdeck[arr[1]].substring(0, playingdeck[arr[0]].length() - 1));
                count2 += 2;

            }

            for (int j : deck) {
                cutlistdeck.remove((Integer) j);

            }
            Set<Integer> deck2 = new HashSet<>();

            while (deck2.size() < 5) {

                deck2.add(cutlistdeck.get((int) (Math.random() * cutlistdeck.size())));
            }
            String[] board = new String[5];
            Object[] arr3 = deck2.toArray();
            for (int b = 0; b < 5; b++) {
                board[b] = playingdeck[(int) arr3[b]];
            }
            list.clear();
            cutlistdeck.clear();
            System.out.println("board of cards: " + Arrays.toString(board));
            int pocketpair = 0;
            List<String> boardpairs = new ArrayList<>();
            for (int r = 0; r<4; r++) {
                for (int y = 4; y>r; y--) {
                    if (Objects.equals(board[r].substring(0,1), board[y].substring(0,1))) {
                        boardpairs.add(board[r].substring(0,1));
                    }
                }
            }
            String[] handrank = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
            List<Integer> test = new ArrayList<>();
            List<String> badbeat = new ArrayList<>();
            int counter = 0;
            for (String s : boardpairs) {
                for (String t: handrank) {
                    counter ++;
                    if (t.equals(s)) {

                        test.add(counter);
                    }
                }
                counter=0;
            }
            Collections.sort(test);
            for (int x = 0; x < pairs.size(); x++) {

                if (x==pairs.size()-1) {
                    break;
                }

                if (Objects.equals(pairs.get(x), pairs.get(x + 1)) && x%2==0) {

                    List<Integer> pocketpairstrength = new ArrayList<>();
                    int counter2 = 0;
                    for (String t: handrank) {
                        counter2 ++;
                        if (t.equals(pairs.get(x))) {
                            pocketpairstrength.add(counter2);
                        }
                    }
                    for (String s : board) {
                        if (pairs.get(x).matches(String.valueOf(s.charAt(0)))) {
                            pocketpair++;
                        }
                    }
                    if (pocketpair == 2) {
                        pocketpair=0;
                        System.out.println("Quads " + pairs.get(x) + "s!");
                        badbeat.add(pairs.get(x));

                    } else if (pocketpair==0) {

                        if(boardpairs.size()==3) {
                            System.out.println("FullHouse! " + pairs.get(x) + boardpairs.get(0) + "s");
                            pocketpairstrength.clear();
                        } else if (boardpairs.size()==2) {

                            if (pocketpairstrength.get(0) > test.get(0)) {
                                System.out.println("Two Pair! " + handrank[test.get(1)-1] + pairs.get(x) + "s");
                                pocketpairstrength.clear();
                            } else if (pocketpairstrength.get(0) < test.get(0)) {
                                System.out.println("counterfeited pair!");
                                pocketpairstrength.clear();
                            }
                        } else if (boardpairs.size()==4) {
                            if (!Objects.equals(test.get(3), test.get(2))) {

                                if (pocketpairstrength.get(0) < test.get(3)) {
                                    System.out.println("counterfeit pair: full house !" + pairs.get(x) + "s");
                                    pocketpairstrength.clear();
                                } else {
                                    System.out.println("Full House! " + handrank[test.get(0)-1] + pairs.get(0) + "s");
                                }
                            } else {
                                if (pocketpairstrength.get(0) < test.get(0)) {
                                    System.out.println("counterfeit pair: full house !" + pairs.get(x) + "s");
                                    pocketpairstrength.clear();
                                } else {
                                    System.out.println("Full House! " + handrank[test.get(3) -1] + pairs.get(x) + "s");
                                    pocketpairstrength.clear();
                                }

                            }
                        }
                        else if (boardpairs.size()==1) {
                            System.out.println("Two Pair! " + boardpairs.get(0) + boardpairs.get(0) + pairs.get(x) + pairs.get(x) + "s");
                            pocketpairstrength.clear();
                        }
                        else {
                            System.out.println("Pocket Pair " + pairs.get(x) + pairs.get(x));
                            pocketpairstrength.clear();
                        }
                    }
                    pocketpair=0;
                }
            }
            int innercount = 0;
            List<String> twopairarray = new ArrayList<>();
            for (String pair : pairs) {
                innercount++;
                for (String ss : board) {
                    if (ss.contains(pair)) {
                        twopairarray.add(pair);
                        if (twopairarray.size() > 1 && twopairarray.get(0).contains(twopairarray.get(1))) {
                            twopairarray.clear();
                        }
                    }
                }
                if (twopairarray.size() == 2 && innercount % 2 == 0) {

                    List<Integer> counterfeit = new ArrayList<>();
                    int counter2 = 0;

                    for (String s : twopairarray) {
                        for (String t : handrank) {
                            counter2++;
                            if (t.equals(s)) {
                                counterfeit.add(counter2);
                            }
                        }
                        counter2 = 0;
                    }
                    Collections.sort(counterfeit);

                    if (boardpairs.size() == 1) {

                        if (counterfeit.get(0) > test.get(0)) {
                            System.out.println("Two Pair! " + twopairarray.get(0) + twopairarray.get(1) + "s");
                            counterfeit.clear();
                            twopairarray.clear();
                        } else {

                            System.out.println("Two Pair! " + handrank[counterfeit.get(1) - 1] + handrank[counterfeit.get(1) - 1] + " " + boardpairs.get(0) + boardpairs.get(0));
                            twopairarray.clear();
                            counterfeit.clear();
                        }
                    } else if (boardpairs.isEmpty()) {
                        System.out.println("Two Pair! " + twopairarray.get(0) + twopairarray.get(0) + twopairarray.get(1) + twopairarray.get(1) + "s");
                        twopairarray.clear();
                        counterfeit.clear();

                    } else if (boardpairs.size() == 3) {

                        if (!boardpairs.contains(twopairarray.get(0)) && !boardpairs.contains(twopairarray.get(1))) {
                            System.out.println("Full House! " + boardpairs + " " + handrank[counterfeit.get(1) - 1] + "s");

                        } else {
                            badbeat.add(boardpairs.get(0));
                        }
                        twopairarray.clear();
                        counterfeit.clear();
                    }
                }
                else if (twopairarray.size()!=2 && innercount%2== 0) {
                    twopairarray.clear();
                }
            }
            int iteratecount = 0;
            List<String> hands = new ArrayList<>();
            List<Integer> playerhandstrength = new ArrayList<>();

            for (String s : pairs) {

                if (iteratecount!=0 && iteratecount%2==0) {
                    playerhandstrength.clear();
                }
                iteratecount ++;

                for (String ss: board) {

                    if (ss.contains(s)) {
                        hands.add(s);
                        hands.add(s);

                        int counter2 = 0;
                        for (String t: handrank) {
                            counter2 ++;
                            if (t.equals(s)) {
                                playerhandstrength.add(counter2);
                            }
                        }
                        Collections.sort(playerhandstrength);
                        if(hands.size()==6 && Objects.equals(hands.get(0), hands.get(5))) {
                            if (badbeat.isEmpty()) {
                                System.out.println("4 of a Kind! " + hands.get(0) + "s");
                                hands.clear();
                            } else if (badbeat.contains(hands.get(0))) {
                                hands.clear();
                            }
                        }
                    }
                }
                if(hands.size()==8 && iteratecount%2==0 && boardpairs.size()==2) {
                    System.out.println("Full House! " + hands.get(0) + hands.get(0) + hands.get(4) + hands.get(4) + "s");
                    hands.clear();
                }
                if(hands.size()==6 && !Objects.equals(hands.get(0), hands.get(5)) && iteratecount%2==0) {

                    if (boardpairs.size()==2) {

                        int fullhouse = Objects.equals(playerhandstrength.get(1), playerhandstrength.get(2)) ? playerhandstrength.get(0) : playerhandstrength.get(2);
                        if (Objects.equals(playerhandstrength.get(1), test.get(1))) {
                            System.out.println("Full House! " + handrank[playerhandstrength.get(1) - 1] + handrank[Math.max(fullhouse, test.get(0)) - 1] + "s");
                            hands.clear();

                        } else if (Objects.equals(playerhandstrength.get(1), test.get(0))) {
                            System.out.println("Full House! " + handrank[playerhandstrength.get(1) - 1] + handrank[Math.max(fullhouse, test.get(1))-1] + "s");
                            hands.clear();
                        }
                    } else {
                        System.out.println("Full House! " + hands.get(0) + " " + hands.get(4) + "s");
                        hands.clear();
                    }

                }
                if (hands.size()==4 && iteratecount%2==0 && Objects.equals(pairs.get(iteratecount-2), pairs.get(iteratecount-1))) {
                    if (boardpairs.size()==1 || boardpairs.size()==2) {
                        if (boardpairs.size()==1) {
                            System.out.println("Full House! " + boardpairs.get(0) + hands.get(0) + "s");
                            hands.clear();
                        } else {
                            System.out.println("Full House! " + hands + " " + handrank[test.get(1)-1]);
                            hands.clear();
                        }
                    }
                    else if (boardpairs.size()==3) {

                        if (playerhandstrength.get(0) > test.get(0)) {
                            hands.remove(3);
                            System.out.println("Full House! " + hands + " " + boardpairs.get(0));
                            hands.clear();
                        } else {
                            System.out.println("Full House! " + boardpairs + " " + hands.get(0));
                            hands.clear();
                        }
                    } else if (boardpairs.size()==6) {
                        hands.clear();
                    }
                    else {
                        System.out.println("Set of " + hands.get(0) + "s!");
                        hands.clear();
                    }
                }

                if (hands.size()==2 && iteratecount%2==0) {
                    if (boardpairs.size()==1) {
                        if (badbeat.contains(hands.get(0))) {
                            hands.clear();
                        } else {
                            System.out.println("Two Pair! " + hands.get(0) + hands.get(0) + " " + boardpairs.get(0) + boardpairs.get(0) + "s");
                            hands.clear();
                        }
                    } else if (boardpairs.size()==2) {
                        if (badbeat.contains(hands.get(0))) {
                            hands.clear();
                        }
                        else if (test.get(0)<playerhandstrength.get(0)) {
                            System.out.println("Two Pair! " + hands.get(0) + hands.get(0) + " " + handrank[test.get(1)-1] + handrank[test.get(1)-1] + "s" );
                            hands.clear();
                        } else {
                            System.out.println("Counterfeit! " + boardpairs.get(0) + boardpairs.get(1) + "s" );
                            hands.clear();
                        }
                    }else if (boardpairs.size()==3) {
                        if (boardpairs.contains(pairs.get(iteratecount-2)) || boardpairs.contains(pairs.get(iteratecount-1))) {
                            hands.clear();
                        } else {
                            System.out.println("Full house! " + boardpairs + " " + hands.get(0));
                            hands.clear();
                        }
                    } else if (boardpairs.size()==6) {
                        System.out.println("Four of a Kind! " + boardpairs.get(0) + boardpairs.get(0) + boardpairs.get(0) + boardpairs.get(0));
                        hands.clear();
                    }else if (boardpairs.size()==0) {
                        System.out.println("Pair! " + hands.get(0) + "s");
                        hands.clear();
                    }
                }
                if(hands.size()==4 && hands.get(0).contains(hands.get(3)) && iteratecount%2==0) {
                    if (boardpairs.size()==1 && !Objects.equals(hands.get(0), boardpairs.get(0))) {
                        System.out.println("Full House! " + hands.get(0) + boardpairs.get(0) + "s");
                        hands.clear();
                    } else if (boardpairs.size()==3) {
                        System.out.println("Full House! " + hands.get(0) + boardpairs.get(0) + "s");
                        hands.clear();
                    } else if (boardpairs.size()==4) {
                        System.out.println("Full House! " + handrank[test.get(3)-1] + hands.get(0) + "s");
                        hands.clear();
                    }
                    // Add full house method
                    else if (boardpairs.size()==2) {
                        String match = !Objects.equals(hands.get(0), boardpairs.get(0)) ? boardpairs.get(0) : boardpairs.get(1);
                        System.out.println("Full House! " + hands.get(0) + match + "s");
                        hands.clear();
                    } else {
                        System.out.println("3 of a Kind! " + hands.get(0) + "s");
                        hands.clear();
                    }
                }
                if(hands.size()==4 && !Objects.equals(hands.get(0), hands.get(2)) && iteratecount%2==0) {
                    hands.clear();
                }
            }
            boardpairs.clear();
            test.clear();
            badbeat.clear();
            Thread.sleep(1500);
        }
    }
}
